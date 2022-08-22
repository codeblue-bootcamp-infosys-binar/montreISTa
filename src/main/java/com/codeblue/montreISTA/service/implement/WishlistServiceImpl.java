package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.WishlistRequestDTO;
import com.codeblue.montreISTA.DTO.WishlistResponseDTO;
import com.codeblue.montreISTA.entity.Buyer;
import com.codeblue.montreISTA.entity.Product;
import com.codeblue.montreISTA.entity.Role;
import com.codeblue.montreISTA.entity.Wishlist;
import com.codeblue.montreISTA.helper.DTOConverter;
import com.codeblue.montreISTA.repository.BuyerRepository;
import com.codeblue.montreISTA.repository.ProductRepository;
import com.codeblue.montreISTA.repository.RoleRepository;
import com.codeblue.montreISTA.repository.WishlistRepository;
import com.codeblue.montreISTA.service.WishlistService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final BuyerRepository buyerRepository;
    private final ProductRepository productRepository;
    private final RoleRepository roleRepository;

    @Override
    public List<WishlistResponseDTO> findAllWishlist() throws Exception{
        List<Wishlist> wishlists = wishlistRepository.findAllByOrderByWishlistIdAsc();
        if(wishlists.isEmpty()){
            throw new Exception("wishlist not found");
        }
        return wishlists.stream().map(DTOConverter::convertWishlist)
                .collect(Collectors.toList());
    }

    @Override
    public WishlistResponseDTO findWishlistById(Long id) throws Exception{
        Wishlist wishlist = wishlistRepository.findById(id).orElseThrow(()->new Exception("Wishlist not found"));
        return DTOConverter.convertWishlist(wishlist);
    }

    @Override
    public WishlistResponseDTO createWishlist(WishlistRequestDTO wishlist, Authentication authentication) throws Exception{
        Buyer buyer = buyerRepository.findByUserUsername(authentication.getName()).orElseThrow(()->new Exception("Please login as buyer"));
        Product product = productRepository.findById(wishlist.getProductId()).orElseThrow(()->new Exception("Product not found"));
        if(buyer.getUser().getUserId().equals(product.getSeller().getUser().getUserId())){
            throw new Exception("You can't order your own product");
        }
        Wishlist wishlistSave = wishlist.convertToEntity(buyer,product);
        Wishlist wishlistDTO = wishlistRepository.save(wishlistSave);
        return DTOConverter.convertWishlist(wishlistDTO);
    }

    @Override
    public WishlistResponseDTO updateWishlist(WishlistRequestDTO wishlistRequestDTO, Long id, Authentication authentication) throws Exception{
        Wishlist wishlist = wishlistRepository.findById(id).orElseThrow(()->new Exception("Wishlist not found"));
        Buyer buyer = buyerRepository.findByUserUsername(authentication.getName()).orElseThrow(()->new Exception("Please login as buyer"));
        Product product = productRepository.findById(wishlistRequestDTO.getProductId()).orElseThrow(()->new Exception("Product not found"));
        List<Role> roles = roleRepository.findByUsersUserUsername(authentication.getName());
        boolean checkRoles = roles.stream().anyMatch(role -> role.getRoleName().equals("ROLE_ADMIN"));
        boolean checkProduct = !product.getSeller().getUser().getUsername().equals(authentication.getName());
        boolean checkWishlist = wishlist.getBuyer().getUser().getUsername().equals(authentication.getName());
        boolean checkUser = checkProduct && checkWishlist;
        if (checkRoles || checkUser) {
            wishlist.setWishlistId(id);
            wishlist.setBuyer(buyer);
            wishlist.setProduct(product);
            wishlist.setQuantity(wishlistRequestDTO.getQuantity());
            Wishlist wishlistDTO = wishlistRepository.save(wishlist);
            return DTOConverter.convertWishlist(wishlistDTO);
        }else{
            throw new Exception("You can't edit other wishlist or order your own product");
        }
    }

    @Override
    public void deleteWishlist(Long id,Authentication authentication) throws Exception {
        Wishlist wishlist = wishlistRepository.findById(id).orElseThrow(()->new Exception("Wishlist not found"));
        List<Role> roles = roleRepository.findByUsersUserUsername(authentication.getName());
        boolean checkRoles = roles.stream().anyMatch(role -> role.getRoleName().equals("ROLE_ADMIN"));
        boolean checkBuyer = wishlist.getBuyer().getUser().getUsername().equals(authentication.getName());
        if (checkRoles || checkBuyer) {
            wishlistRepository.deleteById(id);
        }else {
            throw new Exception("You only can delete your wishlist");
        }
    }

    @Override
    public List<WishlistResponseDTO> findByBuyerUserUsername(Authentication authentication)throws Exception {
        List<Wishlist> wishlists = wishlistRepository.findByBuyerUserUsername(authentication.getName());
        if (wishlists.isEmpty()){
            throw new Exception("Please add Wishlist");
        }
        return wishlists
                .stream()
                .map(DTOConverter::convertWishlist)
                .collect(Collectors.toList());
    }
}
