package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {


    @Query(value = "SELECT * FROM products p WHERE seller_id=?1", nativeQuery = true)
    List<Product> findBySellerId(Long id);
}
