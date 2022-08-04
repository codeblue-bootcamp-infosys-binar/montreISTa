package com.codeblue.montreISTA.service;


import com.codeblue.montreISTA.DTO.PhotoPostDTO;
import com.codeblue.montreISTA.DTO.PhotoRequestDTO;
import com.codeblue.montreISTA.DTO.PhotoResponseDTO;
import com.codeblue.montreISTA.entity.Photo;
import com.codeblue.montreISTA.repository.PhotoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PhotoServiceImp implements PhotoService {
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
    public List<PhotoResponseDTO> findByPhotoName(String photoName) throws Exception {
        List<Photo> photos = photoRepository.findByPhotoName(photoName);
        if(photos == null) {
        throw new Exception("photo not found");
        }
        List<PhotoResponseDTO> results = new ArrayList<>();
        for(Photo data:photos){
            PhotoResponseDTO photosDTO = data.convertToResponse();
            results.add(photosDTO);
        }
        return results;
    }

    @Override
    public List<PhotoResponseDTO> findByProductName(String productName) throws Exception{
        List<Photo> photos = photoRepository.findByProductName(productName);
        if(photos == null) {
            throw new Exception("photo not found");
        }
        List<PhotoResponseDTO> results = new ArrayList<>();
        for(Photo data:photos){
            PhotoResponseDTO photosDTO = data.convertToResponse();
            results.add(photosDTO);
        }
        return results;
    }

    @Override
    public List<PhotoResponseDTO> findByUsername(String keyword) throws Exception{
        List<Photo> photos = photoRepository.findByUsername(keyword);
        if(photos == null) {
            throw new Exception("photo not found");
        }
        List<PhotoResponseDTO> results = new ArrayList<>();
        for(Photo data:photos){
            PhotoResponseDTO photosDTO = data.convertToResponse();
            results.add(photosDTO);
        }
        return results;
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
    public PhotoResponseDTO updatePhoto(PhotoRequestDTO photoRequestDTO,Long id) throws Exception{
        Photo photo = photoRequestDTO.convertToEntity();
        Optional<Photo> photoId = photoRepository.findById(id);
        if(photoId.isEmpty()){
            throw new Exception("Photo not found");
        }
        photo.setPhotoId(id);
        Photo savePhoto = photoRepository.save(photo);
        return savePhoto.convertToResponse();
    }

    @Override
    public void deleteById(Long id) {
        photoRepository.deleteAll();
    }


}
