package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.Buyer;
import com.codeblue.montreISTA.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Long> {

//    @Query(value = "SELECT * FROM sellers s WHERE seller_id=?1", nativeQuery = true)
//    List<Seller> findBySellerId(Long id);
    Optional<Seller> findByUserUsername(String keyword);
    Optional<Seller> findByStoreName(String keyword);
    Optional<Seller> findByUserUserId(Long id);
    List<Seller> findByOrderBySellerIdAsc();

}
