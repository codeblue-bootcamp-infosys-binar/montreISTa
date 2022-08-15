package com.codeblue.montreISTA.service.implement;

import com.codeblue.montreISTA.DTO.WishlistRequestDTO;
import com.codeblue.montreISTA.DTO.WishlistResponseDTO;
import com.codeblue.montreISTA.entity.Buyer;
import com.codeblue.montreISTA.entity.Product;
import com.codeblue.montreISTA.entity.Wishlist;
import com.codeblue.montreISTA.helper.DTOConverter;
import com.codeblue.montreISTA.repository.BuyerRepository;
import com.codeblue.montreISTA.repository.ProductRepository;
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

    @Override
    public List<WishlistResponseDTO> findAllWishlist() throws Exception{
        List<Wishlist> wishlists = wishlistRepository.findAllByOrderByWishlistIdAsc();
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
        if(buyer.getUser().getUserId().equals(product.getSeller().getUserId().getUserId())){
            throw new Exception("You can't order your own product honey");
        }
        Wishlist wishlistSave = wishlist.convertToEntity(buyer,product);
        Wishlist wishlistDTO = wishlistRepository.save(wishlistSave);
        return DTOConverter.convertWishlist(wishlistDTO);
    }

    @Override
    public WishlistResponseDTO updateWishlist(WishlistRequestDTO wishlist, Long id, Authentication authentication) throws Exception{
        Wishlist updateWishlist = wishlistRepository.findById(id).orElseThrow(()->new Exception("Wishlist not found"));
        Buyer buyer = buyerRepository.findByUserUsername(authentication.getName()).orElseThrow(()->new Exception("Please login as buyer"));
        Product product = productRepository.findById(wishlist.getProductId()).orElseThrow(()->new Exception("Product not found"));
        if(buyer!=updateWishlist.getBuyer()){
            throw new Exception("You only can edit your wishlist");
        }
        updateWishlist.setWishlistId(id);
        updateWishlist.setBuyer(buyer);
        updateWishlist.setProduct(product);
        updateWishlist.setQuantity(wishlist.getQuantity());
        Wishlist wishlistDTO = wishlistRepository.save(updateWishlist);
        return DTOConverter.convertWishlist(wishlistDTO);

    }

    @Override
    public void deleteWishlist(Long id,Authentication authentication) throws Exception {
        Wishlist wishlist = wishlistRepository.findById(id).orElseThrow(()->new Exception("Wishlist not found"));
        Buyer buyer = buyerRepository.findByUserUsername(authentication.getName()).orElseThrow(()->new Exception("Please login as buyer"));
        if(buyer!=wishlist.getBuyer()){
            throw new Exception("You only can delete your wishlist");
        }
        wishlistRepository.deleteById(id);
    }

    @Override
    public List<Wishlist> findByBuyerUserName(String keyword) {
        return wishlistRepository.findByBuyerUserName(keyword);
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
