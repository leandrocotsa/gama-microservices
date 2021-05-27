package com.thesis.gamamicroservices.productservice.controller;

import com.thesis.gamamicroservices.productservice.dto.BrandSetDTO;
import com.thesis.gamamicroservices.productservice.dto.CategorySetDTO;
import com.thesis.gamamicroservices.productservice.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/categories")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private static final String CATEGORY_CREATED_LOG = "A Category was created";
    private static final String CATEGORY_DELETED_LOG = "Category: {} was deleted";
    private static final String CATEGORY_UPDATED_LOG = "Category: {} was updated";

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public void createCategory(@RequestBody CategorySetDTO categorySetDTO) throws AlreadyExistsException {
        this.categoryService.createCategory(categorySetDTO);
        logger.info(CATEGORY_CREATED_LOG);
    }

    @DeleteMapping(path="/{catId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCategory(@PathVariable int catId) throws NoDataFoundException, ExistingForeignKeysException {
        this.categoryService.deleteCategory(catId);
        logger.info(CATEGORY_DELETED_LOG, catId);
    }

}