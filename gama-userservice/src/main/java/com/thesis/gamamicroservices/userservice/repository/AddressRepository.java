package com.thesis.gamamicroservices.userservice.repository;

import com.thesis.gamamicroservices.userservice.model.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface AddressRepository extends CrudRepository<Address, Integer> {

    Optional<Address> findById(int id);

}