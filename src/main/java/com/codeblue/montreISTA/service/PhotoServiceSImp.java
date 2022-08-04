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
    public PhotoPostDTO createPhoto(PhotoRequestDTO photoRequestDTO) {
        Photo savePhoto = photoRequestDTO.convertToEntity();
        photoRepository.save(savePhoto);
        return savePhoto.convertToPost();
    }

    @Override
    public PhotoPostDTO updatePhoto(Photo photo,Long id) {
//        Optional<Photo> photoId = photoRepository.findById(id);
//        Photo photos = photoId.get();
//        photos.setPhotoName(photo.getPhotoName());
//        photos.setStudioName(seatsRequest.getStudioName());
//        photos.setIsAvailable(seatsRequest.getIsAvailable());
//        seatsService.updateSeat(seats);
        photo.setPhotoId(id);
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
