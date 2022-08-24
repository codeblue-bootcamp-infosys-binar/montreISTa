package com.codeblue.montreISTA.service.implement;


import com.codeblue.montreISTA.DTO.*;
import com.codeblue.montreISTA.controller.PhotoController;
import com.codeblue.montreISTA.entity.*;
import com.codeblue.montreISTA.repository.PhotoRepository;
import com.codeblue.montreISTA.repository.ProductRepository;
import com.codeblue.montreISTA.repository.RoleRepository;
import com.codeblue.montreISTA.response.ResponseHandler;
import com.codeblue.montreISTA.service.PhotoService;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PhotoServiceImp implements PhotoService {
    private final PhotoRepository photoRepository;
    private final ProductRepository productRepository;
    private final CloudinaryService cloudinaryService;
    private final RoleRepository roleRepository;
    private static final Logger logger = LoggerFactory.getLogger(PhotoController.class);
    private static final String Line = "====================";

    @Override
    public ResponseEntity<Object> findAll() throws Exception {
        try {
            List<Photo> photos = photoRepository.findAllByOrderByPhotoIdAsc();
            if (photos.isEmpty()) {
                throw new Exception("Photo not found");
            }
            List<PhotoResponseDTO> results = new ArrayList<>();
            for (Photo data : photos) {
                PhotoResponseDTO photosDTO = data.convertToResponse();
                results.add(photosDTO);
            }
            logger.info("==================== Logger Start Get All Photo     ====================");
            for (PhotoResponseDTO photoData : results) {
                logger.info("-------------------------");
                logger.info("Photo ID      : " + photoData.getPhoto_id());
                logger.info("Photo Url     : " + photoData.getPhoto_url());
                logger.info("Product ID    : " + photoData.getProduct_id());
            }
            logger.info("==================== Logger End Get All Photo   ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse("successfully retrieved products", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.info("==================== Logger Start Get All Photo     ====================");
            logger.error(String.valueOf(e.getMessage()));
            logger.info("==================== Logger End Get All Photo     ====================");
            logger.info(" ");
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Photo had no value!");
        }
    }


    @Override
    public ResponseEntity<Object> findBySellerName(Authentication authentication) {
        try {
            List<Photo> photos = photoRepository.findByProductSellerUserNameIgnoreCaseContainingOrderByPhotoIdAsc(authentication.getName());
            if (photos.isEmpty()) {
                throw new Exception("Photo not found");
            }
            List<PhotoResponseDTO> results = photos.stream()
                    .map(Photo::convertToResponse)
                    .collect(Collectors.toList());
            logger.info(Line + "Logger Start Get By seller name " + Line);
            logger.info(String.valueOf(results));
            logger.info(Line + "Logger End Get By seller name " + Line);
            return ResponseHandler.generateResponse("successfully retrieved photo", HttpStatus.OK, results);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Photo had no value!");
        }
    }

    @Override
    public ResponseEntity<Object> findById(Long id) throws Exception {
        try {
            PhotoResponseDTO result = photoRepository.findById(id).orElseThrow(() -> new Exception("Photo Not Found")).convertToResponse();
            logger.info(Line + "Logger Start Find By Id " + Line);
            logger.info(String.valueOf(result));
            logger.info(Line + "Logger End Find By Id " + Line);
            return ResponseHandler.generateResponse("successfully retrieved photo", HttpStatus.OK, result);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Failed find photo");
        }
    }

    @Override
    public ResponseEntity<Object> findBySellerId(Long id) {
        try {
            List<PhotoResponseDTO> photos = photoRepository.findByProductSellerSellerIdOrderByPhotoIdAsc(id)
                    .stream()
                    .map(Photo::convertToResponse).toList();
            if (photos.isEmpty()) {
                throw new Exception("Photo not found");
            }
            logger.info(Line + "Logger Start Find By Seller Id " + Line);
            logger.info(String.valueOf(photos));
            logger.info(Line + "Logger End Find By Seller Id " + Line);
            return ResponseHandler.generateResponse("successfully retrieved photo", HttpStatus.OK, photos);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Failed find photo");
        }
    }

    @Override
    public ResponseEntity<Object> findByProductId(Long id) throws Exception {
        try {
            List<PhotoResponseDTO> photos = photoRepository.findByProductIdOrderByPhotoIdAsc(id)
                    .stream()
                    .map(Photo::convertToResponse)
                    .collect(Collectors.toList());
            if (photos.isEmpty()) {
                throw new Exception("Photo not found");
            }
            logger.info(Line + "Logger Start Find By Product Id " + Line);
            logger.info(String.valueOf(photos));
            logger.info(Line + "Logger End Find By Product Id " + Line);
            return ResponseHandler.generateResponse("successfully retrieved photo", HttpStatus.OK, photos);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Failed find photo");
        }
    }

    @Override
    public ResponseEntity<Object> createPhoto(Long productId, List<MultipartFile> files, Authentication authentication) {
        try {
            Product product = productRepository.findById(productId).orElseThrow(() -> new Exception("Product not found"));
            if (!product.getSeller().getUser().getName().equals(authentication.getName())) {
                throw new Exception("You only can add photo for your product");
            }
            List<Photo> photos = photoRepository.findByProductIdOrderByPhotoIdAsc(productId);
            int count = photos.size() + files.size();
            if (count > 4) {
                throw new Exception("Product can only have 4 photos");
            }
            List<PhotoResponseDTO> photoDTO = new ArrayList<>();
            for (MultipartFile file : files) {
                Photo photo = new Photo();
                String url = cloudinaryService.uploadFile(file);
                photo.setPhotoURL(url);
                photo.setProduct(product);
                Photo photoSave = photoRepository.save(photo);
                photoDTO.add(photoSave.convertToResponse());
            }
            logger.info(Line + "Logger Start Query " + Line);
            logger.info(String.valueOf(photoDTO));
            logger.info(Line + "Logger End Query " + Line);
            return ResponseHandler.generateResponse("successfully create photo", HttpStatus.OK, photoDTO);
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed upload!");
        }
    }

    @Override
    public ResponseEntity<Object> updatePhoto(MultipartFile file, Long productId, Long photoId, Authentication authentication) {
        try {
            Product product = productRepository.findById(productId).orElseThrow(() -> new Exception("Product not found"));
            Photo photo = photoRepository.findById(photoId).orElseThrow(() -> new Exception("Photo not found"));
            List<Role> roles = roleRepository.findByUsersUserUsername(authentication.getName());
            boolean checkRoles = roles.stream().anyMatch(role -> role.getRoleName().equals("ROLE_ADMIN"));
            boolean checkPhoto = photo.getProduct().getSeller().getUser().getUsername().equals(authentication.getName());
            boolean checkProduct = product.getSeller().getUser().getUsername().equals(authentication.getName());
            boolean checkUser = checkPhoto && checkProduct;
            String url = cloudinaryService.uploadFile(file);
            if (checkRoles || checkUser) {
                photo.setProduct(product);
                photo.setPhotoURL(url);
                Photo savePhoto = photoRepository.save(photo);
                PhotoResponseDTO result = savePhoto.convertToResponse();
                logger.info(Line + "Logger Start By Id " + Line);
                logger.info(String.valueOf(result));
                logger.info(Line + "Logger End By Id " + Line);
                return ResponseHandler.generateResponse("successfully update product", HttpStatus.OK, result);
            } else {
                throw new Exception("You can't update photo for other photo/product");
            }
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Photo had no value!");
        }
    }

    @Override
    public ResponseEntity<Object> deleteById(Long id, Authentication authentication) throws Exception {
        try {
            Photo photo = photoRepository.findById(id).orElseThrow(() -> new Exception("Photo not found"));
            List<Role> roles = roleRepository.findByUsersUserUsername(authentication.getName());
            boolean checkRoles = roles.stream().anyMatch(role -> role.getRoleName().equals("ROLE_ADMIN"));
            boolean checkPhoto = photo.getProduct().getSeller().getUser().getUsername().equals(authentication.getName());
            if (checkRoles || checkPhoto) {
                photoRepository.deleteById(id);
            } else {
                throw new Exception("You only can delete photo for your product");
            }
            logger.info(Line + "Logger Start Delete By Id " + Line);
            logger.info("Delete Success");
            logger.info(Line + "Logger End Delete By Id " + Line);
            return ResponseHandler.generateResponse("successfully delete product", HttpStatus.OK, "deleted");
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed delete photo!");
        }
    }
}