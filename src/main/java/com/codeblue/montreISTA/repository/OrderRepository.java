package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByListCartProductProductNameContaining(String keyword);
    List<Order> findByListCartBuyerUserUsernameContaining(String keyword);
    List<Order> findByListCartProductSellerStoreNameContaining(String keyword);
    Order findFirstByOrderByCreatedAtDesc();
    Optional<Order> findFirstByListCartBuyerBuyerIdOrderByCreatedAtDesc(Long id);
}
