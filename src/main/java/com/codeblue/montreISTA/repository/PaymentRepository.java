package com.codeblue.montreISTA.repository;


import com.codeblue.montreISTA.entity.Order;
import com.codeblue.montreISTA.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {

    List<Payment> findByNameContaining(String keyword);
}
