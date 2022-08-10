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

    private CartRepository cartRepository;
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private BuyerRepository buyerRepository;
    private PaymentRepository paymentRepository;
    private ShippingRepository shippingRepository;
    private WishlistRepository wishlistRepository;

    @Override
    public List<CartResponseDTO> findAll() {
        List<Cart> results= this.cartRepository.findAllByOrderByCartIdAsc();
    return this.convertListDTO(results);
    }

    @Override
    public List<CartResponseDTO> findByBuyer(String keyword) throws Exception {
        List<Cart> results = this.cartRepository.findByBuyerUserNameIgnoreCaseContainingOrderByCartIdAsc(keyword);
        if(results==null){
            throw new Exception("Carts not found");
        }
        return this.convertListDTO(results);
    }

    @Override
    public List<CartResponseDTO> findBySeller(String keyword) throws Exception {
        List<Cart> results = this.cartRepository.findByProductSellerUserIdNameIgnoreCaseContainingOrderByCartIdAsc(keyword);
        if(results==null){
            throw new Exception("Carts not found");
        }
        return this.convertListDTO(results);
    }

    @Override
    public List<CartResponseDTO> findByProductName(String keyword) throws Exception {
        List<Cart> results = this.cartRepository.findByProductProductNameIgnoreCaseContainingOrderByCartIdAsc(keyword);
        if(results==null){
            throw new Exception("Carts not found");
        }
        return this.convertListDTO(results);
    }

    @Override
    public List<CartResponseDTO> findByCategory(String keyword) throws Exception {
        List<Cart> results = this.cartRepository.findByProductCategoriesCategoryNameIgnoreCaseContainingOrderByCartIdAsc(keyword);
        if(results==null){
            throw new Exception("Cart not found");
        }
        return this.convertListDTO(results);
    }

    @Override
    public CartResponseDTO createCart(CartRequestDTO cartRequestDTO) throws Exception {
        Optional<Order> orderBuyerId = orderRepository.findFirstByListCartBuyerBuyerIdOrderByCreatedAtDesc(cartRequestDTO.getBuyer_id());
        Optional<Product> productId = productRepository.findById(cartRequestDTO.getProduct_id());
        Long orderId;
        if(productId.isEmpty()){
            throw new Exception("Product not found");
        }
        if(orderBuyerId.isPresent()){
            orderId = orderBuyerId.get().getOrderId();
        }else {
            Order newOrder = new Order();
            Long id = 1L;
            Payment payment = this.paymentRepository.findById(id).orElseThrow(Exception::new);
            Shipping shipping = this.shippingRepository.findById(id).orElseThrow(Exception::new);
            newOrder.setShipping(shipping);
            Integer price = cartRequestDTO.getQuantity()*productId.get().getPrice();
            newOrder.setTotalprice(price);
            newOrder.setPayment(payment);
            Order saveOrder = orderRepository.save(newOrder);
            orderId = saveOrder.getOrderId();
        }
        Cart saveCart = this.requestToEntity(cartRequestDTO, orderId);
        this.cartRepository.save(saveCart);

        //update Price
        this.updatePrice(orderId);
        //show Cart
        return convertDTO(saveCart);
    }

    @Override
    public List<CartResponseDTO> wishlistToCart(Long id) throws Exception {
        List<Wishlist> wishlists = wishlistRepository.findByBuyerBuyerIdOrderByModifiedAtDesc(id);
        if(wishlists==null){
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
        if(carts==null){
            throw new Exception("failed to parsing data from wishlist to cart");
        }else {
            wishlistRepository.deleteAll(wishlists);
        }
        return carts;
    }

    @Override
    public CartResponseDTO updateCart(CartRequestDTO cartRequestDTO, Long id) throws Exception {
        Optional<Cart> cartId = this.cartRepository.findById(id);
        if(cartId.isEmpty()){
            throw new Exception("Cart not found");
        }
        Cart cart = cartId.get();
        Long orderId = cart.getOrder().getOrderId();
        Cart saveCart = this.requestToEntity(cartRequestDTO,orderId);
        saveCart.setCartId(id);
        this.cartRepository.save(saveCart);

        //update Price
        this.updatePrice(orderId);
        return convertDTO(saveCart);
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

    public Cart requestToEntity (CartRequestDTO cartRequestDTO, Long id)throws Exception{
        Optional<Product> productId = productRepository.findById(cartRequestDTO.getProduct_id());
        Optional<Order> orderId = orderRepository.findById(id);
        Optional<Buyer> buyerId = buyerRepository.findById(cartRequestDTO.getBuyer_id());
        if(productId.isEmpty() || orderId.isEmpty() || buyerId.isEmpty()){
            throw new Exception("Data not found");
        }
        Product product = productId.get();
        Order order = orderId.get();
        Buyer buyer = buyerId.get();
        return cartRequestDTO.convertToEntity(buyer,product,order);
    }

    public void updatePrice(Long orderId){
        Optional<Order> getOrder = orderRepository.findById(orderId);
        Order updatePrice = getOrder.get();
        if(updatePrice.getListCart()!=null){
            Integer tempPrice = 0;
            for(Cart cartLoop : updatePrice.getListCart()){
                int total = cartLoop.getQuantity() * cartLoop.getProduct().getPrice();
                tempPrice += total;
            }
            tempPrice += updatePrice.getShipping().getPrice();
            updatePrice.setTotalprice(tempPrice);
            orderRepository.save(updatePrice);
        }
    }
}
