package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo,Long> {
    List<Photo> findByPhotoNameIgnoreCaseContaining(String photoName);

//    long deleteByPhotoName(long photoName);

    @Query("Select p from Photo p where p.product.productName like %:name% ORDER BY p.product.productName ASC")
    public List<Photo> findByProductName(@Param("name")String name);

    @Query("Select p from Photo p where concat(p.product.seller.userId.name, ' ', p.product.seller.userId.username, ' ') like %:name% ORDER BY p.product.seller.userId.name ASC")
    public List<Photo> findByUsername(@Param("name")String name);


}
