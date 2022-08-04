package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByListCartProductProductNameContaining(String keyword);
}
