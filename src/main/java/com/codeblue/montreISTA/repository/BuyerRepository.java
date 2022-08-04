package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.Buyer;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BuyerRepository {
    @Query(value = "SELECT * FROM buyers s WHERE buyer_id=?1", nativeQuery = true)
    List<Buyer> findByBuyerId(Long id);
    Optional<Buyer> findById(Long id);
    Buyer save(Buyer buyer);
    void deleteById(Long id);
    List<Buyer> findAll();
}
