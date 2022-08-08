package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.*;
import com.codeblue.montreISTA.entity.*;
import com.codeblue.montreISTA.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartServices {
    private CartRepository cartRepository;
    private CategoryService categoryService;
    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private BuyerRepository buyerRepository;
    private PaymentRepository paymentRepository;
    private ShippingRepository shippingRepository;

    @Override
    public List<CartResponseDTO> findAll() {
        List<Cart> results= cartRepository.findAllByOrderByCartIdAsc();
    return this.convertListDTO(results);
    }

    @Override
    public List<CartResponseDTO> findByBuyer(String keyword) throws Exception {
        List<Cart> results = cartRepository.findByBuyerUserNameIgnoreCaseContainingOrderByCartIdAsc(keyword);
        if(results==null){
            throw new Exception("Carts not found");
        }
        return this.convertListDTO(results);
    }

    @Override
    public List<CartResponseDTO> findBySeller(String keyword) throws Exception {
        List<Cart> results = cartRepository.findByProductSellerUserIdNameIgnoreCaseContainingOrderByCartIdAsc(keyword);
        if(results==null){
            throw new Exception("Carts not found");
        }
        return this.convertListDTO(results);
    }

    @Override
    public List<CartResponseDTO> findByProductName(String keyword) throws Exception {
        List<Cart> results = cartRepository.findByProductProductNameIgnoreCaseContainingOrderByCartIdAsc(keyword);
        if(results==null){
            throw new Exception("Carts not found");
        }
        return this.convertListDTO(results);
    }

    @Override
    public List<CartResponseDTO> findByCategory(String keyword) throws Exception {
        List<Cart> results = cartRepository.findByProductCategoriesCategoryNameIgnoreCaseContainingOrderByCartIdAsc(keyword);
        if(results==null){
            throw new Exception("Cart not found");
        }
        return this.convertListDTO(results);
    }

    @Override
    public CartResponseDTO createCart(CartRequestDTO cartRequestDTO) throws Exception {

        Order order = orderRepository.findFirstByOrderByCreatedAtDesc();

        List<OrderCartDTO> cartDTO = order.getListCart().stream()
                .map(Cart::convertToOrder)
                .collect(Collectors.toList());

        OrderResponseDTO orderDTO = order.convertToResponse(cartDTO);

        Long filter = cartRequestDTO.getBuyer_id();
        Boolean filterlist = orderDTO.getListCart().stream()
                .allMatch(Cart -> Cart.getBuyer_id() == filter);
        Long orderIdCart;

        if (filterlist == true) {
           orderIdCart = orderDTO.getOrderId();
        }else {
            Order newOrder = new Order();
            Long id = 1L;
            Payment payment = paymentRepository.findById(id).orElseThrow(Exception::new);
            Shipping shipping = shippingRepository.findById(id).orElseThrow(Exception::new);
            newOrder.setShipping(shipping);
            newOrder.setTotalprice(10000);
            newOrder.setPayment(payment);
            Order saveOrder = orderRepository.save(newOrder);
            orderIdCart = saveOrder.getOrderId();
        }

        Cart saveCart = this.requestToEntity(cartRequestDTO, orderIdCart);
        cartRepository.save(saveCart);
        return convertDTO(saveCart);
    }

    @Override
    public CartResponseDTO updateCart(CartRequestDTO cartRequestDTO, Long id) throws Exception {
        Optional<Cart> cartId = cartRepository.findById(id);
        if(cartId.isEmpty()){
            throw new Exception("Cart not found");
        }
        Cart cart = cartId.get();
        Long productIdCart = cart.getOrder().getOrderId();
        Cart saveCart = this.requestToEntity(cartRequestDTO,productIdCart);
        saveCart.setCartId(id);
        cartRepository.save(saveCart);
        return convertDTO(saveCart);
    }

    @Override
    public void deleteById(Long id) throws Exception {
        Optional<Cart> cartId = cartRepository.findById(id);
        if(cartId.isEmpty()){
            throw new Exception("Cart not found");
        }
        cartRepository.deleteById(id);
    }

    public List<CartResponseDTO> convertListDTO(List<Cart> carts) {
        List<CartResponseDTO> cartResponseDTOS = new ArrayList<>();
        for (Cart cart : carts) {
            CartResponseDTO cartDTO = convertDTO(cart);
            cartResponseDTOS.add(cartDTO);
        }
        return cartResponseDTOS;
    }

    public CartResponseDTO convertDTO (Cart cart){
        List<PhotoProductDTO> photosDTO = cart.getProduct().getPhotos().stream()
                .map(Photo::convertToProduct)
                .collect(Collectors.toList());
        List<Category> categories = categoryService.findByProductId(cart.getProduct().getProductId());
        List<String> categoriesDTO = new ArrayList<>();
        for (Category category : categories) {
            String categoryDTO = category.getName();
            categoriesDTO.add(categoryDTO);
        }
        return cart.convertToResponse(photosDTO, categoriesDTO);

    }

    public Cart requestToEntity (CartRequestDTO cartRequestDTO, Long id){
        Optional<Product> productId = productRepository.findById(cartRequestDTO.getProduct_id());
        Product product = productId.get();
        Optional<Order> orderId = orderRepository.findById(id);
        Order order = orderId.get();
        Optional<Buyer> buyerId = buyerRepository.findById(cartRequestDTO.getBuyer_id());
        Buyer buyer = buyerId.get();
        return cartRequestDTO.convertToEntity(buyer,product,order);
    }
}
