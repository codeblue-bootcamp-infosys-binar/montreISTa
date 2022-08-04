package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    @Query(value = "SELECT * FROM buyers s WHERE buyer_id=?1", nativeQuery = true)
    List<Buyer> findByBuyerId(Long id);
    Optional<Buyer> findById(Long id);
    Buyer save(Buyer buyer);
    void deleteById(Long id);
    List<Buyer> findAll();

}
