package com.codeblue.montreISTA.repository;


import com.codeblue.montreISTA.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {

    List<Payment> findByNameIgnoreCaseContaining(String keyword);
    Optional<Payment> findByNameIgnoreCase(String keyword);

}
