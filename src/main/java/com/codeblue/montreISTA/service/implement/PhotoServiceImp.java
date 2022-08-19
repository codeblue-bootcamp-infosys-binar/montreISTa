package com.codeblue.montreISTA.service.implement;


import com.codeblue.montreISTA.DTO.*;
import com.codeblue.montreISTA.entity.*;
import com.codeblue.montreISTA.repository.PhotoRepository;
import com.codeblue.montreISTA.repository.ProductRepository;
import com.codeblue.montreISTA.service.PhotoService;
import lombok.*;
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

    @Override
    public List<PhotoResponseDTO> findAll() {
        List<Photo> photos = photoRepository.findAllByOrderByPhotoIdAsc();
        List<PhotoResponseDTO> results = new ArrayList<>();
        for (Photo data : photos) {
            PhotoResponseDTO photosDTO = data.convertToResponse();
            results.add(photosDTO);
        }
        return results;
    }


    @Override
    public List<PhotoResponseDTO> findBySellerName(Authentication authentication) throws Exception {
        List<Photo> photos = photoRepository.findByProductSellerUserNameIgnoreCaseContainingOrderByPhotoIdAsc(authentication.getName());
        if (photos.isEmpty()) {
            throw new Exception("photo not found");
        }
        return photos.stream()
                .map(Photo::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PhotoResponseDTO findById(Long id) throws Exception {
        return photoRepository.findById(id).orElseThrow(()->new Exception("Photo Not Found")).convertToResponse();
    }

    @Override
    public List<PhotoResponseDTO> findBySellerId(Long id) throws Exception {
        return photoRepository.findByProductSellerSellerIdOrderByPhotoIdAsc(id)
                .stream()
                .map(Photo::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PhotoResponseDTO> findByProductId(Long id) throws Exception {
        return photoRepository.findByProductProductIdOrderByPhotoIdAsc(id)
                .stream()
                .map(Photo::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<PhotoResponseDTO> createPhoto(Long productId,List<MultipartFile> files, Authentication authentication) throws Exception {
        Product product = productRepository.findById(productId).orElseThrow(()->new Exception("Product not found"));
        if(!product.getSeller().getUser().getName().equals(authentication.getName()))
        {
            throw new Exception("You only can add photo for your product");
        }
        List<Photo> photos = photoRepository.findByProductProductIdOrderByPhotoIdAsc(productId);
        int count = photos.size()+files.size();
        if(count>=4){
            throw new Exception("Product can only have 4 photos");
        }
        List<PhotoResponseDTO> photoDTO = new ArrayList<>();
        for(MultipartFile file:files){
                Photo photo = new Photo();
                String url = cloudinaryService.uploadFile(file);
                photo.setPhotoURL(url);
                photo.setProduct(product);
                Photo photoSave = photoRepository.save(photo);
                photoDTO.add(photoSave.convertToResponse());
        }
        return photoDTO;
    }

    /**
     */
    @Override
    public PhotoResponseDTO updatePhoto(PhotoRequestDTO photoRequestDTO, Long id,Authentication authentication) throws Exception {
        Product product = productRepository.findById(photoRequestDTO.getProduct_id()).orElseThrow(() -> new Exception("Product not found"));
        if(!product.getSeller().getUser().getName().equals(authentication.getName()))
        {
            throw new Exception("You only can update photo for your product");
        }
        Photo photo = photoRepository.findById(id).orElseThrow(() -> new Exception("Photo not found"));
        photo.setProduct(product);
        photo.setPhotoURL(photoRequestDTO.getPhoto_url());
        Photo savePhoto = photoRepository.save(photo);
        return savePhoto.convertToResponse();
    }

    @Override
    public void deleteById(Long id, Authentication authentication)throws Exception {
        Photo photo = photoRepository.findById(id).orElseThrow(() -> new Exception("Photo not found"));
        if(!photo.getProduct().getSeller().getUser().getUsername().equals(authentication.getName()))
        {
            throw new Exception("You only can delete photo for your product");
        }
        photoRepository.deleteById(id);
    }


}