package com.codeblue.montreISTA.controller;



import com.codeblue.montreISTA.DTO.WishlistRequestDTO;
import com.codeblue.montreISTA.DTO.WishlistResponseDTO;
import com.codeblue.montreISTA.entity.Buyer;
import com.codeblue.montreISTA.entity.Product;
import com.codeblue.montreISTA.entity.Wishlist;
import com.codeblue.montreISTA.helper.DTOConverter;
import com.codeblue.montreISTA.repository.BuyerRepository;
import com.codeblue.montreISTA.repository.ProductRepository;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.WishlistService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@Tag(name="07. Wishlist")
public class WishlistController {

    private static final Logger logger = LoggerFactory.getLogger(WishlistController.class);

    private static final String Line = "====================";

    private BuyerRepository buyerRepository;

    private ProductRepository productRepository;
    private WishlistService wishlistService;

    //GET ALL
    @GetMapping("/user/wishlist")
    public ResponseEntity<Object> getAllWishlist(){
        try{
            List<Wishlist> wishlists = wishlistService.findAllWishlist();
            List <WishlistResponseDTO> wishlistResponseDTOS = wishlists.stream().map(DTOConverter::convertWishlist)
                    .collect(Collectors.toList());
            logger.info(Line + " Logger Start Find All Wishlist " + Line);
            for (Wishlist dataresults:wishlists){
                logger.info("================================");
                logger.info("Wishlist id :"+dataresults.getWishlistId());
                logger.info("Buyer ID :"+dataresults.getBuyer());
                logger.info("Product ID :"+dataresults.getProduct());
                logger.info("Quantity :"+dataresults.getQuantity());
                logger.info("================================");
            }
            logger.info(Line + "Logger End Find All Wishlist" + Line);
//            for (Wishlist wishlist : wishlists) {
//                 Product product = wishlist.getProduct();
////                 products.add(product);
//                 ProductResponseDTO productDTO = DTOConverter.convertOneProducts(product);
//
//                 WishlistResponseDTO wishlistDTO = wishlist.convertToResponse(productDTO);
//                 wishlistResponseDTOS.add(wishlistDTO);
//            }
            return ResponseHandler.generateResponse("success get all wishlist", HttpStatus.OK, wishlistResponseDTOS);
        } catch (Exception e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Table has no value!");
        }
    }

    //GET ALL BY SELLER ID
    @GetMapping("/user/wishlist/store/{wishlist_id}")
    public ResponseEntity<Object> getAllWishlistByWishlistId(@PathVariable("wishlist_id") Long wishlistId){
        try{
            List<Wishlist> wishlist = wishlistService.findWishlisttByWishlistId(wishlistId);
            List <WishlistResponseDTO> wishlistResponseDTOS = wishlist.stream().map(DTOConverter::convertWishlist)
                    .collect(Collectors.toList());
            logger.info(Line + "Logger Start Get by wishlist id " + Line);
            logger.info(String.valueOf(wishlistResponseDTOS));
            logger.info(Line + "Logger End Get by wishlist id " + Line);
            return ResponseHandler.generateResponse("success get by wishlist id", HttpStatus.OK, wishlistResponseDTOS);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND, "Wishlist had no value!");
        }
    }

    @GetMapping("/user/wishlist/buyer/username")
    public ResponseEntity<Object> findByBuyerUsername(@Param("keyword") String keyword){
        try{
            List<Wishlist> wishlists = wishlistService.findByBuyerUserName(keyword);
            List <WishlistResponseDTO> wishlistResponseDTOS = wishlists.stream().map(DTOConverter::convertWishlist)
                    .collect(Collectors.toList());
            logger.info(Line + "Logger Start Get buyer username " + Line);
            logger.info(String.valueOf(wishlistResponseDTOS));
            logger.info(Line + "Logger End Get buyer username " + Line);
            return ResponseHandler.generateResponse("success get by buyer username", HttpStatus.OK, wishlistResponseDTOS);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Wishlist had no value!");
        }
    }

    @GetMapping("/user/wishlist/Buyer/User/username")
    public ResponseEntity<Object> findByBuyerUserUsername(@Param("keyword") String keyword){
        try{
            List<Wishlist> wishlists = wishlistService.findByBuyerUserUsername(keyword);
            List <WishlistResponseDTO> wishlistResponseDTOS = wishlists.stream().map(DTOConverter::convertWishlist)
                    .collect(Collectors.toList());
            logger.info(Line + "Logger Start Get buyer user username " + Line);
            logger.info(String.valueOf(wishlistResponseDTOS));
            logger.info(Line + "Logger End Get buyer user username " + Line);
            return ResponseHandler.generateResponse("success get by buyer user username", HttpStatus.OK, wishlistResponseDTOS);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Wishlist had no value!");
        }
    }

    //GET ONE BY ID
    @GetMapping("/user/wishlist/{id}")
    public ResponseEntity<Object> getWishlistById(@PathVariable("id") Long id){
        try{
            Optional<Wishlist> wishlist = wishlistService.findWishlistById(id);
            WishlistResponseDTO wishlistResponseDTO = DTOConverter.convertWishlist(wishlist.get());
            logger.info(Line + "Logger Start Get By Id " + Line);
            logger.info(String.valueOf(wishlistResponseDTO));
            logger.info(Line + "Logger End Get By Id " + Line);
            return ResponseHandler.generateResponse("success get by id", HttpStatus.OK, wishlistResponseDTO);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Wishlist had no value!");
        }
    }

    //CREATE
    @PostMapping("/user/wishlist/create")
    public ResponseEntity<Object> createWishlist(@RequestBody WishlistRequestDTO wishlistRequestDTO)throws Exception{
        try {
            Buyer buyer = buyerRepository.findById(wishlistRequestDTO.getBuyerId()).orElseThrow(Exception::new);
            Product product = productRepository.findById(wishlistRequestDTO.getProductId()).orElseThrow(Exception::new);
            Wishlist wishlist1 = wishlistRequestDTO.convertToEntity(buyer,product);
            Wishlist wishlist = wishlistService.createWishlist(wishlist1);
            WishlistResponseDTO wishlistResponseDTO = DTOConverter.convertWishlist(wishlist);
            logger.info(Line + "Logger Start Create " + Line);
            logger.info(String.valueOf(wishlistResponseDTO));
            logger.info(Line + "Logger End Create " + Line);
            return ResponseHandler.generateResponse("successfully create wishlist", HttpStatus.CREATED, wishlistResponseDTO);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST,"Failed create wishlist!");
        }
    }

    //UPDATE
    @PutMapping("/user/wishlist/update/{id}")
    public ResponseEntity<Object> updateWishlist(@RequestBody WishlistRequestDTO wishlistRequestDTO, @PathVariable("id") Long id){
        try{
            Optional<Wishlist> targetWishlist = wishlistService.findWishlistById(id);
            Buyer buyer = buyerRepository.findById(wishlistRequestDTO.getBuyerId()).orElseThrow(Exception::new);
            Product product = productRepository.findById(wishlistRequestDTO.getProductId()).orElseThrow(Exception::new);
            Wishlist updateWishlist = targetWishlist.get();
            updateWishlist.setWishlistId(id);
            updateWishlist.setBuyer(buyer);
            updateWishlist.setProduct(product);
            wishlistService.updateWishlist(updateWishlist);
            WishlistResponseDTO wishlistResponseDTO = DTOConverter.convertWishlist(targetWishlist.get());
            logger.info(Line + "Logger Start Update By Id " + Line);
            logger.info(String.valueOf(targetWishlist));
            logger.info(Line + "Logger End Update By Id " + Line);
            return ResponseHandler.generateResponse("successfully updated Wishlist", HttpStatus.OK,targetWishlist );
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed update wishlist!");
        }
    }

    //DELETE
    @DeleteMapping("/user/wishlist/delete/{id}")
    public ResponseEntity<Object> deleteWishlist(@PathVariable("id") Long id){
        try{
            wishlistService.deleteWishlist(id);
            logger.info(Line + "Logger Start Delete By Id " + Line);
            logger.info("Delete Success");
            logger.info(Line + "Logger End Delete By Id " + Line);
            return ResponseHandler.generateResponse("successfully deleted wishlist", HttpStatus.OK, null);
        } catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed delete wishlist!");
        }

    }

}
