package com.codeblue.montreISTA.service.implement;



import com.codeblue.montreISTA.entity.Shipping;
import com.codeblue.montreISTA.repository.ShippingRepository;
import com.codeblue.montreISTA.service.ShippingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ShippingServiceImpl implements ShippingService {

    private final ShippingRepository shippingRepository;

    @Override
    public List<Shipping> findAllShipping() {
        return shippingRepository.findAll();
    }

    @Override
    public Optional<Shipping> findShippingById(Long id) {return shippingRepository.findById(id);
    }

    @Override
    public Shipping createShipping(Shipping shipping) {return shippingRepository.save(shipping);
    }

    @Override
    public Shipping updateShipping(Shipping updateShipping) {
        return shippingRepository.save(updateShipping);
    }


    @Override
    public void deleteShipping(Long id) {shippingRepository.deleteById(id);
    }

    @Override
    public List<Shipping> findShippingByShippingId(Long id) {
        List<Shipping> shipping = shippingRepository.findByShippingId(id);
        if(shipping.isEmpty()){
            return null;
        } else {
            return shipping;
        }
    }

    @Override
    public List<Shipping> findByName(String keyword) {
        return shippingRepository.findByName(keyword);
    }
}
