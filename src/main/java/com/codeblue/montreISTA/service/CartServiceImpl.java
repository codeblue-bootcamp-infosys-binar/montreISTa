package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.DTO.CartRequestDTO;
import com.codeblue.montreISTA.DTO.CartResponseDTO;
import com.codeblue.montreISTA.DTO.PhotoProductDTO;
import com.codeblue.montreISTA.entity.*;
import com.codeblue.montreISTA.repository.BuyerRepository;
import com.codeblue.montreISTA.repository.CartRepository;
import com.codeblue.montreISTA.repository.OrderRepository;
import com.codeblue.montreISTA.repository.ProductRepository;
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

    @Override
    public List<CartResponseDTO> findAll() {
        List<Cart> results= cartRepository.findAll();
    return this.convertListDTO(results);
    }

    @Override
    public List<CartResponseDTO> findByBuyer(String keyword) throws Exception {
        List<Cart> results = cartRepository.findByBuyerUserNameIgnoreCaseContaining(keyword);
        if(results==null){
            throw new Exception("Carts not found");
        }
        return this.convertListDTO(results);
    }

    @Override
    public List<CartResponseDTO> findBySeller(String keyword) throws Exception {
        List<Cart> results = cartRepository.findByProductSellerUserIdNameIgnoreCaseContaining(keyword);
        if(results==null){
            throw new Exception("Carts not found");
        }
        return this.convertListDTO(results);
    }

    @Override
    public List<CartResponseDTO> findByProductName(String keyword) throws Exception {
        List<Cart> results = cartRepository.findByProductProductNameIgnoreCaseContaining(keyword);
        if(results==null){
            throw new Exception("Carts not found");
        }
        return this.convertListDTO(results);
    }

    @Override
    public List<CartResponseDTO> findByCategory(String keyword) throws Exception {
        List<Cart> results = cartRepository.findByProductCategoriesCategoryNameIgnoreCaseContaining(keyword);
        if(results==null){
            throw new Exception("Cart not found");
        }
        return this.convertListDTO(results);
    }

    @Override
    public CartResponseDTO createCart(CartRequestDTO cartRequestDTO) {
       Cart saveCart = this.requestToEntity(cartRequestDTO);
       cartRepository.save(saveCart);
       return convertDTO(saveCart);
    }

    @Override
    public CartResponseDTO updateCart(CartRequestDTO cartRequestDTO, Long id) throws Exception {
        Optional<Cart> cartId = cartRepository.findById(id);
        if(cartId.isEmpty()){
            throw new Exception("Cart not found");
        }
        Cart saveCart = this.requestToEntity(cartRequestDTO);
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

    public Cart requestToEntity (CartRequestDTO cartRequestDTO){
        Optional<Product> productId = productRepository.findById(cartRequestDTO.getProduct_id());
        Product product = productId.get();
        Optional<Order> orderId = orderRepository.findById(cartRequestDTO.getOrder_id());
        Order order = orderId.get();
        Optional<Buyer> buyerId = buyerRepository.findById(cartRequestDTO.getBuyer_id());
        Buyer buyer = buyerId.get();
        return cartRequestDTO.convertToEntity(buyer,product,order);
    }
}
