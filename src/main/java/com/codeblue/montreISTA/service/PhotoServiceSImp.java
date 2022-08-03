package com.codeblue.montreISTA.service;

import DTO.PhotoPostDTO;
import DTO.PhotoRequestDTO;
import DTO.PhotoResponseDTO;
import com.codeblue.montreISTA.entity.Photo;
import com.codeblue.montreISTA.repository.PhotoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PhotoServiceSImp implements PhotoServices {
    private PhotoRepository photoRepository;

    @Override
    public List<PhotoResponseDTO> findAll() {
        List<Photo> photos = photoRepository.findAll();
        List<PhotoResponseDTO> results = new ArrayList<>();
        for(Photo data:photos){
            PhotoResponseDTO photosDTO = data.convertToResponse();
            results.add(photosDTO);
        }
        return results;
    }

    @Override
    public List<PhotoResponseDTO> findByPhotoName(String photoName) {
        List<Photo> photos = photoRepository.findByPhotoName(photoName);
        List<PhotoResponseDTO> results = new ArrayList<>();
        for(Photo data:photos){
            PhotoResponseDTO photosDTO = data.convertToResponse();
            results.add(photosDTO);
        }
        return results;
    }

    @Override
    public List<PhotoResponseDTO> findByProductName(String productName) {
        return null;
    }

    @Override
    public List<PhotoResponseDTO> findByUsername(String name) {
        return null;
    }
    /**
     * belumada validasi optionalphoto by id isPresent, throw
     * @param photoRequestDTO
     * @return
     */
    @Override
    public PhotoPostDTO createPhoto(PhotoRequestDTO photoRequestDTO) {
        Photo savePhoto = photoRequestDTO.convertToEntity();
        photoRepository.save(savePhoto);
        return savePhoto.convertToPost();
    }

    /**
     * note : logic validasi username = username
     * @param photoRequestDTO
     * @param id
     * @return
     */
    @Override
    public PhotoResponseDTO updatePhoto(PhotoRequestDTO photoRequestDTO,Long id){
        Photo photo = photoRequestDTO.convertToEntity();
        Optional<Photo> photoId = photoRepository.findById(photo.getPhotoId());
//        if(photoId.isEmpty()){
//            throw new Exception("Photo not found");
//        }
//        photo.setPhotoId(id);
        Photo savePhoto = photoRepository.save(photo);
        return savePhoto.convertToResponse();
    }

    @Override
    public void deleteById(Long id) {
        photoRepository.deleteById(id);
    }

    @Override
    public long deleteByPhotoName(String photoName) {
       return photoRepository.deleteByPhotoName(photoName);
    }

    @Override
    public void deletedByProductName(String name) {

    }

    @Override
    public void deleteByUsername(String name) {

    }
}
