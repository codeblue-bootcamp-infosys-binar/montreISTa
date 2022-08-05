package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BuyerRepository extends JpaRepository<Buyer,Long> {
    @Query(value = "SELECT * FROM buyers s WHERE buyer_id=?1", nativeQuery = true)
    List<Buyer> findByBuyerId(Long id);

}
