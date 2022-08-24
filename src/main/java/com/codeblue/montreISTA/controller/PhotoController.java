package com.codeblue.montreISTA.controller;

import com.codeblue.montreISTA.entity.Product;
import com.codeblue.montreISTA.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.codeblue.montreISTA.service.PhotoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@AllArgsConstructor
@Tag(name = "04. Photo")
@SecurityRequirement(name = "bearer-key")
public class PhotoController {

    private final PhotoService photoService;
    private final ProductService productService;


    @GetMapping("/user/my-photo-product")
    public ResponseEntity<Object> findBySellerName(Authentication authentication) throws Exception {
        return photoService.findBySellerName(authentication);
    }

    @PostMapping(value = "/user/upload-photo/product-id", consumes = "multipart/form-data")
    public ResponseEntity<Object> postPhotoProduct(@RequestParam("file") List<MultipartFile> files,
                                                   @RequestParam Long productId,
                                                   Authentication authentication) throws Exception {
        return photoService.createPhoto(productId, files, authentication);
    }

    @PostMapping(value = "/user/upload-photo/last-product-seller", consumes = "multipart/form-data")
    public ResponseEntity<Object> postPhoto(@RequestParam("file") List<MultipartFile> files,
                                            Authentication authentication) throws Exception {
        Product product = productService.findBySellerUsername(authentication.getName());
        return photoService.createPhoto(product.getId(), files, authentication);
    }

    @PutMapping(value = "/user/photo/edit/{photoId}", consumes = "multipart/form-data")
    public ResponseEntity<Object> updatePhoto(@RequestParam("file") MultipartFile file,
                                              @PathVariable Long photoId,
                                              @RequestParam Long productId,
                                              Authentication authentication) throws Exception {
        return photoService.updatePhoto(file, productId, photoId, authentication);
    }

    @DeleteMapping("/user/photo/delete/{id}")
    public ResponseEntity<Object> deletePhoto(@PathVariable Long id, Authentication authentication) throws Exception {

        return photoService.deleteById(id, authentication);
    }

    @GetMapping("/dashboard/photo/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) throws Exception {
        return photoService.findById(id);
    }

    @GetMapping("/dashboard/photo/product/{id}")
    public ResponseEntity<Object> findByProduct(@PathVariable Long id) throws Exception {
        return photoService.findByProductId(id);
    }

    @GetMapping("/dashboard/photo/seller/{id}")
    public ResponseEntity<Object> findBySellerId(@PathVariable Long id) throws Exception {
        return photoService.findBySellerId(id);
    }

    @GetMapping("/dashboard/photos")
    public ResponseEntity<Object> findAll() throws Exception {
        return photoService.findAll();
    }
}
