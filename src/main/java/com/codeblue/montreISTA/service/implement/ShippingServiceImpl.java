package com.codeblue.montreISTA.service.implement;



import com.codeblue.montreISTA.DTO.ShippingRequestDTO;
import com.codeblue.montreISTA.DTO.ShippingResponseDTO;
import com.codeblue.montreISTA.entity.Shipping;
import com.codeblue.montreISTA.repository.ShippingRepository;
import com.codeblue.montreISTA.service.ShippingService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ShippingServiceImpl implements ShippingService {

    private final ShippingRepository shippingRepository;

    @Override
    public List<ShippingResponseDTO> findAllShipping() throws Exception {
        List<Shipping> shippings = shippingRepository.findAll();
        if(shippings.isEmpty()){
            throw new Exception("Shipping not found");
        }
        return shippings.stream()
                .map(Shipping::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ShippingResponseDTO findShippingById(Long id) throws Exception {
        return shippingRepository.findById(id)
                .orElseThrow(()->new Exception("Shipping not found"))
                .convertToResponse();
    }

    @Override
    public ShippingResponseDTO createShipping(ShippingRequestDTO shippingRequestDTO) throws Exception {
        Shipping shipping = shippingRequestDTO.convertToEntity();
        Optional<Shipping> check = shippingRepository.findByNameIgnoreCase(shippingRequestDTO.getName());
        if(check.isPresent()){
            throw new Exception("this name not unique");
        }
        return shippingRepository.save(shipping).convertToResponse();
    }

    @Override
    public ShippingResponseDTO updateShipping(ShippingRequestDTO shippingRequestDTO, Long id) throws Exception {
        Shipping shipping = shippingRepository.findById(id).orElseThrow(()->new Exception("Shipping not found"));
        Optional<Shipping> check = shippingRepository.findByNameIgnoreCase(shippingRequestDTO.getName());
        if(check.isPresent()) {
            throw new Exception("this name not unique");
        }
        shipping.setShippingId(id);
        shipping.setName(shippingRequestDTO.getName());
        shipping.setPrice(shippingRequestDTO.getPrice());
        return shippingRepository.save(shipping).convertToResponse();
    }


    @Override
    public void deleteShipping(Long id) throws Exception{
        Shipping shipping = shippingRepository.findById(id).orElseThrow(()->new Exception("Shipping not found"));
        shippingRepository.deleteById(id);
    }

    @Override
    public List<ShippingResponseDTO> findByName(String keyword) throws Exception {
        List<Shipping> shippings =shippingRepository.findByName(keyword);
        if(shippings.isEmpty()){
            throw new Exception("Shipping not found");
        }
        return shippings.stream()
                .map(Shipping::convertToResponse)
                .collect(Collectors.toList());
    }
}
