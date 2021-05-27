package com.thesis.gamamicroservices.orderservice.service;


import com.thesis.gamamicroservices.orderservice.dto.ShippingReferenceSetDTO;
import com.thesis.gamamicroservices.orderservice.model.ShippingReferenceValue;
import com.thesis.gamamicroservices.orderservice.repository.ShippingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Service
public class ShippingService {

    @Autowired
    ShippingRepository shippingRepository;

    public Double calculateShippingValue(float weight, String country) throws NoDataFoundException {
        Optional<ShippingReferenceValue> s = shippingRepository.findByCountry(country);
        if(s.isPresent()) {
            return s.get().getCostPerkg()*weight;
        }
        else {
            throw new NoDataFoundException("We don't ship to " + country);
        }
    }


    public void createShippingReference(ShippingReferenceSetDTO shippingReferenceSetDTO) throws AlreadyExistsException {
        if(shippingRepository.findByCountry(shippingReferenceSetDTO.getCountry()).isEmpty()) {
            ShippingReferenceValue s1 = new ShippingReferenceValue(shippingReferenceSetDTO);
            shippingRepository.save(s1);
        } else {
            throw new AlreadyExistsException("That country is already registered");
        }
    }

}
