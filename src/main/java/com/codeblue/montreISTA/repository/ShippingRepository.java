package com.codeblue.montreISTA.repository;


import com.codeblue.montreISTA.entity.Payment;
import com.codeblue.montreISTA.entity.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShippingRepository extends JpaRepository<Shipping,Long> {

    @Query(value = "SELECT * FROM shipping sh WHERE shipping_id=?1", nativeQuery = true)
    List<Shipping> findByShippingId(Long id);

    List<Shipping> findByName(String keyword);

    Optional<Shipping> findByNameIgnoreCase(String keyword);
}
