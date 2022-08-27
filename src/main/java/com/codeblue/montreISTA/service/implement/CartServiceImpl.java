package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.*;
import com.codeblue.montreISTA.controller.CartController;
import com.codeblue.montreISTA.entity.*;
import com.codeblue.montreISTA.repository.*;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.CartService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    private static final String Line = "====================";

    private final CartRepository cartRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final BuyerRepository buyerRepository;
    private final PaymentRepository paymentRepository;
    private final ShippingRepository shippingRepository;
    private final WishlistRepository wishlistRepository;
    private final RoleRepository roleRepository;

    @Override
    public ResponseEntity<Object> findAll() throws Exception {
        try {
            List<Cart> Carts = this.cartRepository.findAllByOrderByCartIdAsc();
            if (Carts.isEmpty()) {
                throw new Exception("Carts not found");
            }
            List<CartResponseDTO> results = this.convertListDTO(Carts);
            logger.info("==================== Logger Start Get All Cart     ====================");
            logger.info(String.valueOf(results));
            logger.info("==================== Logger End Get All Cart    ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("successfully retrieved cart", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.info("==================== Logger Start Get All Cart     ====================");
            logger.error(String.valueOf(ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Cart had no value!")));
            logger.info("==================== Logger End Get All Cart     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Failed retrieved cart!");
        }
    }

    @Override
    public ResponseEntity<Object> findByBuyer(String keyword) throws Exception {
        try {
            List<Cart> Carts = this.cartRepository.findByBuyerUserUsernameIgnoreCaseContainingOrderByCartIdAsc(keyword);
            if (Carts.isEmpty()) {
                throw new Exception("Carts not found");
            }
            List<CartResponseDTO> results = this.convertListDTO(Carts);
            logger.info(Line + "Logger Start Get By Id " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get By Id " + Line);
            return ResponseHandler.generateResponse("successfully find cart", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Failed find cart! ");
        }
    }

    @Override
    public ResponseEntity<Object> findBySeller(String keyword) throws Exception {
        try {
            List<Cart> Carts = this.cartRepository.findByProductSellerUserUsernameIgnoreCaseContainingOrderByCartIdAsc(keyword);
            if (Carts.isEmpty()) {
                throw new Exception("Carts not found");
            }
            List<CartResponseDTO> results = this.convertListDTO(Carts);
            logger.info(Line + "Logger Start Get Seller " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get Seller " + Line);
            return ResponseHandler.generateResponse("successfully find cart", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Failed find cart! ");
        }
    }

    @Override
    public ResponseEntity<Object> findByProductName(String keyword) throws Exception {
        try {
            List<Cart> carts = this.cartRepository.findByProductProductNameIgnoreCaseContainingOrderByCartIdAsc(keyword);
            if (carts.isEmpty()) {
                throw new Exception("Carts not found");
            }
            List<CartResponseDTO> results = this.convertListDTO(carts);
            logger.info(Line + "Logger Start Get Seller " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get Seller " + Line);
            return ResponseHandler.generateResponse("successfully find cart", HttpStatus.NOT_FOUND, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Failed find cart! ");
        }
    }

    @Override
    public ResponseEntity<Object> findByCategory(String keyword) throws Exception {
        try {
            List<Cart> Carts = this.cartRepository.findByProductCategoriesCategoryNameIgnoreCaseContainingOrderByCartIdAsc(keyword);
            if (Carts.isEmpty()) {
                throw new Exception("Cart not found");
            }
            List<CartResponseDTO> results = this.convertListDTO(Carts);

            logger.info(Line + "Logger Start Get Seller " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get Seller " + Line);
            return ResponseHandler.generateResponse("successfully find cart", HttpStatus.NOT_FOUND, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Failed find cart! ");
        }
    }

    @Override
    public ResponseEntity<Object> createCartResponse(CartRequestDTO cartRequestDTO, Authentication authentication) throws Exception {
        try {
            CartResponseDTO result = this.createCart(cartRequestDTO,authentication);
            logger.info(Line + "Logger Start Create " + Line);
            logger.info(String.valueOf(result));
            logger.info(Line + "Logger End Create " + Line);
            return ResponseHandler.generateResponse("successfully create cart", HttpStatus.OK, result);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed create cart!");
        }
    }

    @Override
    public ResponseEntity<Object> wishlistToCart(Authentication authentication) throws Exception {
        try {
            List<Wishlist> wishlists = wishlistRepository.findByBuyerUserUsername(authentication.getName());
            if (wishlists.isEmpty()) {
                throw new Exception("Your wishlist is empty");
            }
            List<CartResponseDTO> carts = new ArrayList<>();
            for (Wishlist wishlist : wishlists) {
                CartRequestDTO cartRequestDTO = new CartRequestDTO();
                cartRequestDTO.setProduct_id(wishlist.getProduct().getId());
                cartRequestDTO.setQuantity(wishlist.getQuantity());
                CartResponseDTO cartResponseDTO = this.createCart(cartRequestDTO, authentication);
                carts.add(cartResponseDTO);
            }
            wishlistRepository.deleteAll(wishlists);
            logger.info(Line + "Logger Start Get By Id " + Line);
            logger.info(String.valueOf(carts));
            logger.info(Line + "Logger End Get By Id " + Line);
            return ResponseHandler.generateResponse("successfully create cart", HttpStatus.OK, carts);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed create cart from wishlist! ");
        }
    }

    @Override
    public ResponseEntity<Object> updateCart(CartRequestDTO cartRequestDTO, Long id, Authentication authentication) throws Exception {
        try {
            if(cartRequestDTO.getProduct_id()==null||cartRequestDTO.getQuantity()==null){
                throw new Exception("Please check again your input, it can't empty");
            }
            if(cartRequestDTO.getQuantity()<=0){
                throw new Exception("Quantity can't be 0 or negatif");
            }
            Cart cart = cartRepository.findById(id).orElseThrow(() -> new Exception("Cart not found"));
            Buyer buyer = buyerRepository.findByUserUsername(authentication.getName()).orElseThrow(() -> new Exception("Buyer not Found"));
            Product product = productRepository.findById(cartRequestDTO.getProduct_id()).orElseThrow(() -> new Exception("Product not Found"));
            if(product.getStock()-cartRequestDTO.getQuantity()<0){
                throw new Exception("Product do not have enough stock to cart");
            }
            List<Role> roles = roleRepository.findByUsersUserUsername(authentication.getName());
            boolean checkRoles = roles.stream().anyMatch(role -> role.getRoleName().equals("ROLE_ADMIN"));
            boolean checkProduct = !product.getSeller().getUser().getUsername().equals(authentication.getName());
            boolean checkCart = cart.getBuyer().getUser().getUsername().equals(authentication.getName());
            boolean checkUser = checkProduct && checkCart;

            Long orderId = cart.getOrder().getOrderId();
            if (checkRoles || checkUser) {
                Cart saveCart = this.requestToEntity(cartRequestDTO, orderId, buyer);
                saveCart.setCartId(id);
                saveCart.setQuantity(cartRequestDTO.getQuantity());
                //update Price
                this.updatePrice(orderId);
                Cart cartResponse = cartRepository.save(saveCart);
                CartResponseDTO results = convertDTO(cartResponse);
                return ResponseHandler.generateResponse("successfully update cart", HttpStatus.OK, results);
            } else {
                throw new Exception("You can't edit other cart or order your own product");
            }
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed update cart!");
        }
    }

    @Override
    public ResponseEntity<Object> deleteById(Long id, Authentication authentication) throws Exception {
        try {
            Cart cart = cartRepository.findById(id).orElseThrow(() -> new Exception("Cart not found"));
            List<Role> roles = roleRepository.findByUsersUserUsername(authentication.getName());
            boolean checkRoles = roles.stream().anyMatch(role -> role.getRoleName().equals("ROLE_ADMIN"));
            boolean checkCart = cart.getBuyer().getUser().getUsername().equals(authentication.getName());
            long orderId = cart.getOrder().getOrderId();
            if (checkRoles || checkCart) {
                cartRepository.deleteById(id);
                List<Cart> checkOrder = cartRepository.findByOrderOrderId(orderId);
                if (checkOrder.isEmpty()) {
                    orderRepository.deleteById(cart.getOrder().getOrderId());
                }
            } else {
                throw new Exception("You can't delete other cart");
            }
            logger.info(Line + "Logger Start Delete By Id " + Line);
            logger.info("Delete Success");
            logger.info(Line + "Logger End Delete By Id " + Line);
            return ResponseHandler.generateResponse("successfully delete cart", HttpStatus.OK, "deleted");
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed delete cart!");
        }
    }

    public List<CartResponseDTO> convertListDTO(List<Cart> carts) {
        return carts.stream()
                .map(this::convertDTO)
                .collect(Collectors.toList());
    }

    public CartResponseDTO convertDTO(Cart cart) {
        List<PhotoProductDTO> photosDTO = cart.getProduct().getPhotos().stream()
                .map(Photo::convertToProduct)
                .collect(Collectors.toList());
        List<Category> categories = this.categoryRepository.findByProductsProductId(cart.getProduct().getId());
        List<String> categoriesDTO = categories.stream()
                .map(Category::getName)
                .collect(Collectors.toList());
        return cart.convertToResponse(photosDTO, categoriesDTO);
    }

    public Cart requestToEntity(CartRequestDTO cartRequestDTO, Long orderId, Buyer buyer) throws Exception {
        Product product = productRepository.findById(cartRequestDTO.getProduct_id()).orElseThrow(() -> new Exception("Product not found"));
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new Exception("Order not found"));
        return cartRequestDTO.convertToEntity(buyer, product, order);
    }

    public void updatePrice(Long orderId) throws Exception {
        Order updatePrice = orderRepository.findById(orderId).orElseThrow(() -> new Exception("Cart not found"));
        if (updatePrice.getListCart() != null) {
            int tempPrice = 0;
            for (Cart cartLoop : updatePrice.getListCart()) {
                int subtotal = cartLoop.getQuantity() * cartLoop.getProduct().getPrice();
                tempPrice += subtotal + updatePrice.getShipping().getPrice();
            }
            updatePrice.setTotalprice(tempPrice);
            orderRepository.save(updatePrice);
        }
    }

    public CartResponseDTO createCart(CartRequestDTO cartRequestDTO, Authentication authentication) throws Exception {
        if(cartRequestDTO.getProduct_id()==null||cartRequestDTO.getQuantity()==null){
            throw new Exception("Please check again your input, it can't empty");
        }
        if(cartRequestDTO.getQuantity()<=0){
            throw new Exception("Quantity can't be 0 or negatif");
        }
        Optional<Order> orderBuyerId = orderRepository.findFirstByListCartBuyerUserUsernameOrderByOrderIdDesc(authentication.getName());
        Product productId = productRepository.findById(cartRequestDTO.getProduct_id()).orElseThrow(() -> new Exception("Product not Found"));
        if(productId.getStock()-cartRequestDTO.getQuantity()<0){
            throw new Exception("Product do not have enough stock to cart");
        }
        Buyer buyer = buyerRepository.findByUserUsername(authentication.getName()).orElseThrow(() -> new Exception("Buyer not Found"));
        if (buyer.getUser().equals(productId.getSeller().getUser())) {
            throw new Exception("You can't order your own product");
        }
        Long orderId;
        if (orderBuyerId.isPresent()) {
            orderId = orderBuyerId.get().getOrderId();
        } else {
            Order newOrder = new Order();
            Long id = 1L;
            Payment payment = this.paymentRepository.findById(id).orElseThrow(Exception::new);
            Shipping shipping = this.shippingRepository.findById(id).orElseThrow(Exception::new);
            newOrder.setShipping(shipping);
            Integer subtotal = (cartRequestDTO.getQuantity() * productId.getPrice());
            newOrder.setTotalprice(subtotal + shipping.getPrice());
            newOrder.setPayment(payment);
            boolean check = false;
            newOrder.setIsPay(check);
            Order saveOrder = orderRepository.save(newOrder);
            orderId = saveOrder.getOrderId();
        }
        Cart saveCart = this.requestToEntity(cartRequestDTO, orderId, buyer);

        //update Price
        this.updatePrice(orderId);
        Cart cartResponse = this.cartRepository.save(saveCart);

        return convertDTO(cartResponse);
    }
}
