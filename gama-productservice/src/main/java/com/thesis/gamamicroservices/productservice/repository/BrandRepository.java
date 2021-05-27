package com.thesis.gamamicroservices.productservice.repository;

import com.thesis.gamamicroservices.productservice.model.Brand;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends CrudRepository<Brand, Integer> {
    Optional<Brand> findByName(String name);
    List<Brand> findAll();
}
