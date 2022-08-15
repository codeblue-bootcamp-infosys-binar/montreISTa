package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.*;
import com.codeblue.montreISTA.entity.*;
import com.codeblue.montreISTA.repository.*;
import com.codeblue.montreISTA.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
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
        List<Cart> results = this.cartRepository.findByBuyerUserUsernameIgnoreCaseContainingOrderByCartIdAsc(keyword);
        if(results.isEmpty()){
            throw new Exception("Carts not found");
        }
        return this.convertListDTO(results);
    }

    @Override
    public List<CartResponseDTO> findBySeller(String keyword) throws Exception {
        List<Cart> results = this.cartRepository.findByProductSellerUserIdUsernameIgnoreCaseContainingOrderByCartIdAsc(keyword);
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
    public CartResponseDTO createCart(CartRequestDTO cartRequestDTO, Authentication authentication) throws Exception {
        Optional<Order> orderBuyerId = orderRepository.findFirstByListCartBuyerUserUsernameOrderByModifiedAtDesc(authentication.getName());
        Product productId = productRepository.findById(cartRequestDTO.getProduct_id()).orElseThrow(()->new Exception("Product not Found"));
        Buyer buyer = buyerRepository.findByUserUsername(authentication.getName()).orElseThrow(()->new Exception("Buyer not Found"));
        if(buyer.getUser().equals(productId.getSeller().getUserId())){
            throw new Exception("You can't order your own product honey");
        }
        Long orderId;
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
        Cart saveCart = this.requestToEntity(cartRequestDTO, orderId, buyer);

        //update Price
        this.updatePrice(orderId);
        Cart cartResponse = this.cartRepository.save(saveCart);

        //show Cart
        return convertDTO(cartResponse);
    }

    @Override
    public List<CartResponseDTO> wishlistToCart(Authentication authentication) throws Exception {
        List<Wishlist> wishlists = wishlistRepository.findByBuyerUserUsername(authentication.getName());
        if(wishlists.isEmpty()){
            throw new Exception("Your wishlist is empty");
        }
        List<CartResponseDTO> carts = new ArrayList<>();
        for(Wishlist wishlist:wishlists){
           CartRequestDTO cartRequestDTO = new CartRequestDTO();
           cartRequestDTO.setProduct_id(wishlist.getProduct().getProductId());
           cartRequestDTO.setQuantity(wishlist.getQuantity());
           CartResponseDTO cartResponseDTO = this.createCart(cartRequestDTO,authentication);
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
    public CartResponseDTO updateCart(CartRequestDTO cartRequestDTO, Long id,Authentication authentication) throws Exception {
        Cart cart = cartRepository.findById(id).orElseThrow(()->new Exception("Cart not found"));
        Buyer buyer = buyerRepository.findByUserUsername(authentication.getName()).orElseThrow(()->new Exception("Buyer not Found"));
        Long orderId = cart.getOrder().getOrderId();
        if(!buyer.equals(cart.getBuyer())){
            throw new Exception("You can't update other cart");
        }
        if(buyer.getUser().equals(cart.getProduct().getSeller().getUserId())){
            throw new Exception("You can't order your own product honey");
        }
        Cart saveCart = this.requestToEntity(cartRequestDTO,orderId,buyer);
        saveCart.setCartId(id);
        saveCart.setQuantity(cartRequestDTO.getQuantity());
        //update Price
        this.updatePrice(orderId);
        Cart cartResponse = cartRepository.save(saveCart);
        return convertDTO(cartResponse);
    }

    @Override
    public void deleteById(Long id,Authentication authentication) throws Exception {
        Optional<Cart> cartId = this.cartRepository.findById(id);
        Buyer buyer = buyerRepository.findByUserUsername(authentication.getName()).orElseThrow(()->new Exception("Buyer not Found"));
         if(cartId.isEmpty()){
            throw new Exception("Cart not found");
        }
        if(!buyer.equals(cartId.get().getBuyer())){
            throw new Exception("You can't delete other cart");
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

    public Cart requestToEntity (CartRequestDTO cartRequestDTO, Long orderId, Buyer buyer)throws Exception{
        Product product = productRepository.findById(cartRequestDTO.getProduct_id()).orElseThrow(()->new Exception("Product not found"));
        Order order = orderRepository.findById(orderId).orElseThrow(()->new Exception("Order not found"));
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
