<<<<<<< HEAD
//package com.codeblue.montreISTA.repository;
//
//import com.codeblue.montreISTA.entity.Buyer;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface BuyerRepository {
=======
package com.codeblue.montreISTA.repository;

import com.codeblue.montreISTA.entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Long> {
>>>>>>> 9296f8e3460a6f033b517fb67a30996f9b115b40
//    @Query(value = "SELECT * FROM buyers s WHERE buyer_id=?1", nativeQuery = true)
//    List<Buyer> findByBuyerId(Long id);
//    Optional<Buyer> findById(Long id);
//    Buyer save(Buyer buyer);
//    void deleteById(Long id);
//    List<Buyer> findAll();
<<<<<<< HEAD
//}
=======

}
>>>>>>> 9296f8e3460a6f033b517fb67a30996f9b115b40
