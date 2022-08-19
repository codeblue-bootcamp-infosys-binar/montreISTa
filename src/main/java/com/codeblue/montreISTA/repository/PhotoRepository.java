package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.Cart;
import com.codeblue.montreISTA.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo,Long> {
    List<Photo> findAllByOrderByPhotoIdAsc();
    List<Photo> findByProductSellerUserNameIgnoreCaseContainingOrderByPhotoIdAsc(String productName);
    List<Photo> findByProductSellerSellerIdOrderByPhotoIdAsc(long id);
    List<Photo> findByProductProductIdOrderByPhotoIdAsc(Long id);
    @Query("Select p from Photo p where p.product.productName like %:name% ORDER BY p.product.productName ASC")
    List<Photo> findByProductName(@Param("name")String name);

    @Query("Select p from Photo p where concat(p.product.seller.user.name, ' ', p.product.seller.user.username, ' ') like %:name% ORDER BY p.product.seller.user.name ASC")
    List<Photo> findByUsername(@Param("name")String name);


}
