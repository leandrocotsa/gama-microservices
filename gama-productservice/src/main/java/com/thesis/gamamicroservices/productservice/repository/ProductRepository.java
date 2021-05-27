package com.thesis.gamamicroservices.productservice.repository;

import com.thesis.gamamicroservices.productservice.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    Optional<Product> findById(int id);
    Page<Product> findAll(Specification<Product> specs, Pageable pageable);
    List<Product> findAllByCategory_Name(String category); //devia ter sort default de popularidade
    List<Product> findAllByCategory_Name(String category, Pageable pageable);
    List<Product> findAllByCategory_Name(String category, Pageable pageable, Specification<Product> specs);
    List<Product> findAllByNameContaining(String name, Pageable pageable);
    //ha muitas muitas queries fixes, posso por a ordenar por reviews stars e assim
    @Query(value = "SELECT * FROM Product p WHERE p.brand_id = ?1 LIMIT 1", nativeQuery = true)
    Optional<Product> findProductByBrand(int brandId);
    @Query(value = "SELECT * FROM Product p WHERE p.category_id = ?1 LIMIT 1", nativeQuery = true)
    Optional<Product> findProductByCategory(int catId);
}
