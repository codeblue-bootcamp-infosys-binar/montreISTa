package com.codeblue.montreISTA.service.implement;


import com.codeblue.montreISTA.DTO.*;
import com.codeblue.montreISTA.entity.*;
import com.codeblue.montreISTA.repository.PhotoRepository;
import com.codeblue.montreISTA.repository.ProductRepository;
import com.codeblue.montreISTA.repository.RoleRepository;
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
    private final RoleRepository roleRepository;

    @Override
    public List<PhotoResponseDTO> findAll() throws Exception{
        List<Photo> photos = photoRepository.findAllByOrderByPhotoIdAsc();
        if(photos.isEmpty()){
            throw new Exception("Photo not found");
        }
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
            throw new Exception("Photo not found");
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
        List<PhotoResponseDTO> photos = photoRepository.findByProductSellerSellerIdOrderByPhotoIdAsc(id)
                .stream()
                .map(Photo::convertToResponse)
                .collect(Collectors.toList());
        if (photos.isEmpty()) {
            throw new Exception("Photo not found");
        }
        return photos;
    }

    @Override
    public List<PhotoResponseDTO> findByProductId(Long id) throws Exception {
        List<PhotoResponseDTO> photos = photoRepository.findByProductIdOrderByPhotoIdAsc(id)
                .stream()
                .map(Photo::convertToResponse)
                .collect(Collectors.toList());
        if (photos.isEmpty()) {
            throw new Exception("Photo not found");
        }
        return photos;
    }

    @Override
    public List<PhotoResponseDTO> createPhoto(Long productId,List<MultipartFile> files, Authentication authentication) throws Exception {
        Product product = productRepository.findById(productId).orElseThrow(()->new Exception("Product not found"));
        if(!product.getSeller().getUser().getName().equals(authentication.getName()))
        {
            throw new Exception("You only can add photo for your product");
        }
        List<Photo> photos = photoRepository.findByProductIdOrderByPhotoIdAsc(productId);
        int count = photos.size()+files.size();
        if(count>4){
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
        Photo photo = photoRepository.findById(id).orElseThrow(() -> new Exception("Photo not found"));
        List<Role> roles = roleRepository.findByUsersUserUsername(authentication.getName());
        boolean checkRoles = roles.stream().anyMatch(role -> role.getRoleName().equals("ROLE_ADMIN"));
        boolean checkPhoto = photo.getProduct().getSeller().getUser().getUsername().equals(authentication.getName());
        boolean checkProduct = product.getSeller().getUser().getUsername().equals(authentication.getName());
        boolean checkUser = checkPhoto && checkProduct;
        if (checkRoles || checkUser) {
            photo.setProduct(product);
            photo.setPhotoURL(photoRequestDTO.getPhoto_url());
            Photo savePhoto = photoRepository.save(photo);
            return savePhoto.convertToResponse();
        }else{
            throw new Exception("You can't update photo for other photo/product");
        }
    }

    @Override
    public void deleteById(Long id, Authentication authentication)throws Exception {
        Photo photo = photoRepository.findById(id).orElseThrow(() -> new Exception("Photo not found"));
        List<Role> roles = roleRepository.findByUsersUserUsername(authentication.getName());
        boolean checkRoles = roles.stream().anyMatch(role -> role.getRoleName().equals("ROLE_ADMIN"));
        boolean checkPhoto = photo.getProduct().getSeller().getUser().getUsername().equals(authentication.getName());
        if (checkRoles || checkPhoto) {
            photoRepository.deleteById(id);
        }else{
            throw new Exception("You only can delete photo for your product");
        }
    }


}