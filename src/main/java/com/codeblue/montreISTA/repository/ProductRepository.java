package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.service.PhotoServiceImp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<PhotoServiceImp.Product,Long> {


    @Query(value = "SELECT * FROM products p WHERE seller_id=?1", nativeQuery = true)
    List<PhotoServiceImp.Product> findBySellerId(Long id);
}
