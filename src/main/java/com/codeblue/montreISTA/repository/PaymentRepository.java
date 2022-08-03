package com.codeblue.montreISTA.repository;


import com.codeblue.montreISTA.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {

//    @Query("SELECT p FROM Payment p where p.Name LIKE %:name% ORDER BY p.Name ASC")
//    public List<Payment> getPaymentByName(@Param("name") String name);
//
//    List<Payment> findByName(String name);
}
