package com.thesis.gamamicroservices.productsview.messaging.service;

import com.thesis.gamamicroservices.productsview.dto.messages.product_service.BrandCreatedMessage;
import com.thesis.gamamicroservices.productsview.dto.messages.product_service.BrandDeletedMessage;
import com.thesis.gamamicroservices.productsview.dto.messages.product_service.CategoryCreatedMessage;
import com.thesis.gamamicroservices.productsview.dto.messages.product_service.CategoryDeletedMessage;
import com.thesis.gamamicroservices.productsview.model.Brand;
import com.thesis.gamamicroservices.productsview.model.Category;
import com.thesis.gamamicroservices.productsview.repository.BrandRepository;
import com.thesis.gamamicroservices.productsview.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandsCategoriesEventsService {
    @Autowired
    BrandRepository brandRepository;
    @Autowired
    CategoryRepository categoryRepository;


    public void createBrand(BrandCreatedMessage brandCreatedMessage) {
        brandRepository.save(new Brand(brandCreatedMessage));
    }

    public void deleteBrand(BrandDeletedMessage brandDeletedMessage) {
        brandRepository.deleteById(String.valueOf(brandDeletedMessage.getId()));
    }

    public void createCategory(CategoryCreatedMessage categoryCreatedMessage) {
        categoryRepository.save(new Category(categoryCreatedMessage));
    }

    public void deleteCategory(CategoryDeletedMessage categoryDeletedMessage) {
        categoryRepository.deleteById(String.valueOf(categoryDeletedMessage.getId()));
    }
}
