package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.Cart;
import com.codeblue.montreISTA.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByListCartProductProductNameIgnoreCaseContaining(String keyword);
    List<Order> findByListCartBuyerUserUsernameContaining(String keyword);
    List<Order> findByListCartProductSellerStoreNameIgnoreCaseContaining(String keyword);
    List<Order> findByListCartBuyerBuyerIdOrderByOrderIdAsc(Long id);
    Order findFirstByOrderByOrderIdDesc();
    Optional<Order> findFirstByListCartBuyerUserUsernameOrderByOrderIdDesc(String keyword);
    Optional<Order> findFirstByListCartBuyerBuyerIdOrderByOrderIdDesc(Long id);
    Optional<Order> findFirstByListCartProductSellerSellerIdOrderByOrderIdDesc(Long id);
    List<Order> findByListCartProductSellerUserUsernameOrderByOrderIdDesc(String keyword);
    List<Order> findAllByOrderByOrderIdAsc();
    List<Order> findByIsPay(Boolean isPay);
}
