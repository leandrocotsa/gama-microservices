package com.thesis.gamamicroservices.productsview.service;

import com.thesis.gamamicroservices.productsview.exceptions.NoDataFoundException;
import com.thesis.gamamicroservices.productsview.model.Product;
import com.thesis.gamamicroservices.productsview.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {


    @Autowired
    ProductRepository productRepository;

    private static final String PRODUCT_NOT_FOUND = "There is no product with id: ";

    public Product getProductById(int id) throws NoDataFoundException {
        return productRepository.findByProductId(id)
                .orElseThrow(() -> new NoDataFoundException(PRODUCT_NOT_FOUND + id));
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
}
