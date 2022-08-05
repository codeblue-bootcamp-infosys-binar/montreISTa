package com.codeblue.montreISTA.service;



import com.codeblue.montreISTA.entity.Shipping;
import com.codeblue.montreISTA.repository.ShippingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShippingService {
    @Autowired
    ShippingRepository shippingRepository;

    public List<Shipping> findAllShipping() {
        List<Shipping> shippings = shippingRepository.findAll();
        return shippings;
    }

    public Optional<Shipping> findShippingById(Long id) {return shippingRepository.findById(id);
    }

    public Shipping createShipping(Shipping shipping) {return shippingRepository.save(shipping);
    }

    public Shipping updateShipping(Shipping updateShipping) {
        return shippingRepository.save(updateShipping);
    }

    public void deleteShipping(Long id) {shippingRepository.deleteById(id);
    }

    public List<Shipping> findShippingByShippingId(Long id) {
        List<Shipping> shipping = shippingRepository.findByShippingId(id);
        if(shipping.isEmpty()){
            return null;
        } else {
            return shipping;
        }
    }
}
