package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo,Long> {
    List<Photo> findByPhotoName(String photoName);
}
