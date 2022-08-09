package com.codeblue.montreISTA.service;

import com.codeblue.montreISTA.entity.Shipping;

import java.util.List;
import java.util.Optional;

public interface ShippingService {

    List<Shipping> findAllShipping();

    Optional<Shipping> findShippingById(Long id);

    Shipping createShipping(Shipping shipping);

    Shipping updateShipping(Shipping updateShipping);

    void deleteShipping(Long id);

    List<Shipping> findShippingByShippingId(Long id);

    List<Shipping> findByName(String keyword);
}
