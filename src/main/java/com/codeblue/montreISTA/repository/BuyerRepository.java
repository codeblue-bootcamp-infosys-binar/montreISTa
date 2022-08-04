package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Long> {
}
