package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Long> {

    @Query(value = "SELECT * FROM sellers s WHERE seller_id=?1", nativeQuery = true)
    List<Seller> findBySellerId(Long id);

}
