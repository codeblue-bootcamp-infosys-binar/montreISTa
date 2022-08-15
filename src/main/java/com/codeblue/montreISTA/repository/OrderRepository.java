package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.Cart;
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
    Optional<Order> findFirstByListCartBuyerUserUsernameOrderByModifiedAtDesc(String keyword);
    Optional<Order> findFirstByListCartBuyerBuyerIdOrderByModifiedAtDesc(Long id);
    Optional<Order> findFirstByListCartProductSellerSellerIdOrderByCreatedAtDesc(Long id);
    Optional<Order> findFirstByListCartProductSellerUserIdUsernameOrderByCreatedAtDesc(String keyword);
    List<Order> findAllByOrderByOrderIdAsc();
}
