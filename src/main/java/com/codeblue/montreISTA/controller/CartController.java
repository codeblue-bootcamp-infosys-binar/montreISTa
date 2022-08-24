package com.codeblue.montreISTA.controller;
import com.codeblue.montreISTA.DTO.CartRequestDTO;
import com.codeblue.montreISTA.service.CartService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(name="07. Cart")
@SecurityRequirement(name = "bearer-key")
public class CartController {


    private final CartService cartService;




    @GetMapping("/user/my-cart/buyer")
    public ResponseEntity<Object> findByBuyer(Authentication authentication) throws Exception {
            return cartService.findByBuyer(authentication.getName());
    }


    @GetMapping("/user/my-cart/seller")
    public ResponseEntity<Object> findBySeller(Authentication authentication) throws Exception {
           return cartService.findBySeller(authentication.getName());
    }


    @PostMapping("/user/add-to-cart")
    public ResponseEntity<Object> postCart(@RequestBody CartRequestDTO cart, Authentication authentication) throws Exception {
            return cartService.createCart(cart, authentication);
    }

    @PostMapping("/user/wishlist-to-cart")
    public ResponseEntity<Object> postCart(Authentication authentication) throws Exception {
           return cartService.wishlistToCart(authentication);
    }


    @PutMapping("/user/cart/{id}")
    public ResponseEntity<Object> updateCart(@PathVariable Long id, @RequestBody CartRequestDTO cart, Authentication authentication) throws Exception {
            return cartService.updateCart(cart,id,authentication);
    }


    @DeleteMapping("/user/cart/{id}")
    public ResponseEntity<Object> deleteCart(@PathVariable Long id, Authentication authentication) throws Exception {
           return cartService.deleteById(id, authentication);
    }


    @GetMapping("/dashboard/carts")
    public ResponseEntity<Object> findAll() throws Exception {
          return cartService.findAll();

    }

    @GetMapping("/dashboard/cart/category")
    public ResponseEntity<Object> findByCategory(@Param("keyword") String keyword) throws Exception {
            return cartService.findByCategory(keyword);
    }


    @GetMapping("/dashboard/cart/product-name")
    public ResponseEntity<Object> findByProductName(@Param("keyword") String keyword) throws Exception {
            return cartService.findByProductName(keyword);
    }
}
