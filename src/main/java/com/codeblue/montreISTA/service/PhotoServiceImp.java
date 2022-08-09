package com.codeblue.montreISTA.service;


import com.codeblue.montreISTA.DTO.*;
import com.codeblue.montreISTA.entity.*;
import com.codeblue.montreISTA.repository.PhotoRepository;
import com.codeblue.montreISTA.repository.ProductRepository;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PhotoServiceImp implements PhotoService {
    private PhotoRepository photoRepository;
    private ProductRepository productRepository;

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
    public List<PhotoResponseDTO> findByPhotoName(String photoName) throws Exception {
        List<Photo> photos = photoRepository.findByPhotoNameIgnoreCaseContaining(photoName);
        if (photos == null) {
            throw new Exception("photo not found");
        }
        List<PhotoResponseDTO> results = new ArrayList<>();
        for (Photo data : photos) {
            PhotoResponseDTO photosDTO = data.convertToResponse();
            results.add(photosDTO);
        }
        return results;
    }

    @Override
    public List<PhotoResponseDTO> findByProductName(String productName) throws Exception {
        List<Photo> photos = photoRepository.findByProductProductNameIgnoreCaseContainingOrderByPhotoIdAsc(productName);
        if (photos == null) {
            throw new Exception("photo not found");
        }
        List<PhotoResponseDTO> results = new ArrayList<>();
        for (Photo data : photos) {
            PhotoResponseDTO photosDTO = data.convertToResponse();
            results.add(photosDTO);
        }
        return results;
    }

    @Override
    public List<PhotoResponseDTO> findBySellerName(String keyword) throws Exception {
        List<Photo> photos = photoRepository.findByProductSellerUserIdNameIgnoreCaseContainingOrderByPhotoIdAsc(keyword);
        if (photos == null) {
            throw new Exception("photo not found");
        }
        List<PhotoResponseDTO> results = new ArrayList<>();
        for (Photo data : photos) {
            PhotoResponseDTO photosDTO = data.convertToResponse();
            results.add(photosDTO);
        }
        return results;
    }

    @Override
    public List<PhotoResponseDTO> findByStoreName(String keyword) throws Exception {
        List<Photo> photos = photoRepository.findByProductSellerStoreNameIgnoreCaseContainingOrderByPhotoIdAsc(keyword);
        if (photos == null) {
            throw new Exception("photo not found");
        }
        List<PhotoResponseDTO> results = new ArrayList<>();
        for (Photo data : photos) {
            PhotoResponseDTO photosDTO = data.convertToResponse();
            results.add(photosDTO);
        }
        return results;
    }

    /**
     * belumada validasi optionalphoto by id isPresent, throw
     *
     * @param photoRequestDTO
     * @return
     */
    @Override
    public PhotoResponseDTO createPhoto(PhotoRequestDTO photoRequestDTO) throws Exception {
        Optional<Product> photoproduct = productRepository.findById(photoRequestDTO.getProduct_id());
        if(photoproduct.isPresent()){
            throw new Exception("Product not found");
        }
        /* validation
        if(photoproduct.get().getSeller().getUserId().getName()!=principal.getName)
        {
            throw new Exception("You only can add photo for your product");
        }
        */
        Product product = photoproduct.get();
        Photo savePhoto = photoRequestDTO.convertToEntity(product);
        photoRepository.save(savePhoto);
        return savePhoto.convertToResponse();
    }

    /**
     * note : logic validasi username = username
     *
     * @param photoRequestDTO
     * @param id
     * @return
     */
    @Override
    public PhotoResponseDTO updatePhoto(PhotoRequestDTO photoRequestDTO, Long id) throws Exception {
        Optional<Product> photoproduct = productRepository.findById(photoRequestDTO.getProduct_id());
        Product product = photoproduct.get();
        /* validation
        if(photoproduct.get().getSeller().getUserId().getName()!=principal.getName)
        {
            throw new Exception("You only can add photo for your product");
        }
        */
        Photo photo = photoRepository.findById(photoRequestDTO.getPhoto_id()).orElseThrow(Exception::new);
        Optional<Photo> photoId = photoRepository.findById(id);
        if (photoId.isEmpty()) {
            throw new Exception("Photo not found");
        }
        photo.setProduct(product);
        photo.setPhotoURL(photoRequestDTO.getPhoto_url());
        photo.setPhotoName(photoRequestDTO.getPhoto_name());
        Photo savePhoto = photoRepository.save(photo);
        return savePhoto.convertToResponse();
    }

    @Override
    public void deleteById(Long id) {
        photoRepository.deleteAll();
    }


}