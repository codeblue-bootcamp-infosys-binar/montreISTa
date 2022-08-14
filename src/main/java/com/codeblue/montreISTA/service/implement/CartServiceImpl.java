package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.*;
import com.codeblue.montreISTA.entity.*;
import com.codeblue.montreISTA.repository.*;
import com.codeblue.montreISTA.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final BuyerRepository buyerRepository;
    private final PaymentRepository paymentRepository;
    private final ShippingRepository shippingRepository;
    private final WishlistRepository wishlistRepository;

    @Override
    public List<CartResponseDTO> findAll() throws Exception {
        List<Cart> results= this.cartRepository.findAllByOrderByCartIdAsc();
        if(results.isEmpty()){
            throw new Exception("Carts not found");
        }
    return this.convertListDTO(results);
    }

    @Override
    public List<CartResponseDTO> findByBuyer(String keyword) throws Exception {
        List<Cart> results = this.cartRepository.findByBuyerUserNameIgnoreCaseContainingOrderByCartIdAsc(keyword);
        if(results.isEmpty()){
            throw new Exception("Carts not found");
        }
        return this.convertListDTO(results);
    }

    @Override
    public List<CartResponseDTO> findBySeller(String keyword) throws Exception {
        List<Cart> results = this.cartRepository.findByProductSellerUserIdNameIgnoreCaseContainingOrderByCartIdAsc(keyword);
        if(results.isEmpty()){
            throw new Exception("Carts not found");
        }
        return this.convertListDTO(results);
    }

    @Override
    public List<CartResponseDTO> findByProductName(String keyword) throws Exception {
        List<Cart> results = this.cartRepository.findByProductProductNameIgnoreCaseContainingOrderByCartIdAsc(keyword);
        if(results.isEmpty()){
            throw new Exception("Carts not found");
        }
        return this.convertListDTO(results);
    }

    @Override
    public List<CartResponseDTO> findByCategory(String keyword) throws Exception {
        List<Cart> results = this.cartRepository.findByProductCategoriesCategoryNameIgnoreCaseContainingOrderByCartIdAsc(keyword);
        if(results.isEmpty()){
            throw new Exception("Cart not found");
        }
        return this.convertListDTO(results);
    }

    @Override
    public CartResponseDTO createCart(CartRequestDTO cartRequestDTO) throws Exception {
        Optional<Order> orderBuyerId = orderRepository.findFirstByListCartBuyerBuyerIdOrderByModifiedAtDesc(cartRequestDTO.getBuyer_id());
        Product productId = productRepository.findById(cartRequestDTO.getProduct_id()).orElseThrow(()->new Exception("Product not Found"));
        Buyer buyer = buyerRepository.findById(cartRequestDTO.getBuyer_id()).orElseThrow(()->new Exception("Buyer not Found"));
        Long orderId;
        if(buyer.getUser().getUserId().equals(productId.getSeller().getUserId().getUserId())){
            throw new Exception("You can't order your own product honey");
        }
        if(orderBuyerId.isPresent()){
            orderId = orderBuyerId.get().getOrderId();
        }else {
            Order newOrder = new Order();
            Long id = 1L;
            Payment payment = this.paymentRepository.findById(id).orElseThrow(Exception::new);
            Shipping shipping = this.shippingRepository.findById(id).orElseThrow(Exception::new);
            newOrder.setShipping(shipping);
            Integer subtotal = (cartRequestDTO.getQuantity()*productId.getPrice());
            newOrder.setTotalprice(subtotal+shipping.getPrice());
            newOrder.setPayment(payment);
            Order saveOrder = orderRepository.save(newOrder);
            orderId = saveOrder.getOrderId();
        }
        Cart saveCart = this.requestToEntity(cartRequestDTO, orderId);

        //update Price
        this.updatePrice(orderId);
        Cart cartResponse = this.cartRepository.save(saveCart);

        //show Cart
        return convertDTO(cartResponse);
    }

    @Override
    public List<CartResponseDTO> wishlistToCart(Long id) throws Exception {
        List<Wishlist> wishlists = wishlistRepository.findByBuyerBuyerIdOrderByModifiedAtDesc(id);
        if(wishlists.isEmpty()){
            throw new Exception("Your wishlist is empty");
        }
        List<CartResponseDTO> carts = new ArrayList<>();
        for(Wishlist wishlist:wishlists){
           CartRequestDTO cartRequestDTO = new CartRequestDTO();
           cartRequestDTO.setBuyer_id(wishlist.getBuyer().getBuyerId());
           cartRequestDTO.setProduct_id(wishlist.getProduct().getProductId());
           cartRequestDTO.setQuantity(wishlist.getQuantity());
           CartResponseDTO cartResponseDTO = this.createCart(cartRequestDTO);
           carts.add(cartResponseDTO);
        }
        if(carts.isEmpty()){
            throw new Exception("failed to parsing data from wishlist to cart");
        }else {
            wishlistRepository.deleteAll(wishlists);
        }
        return carts;
    }

    @Override
    public CartResponseDTO updateCart(CartRequestDTO cartRequestDTO, Long id) throws Exception {
        Cart cart = this.cartRepository.findById(id).orElseThrow(()->new Exception("Cart not found"));
        Long orderId = cart.getOrder().getOrderId();
        Cart saveCart = this.requestToEntity(cartRequestDTO,orderId);
        saveCart.setCartId(id);
        saveCart.setQuantity(cartRequestDTO.getQuantity());
        //update Price
        this.updatePrice(orderId);
        Cart cartResponse = this.cartRepository.save(saveCart);
        return convertDTO(cartResponse);
    }

    @Override
    public void deleteById(Long id) throws Exception {
        Optional<Cart> cartId = this.cartRepository.findById(id);
        if(cartId.isEmpty()){
            throw new Exception("Cart not found");
        }
        this.cartRepository.deleteById(id);
    }

    public List<CartResponseDTO> convertListDTO(List<Cart> carts) {
        return carts.stream()
                .map(this::convertDTO)
                .collect(Collectors.toList());
    }

    public CartResponseDTO convertDTO (Cart cart){
        List<PhotoProductDTO> photosDTO = cart.getProduct().getPhotos().stream()
                .map(Photo::convertToProduct)
                .collect(Collectors.toList());
        List<Category> categories = this.categoryRepository.findByProductsProductProductId(cart.getProduct().getProductId());
        List<String> categoriesDTO = categories.stream()
                .map(Category::getName)
                .collect(Collectors.toList());
        return cart.convertToResponse(photosDTO, categoriesDTO);
    }

    public Cart requestToEntity (CartRequestDTO cartRequestDTO, Long orderId)throws Exception{
        Optional<Product> productById = productRepository.findById(cartRequestDTO.getProduct_id());
        Optional<Order> orderById = orderRepository.findById(orderId);
        Optional<Buyer> buyerById = buyerRepository.findById(cartRequestDTO.getBuyer_id());
        if(productById.isEmpty() || orderById.isEmpty() || buyerById.isEmpty()){
            throw new Exception("Data not found");
        }
        Product product = productById.get();
        Order order = orderById.get();
        Buyer buyer = buyerById.get();
        return cartRequestDTO.convertToEntity(buyer,product,order);
    }

    public void updatePrice(Long orderId) throws Exception{
        Order updatePrice = orderRepository.findById(orderId).orElseThrow(()->new Exception("Cart not found"));
        if(updatePrice.getListCart()!=null){
            int tempPrice = 0;
            for(Cart cartLoop : updatePrice.getListCart()){
                int subtotal = cartLoop.getQuantity() * cartLoop.getProduct().getPrice();
                tempPrice += subtotal+updatePrice.getShipping().getPrice();
            }
            updatePrice.setTotalprice(tempPrice);
            orderRepository.save(updatePrice);
        }
    }
}
