package com.codeblue.montreISTA.service.implement;


import com.codeblue.montreISTA.DTO.*;
import com.codeblue.montreISTA.entity.*;
import com.codeblue.montreISTA.repository.PhotoRepository;
import com.codeblue.montreISTA.repository.ProductRepository;
import com.codeblue.montreISTA.service.PhotoService;
import lombok.*;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PhotoServiceImp implements PhotoService {
    private final PhotoRepository photoRepository;
    private final ProductRepository productRepository;

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
    public List<PhotoResponseDTO> findBySellerName(String keyword) throws Exception {
        List<Photo> photos = photoRepository.findByProductSellerUserIdNameIgnoreCaseContainingOrderByPhotoIdAsc(keyword);
        if (photos == null) {
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

    /**
     * belumada validasi optionalphoto by id isPresent, throw
     *
     */
    @Override
    public PhotoResponseDTO createPhoto(PhotoRequestDTO photoRequestDTO) throws Exception {
        Optional<Product> photoproduct = productRepository.findById(photoRequestDTO.getProduct_id());
        if(photoproduct.isEmpty()){
            throw new Exception("Product not found");
        }
        List<Photo> photos = photoRepository.findByProductProductIdOrderByPhotoIdAsc(photoRequestDTO.getProduct_id());
        int count = photos.size();
        if(count>=4){
            throw new Exception("Product can only have 4 photos");
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
        Product product = productRepository.findById(photoRequestDTO.getProduct_id()).orElseThrow(() -> new Exception("Product not found"));
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
        photoRepository.deleteById(id);
    }


}