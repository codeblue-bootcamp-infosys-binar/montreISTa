package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.entity.Cart;
import com.codeblue.montreISTA.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartServices {
    private CartRepository cartRepository;

    @Override
    public List<Cart> findAll() {

        return cartRepository.findAll();
    }

    @Override
    public List<Cart> findByBuyer(String keyword) throws Exception {
        List<Cart> results = cartRepository.findByBuyerUserNameContaining(keyword);
        if(results==null){
            throw new Exception("Cart not found");
        }
        return results;
    }

    @Override
    public List<Cart> findBySeller(String keyword) throws Exception {
        List<Cart> results = cartRepository.findByProductSellerUserIdNameContaining(keyword);
        if(results==null){
            throw new Exception("Cart not found");
        }
        return results;
    }

    @Override
    public List<Cart> findByProductName(String keyword) throws Exception {
        List<Cart> results = cartRepository.findByProductProductNameContaining(keyword);
        if(results==null){
            throw new Exception("Cart not found");
        }
        return results;
    }

    @Override
    public List<Cart> findByCategory(String keyword) throws Exception {
        List<Cart> results = cartRepository.findByProductCategoriesCategoryNameContaining(keyword);
        if(results==null){
            throw new Exception("Cart not found");
        }
        return results;
    }

    @Override
    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart updateCart(Cart cart, Long id) throws Exception {
        Optional<Cart> cartId = cartRepository.findById(id);
        if(cartId.isEmpty()){
            throw new Exception("Cart not found");
        }
        cart.setCartId(id);
        return cartRepository.save(cart);
    }

    @Override
    public void deleteById(Long id) throws Exception {
        Optional<Cart> cartId = cartRepository.findById(id);
        if(cartId.isEmpty()){
            throw new Exception("Cart not found");
        }
        cartRepository.deleteById(id);
    }
}
