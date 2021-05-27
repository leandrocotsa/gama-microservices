package com.thesis.gamamicroservices.productservice.repository;


import com.thesis.gamamicroservices.productservice.model.Brand;
import com.thesis.gamamicroservices.productservice.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {
    Optional<Category> findByName(String name);
    List<Category> findAll();
}
