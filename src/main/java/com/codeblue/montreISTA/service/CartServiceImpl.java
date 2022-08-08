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
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private BuyerRepository buyerRepository;
    private PaymentRepository paymentRepository;
    private ShippingRepository shippingRepository;

    @Override
    public List<CartResponseDTO> findAll() {
        List<Cart> results= this.cartRepository.findAllByOrderByCartIdAsc();
    return this.convertListDTO(results);
    }

    @Override
    public List<CartResponseDTO> findByBuyer(String keyword) throws Exception {
        List<Cart> results = this.cartRepository.findByBuyerUserNameIgnoreCaseContaining(keyword);
        if(results==null){
            throw new Exception("Carts not found");
        }
        return this.convertListDTO(results);
    }

    @Override
    public List<CartResponseDTO> findBySeller(String keyword) throws Exception {
        List<Cart> results = this.cartRepository.findByProductSellerUserIdNameIgnoreCaseContaining(keyword);
        if(results==null){
            throw new Exception("Carts not found");
        }
        return this.convertListDTO(results);
    }

    @Override
    public List<CartResponseDTO> findByProductName(String keyword) throws Exception {
        List<Cart> results = this.cartRepository.findByProductProductNameIgnoreCaseContaining(keyword);
        if(results==null){
            throw new Exception("Carts not found");
        }
        return this.convertListDTO(results);
    }

    @Override
    public List<CartResponseDTO> findByCategory(String keyword) throws Exception {
        List<Cart> results = this.cartRepository.findByProductCategoriesCategoryNameIgnoreCaseContaining(keyword);
        if(results==null){
            throw new Exception("Cart not found");
        }
        return this.convertListDTO(results);
    }

    @Override
    public CartResponseDTO createCart(CartRequestDTO cartRequestDTO) throws Exception {
        Optional<Order> orderBuyerId = this.orderRepository.findFirstByListCartBuyerBuyerIdOrderByCreatedAtDesc(cartRequestDTO.getBuyer_id());
        Long orderId;
        if(orderBuyerId.isPresent()){
            Order order = orderBuyerId.get();
            orderId = order.getOrderId();
        }else {
            Order newOrder = new Order();
            Long id = 1L;
            Payment payment = this.paymentRepository.findById(id).orElseThrow(Exception::new);
            Shipping shipping = this.shippingRepository.findById(id).orElseThrow(Exception::new);
            newOrder.setShipping(shipping);
            newOrder.setTotalprice(0);
            newOrder.setPayment(payment);
            Order saveOrder = orderRepository.save(newOrder);
            orderId = saveOrder.getOrderId();
        }
        Cart saveCart = this.requestToEntity(cartRequestDTO, orderId);
        this.cartRepository.save(saveCart);
        return convertDTO(saveCart);
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
}
