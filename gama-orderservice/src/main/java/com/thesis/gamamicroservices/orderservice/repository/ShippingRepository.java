package com.thesis.gamamicroservices.orderservice.repository;

import com.thesis.gamamicroservices.orderservice.model.ShippingReferenceValue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShippingRepository extends CrudRepository<ShippingReferenceValue, Integer> {

    Optional<ShippingReferenceValue> findByCountry(String countryName);

}
