package com.thesis.gamamicroservices.productsview.controller;

import com.thesis.gamamicroservices.productsview.exceptions.NoDataFoundException;
import com.thesis.gamamicroservices.productsview.model.Product;
import com.thesis.gamamicroservices.productsview.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class CatalogController {

    @Autowired
    ProductService productService;

    @GetMapping(path="/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) throws NoDataFoundException {
        return ResponseEntity.ok(this.productService.getProductById(id));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() throws NoDataFoundException {
        return ResponseEntity.ok(this.productService.findAllProducts());
    }


}
