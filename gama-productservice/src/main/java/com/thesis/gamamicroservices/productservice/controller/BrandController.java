package com.thesis.gamamicroservices.productservice.controller;

import com.thesis.gamamicroservices.productservice.dto.BrandSetDTO;
import com.thesis.gamamicroservices.productservice.service.AlreadyExistsException;
import com.thesis.gamamicroservices.productservice.service.BrandService;
import com.thesis.gamamicroservices.productservice.service.ExistingForeignKeysException;
import com.thesis.gamamicroservices.productservice.service.NoDataFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/brands")
public class BrandController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private static final String BRAND_CREATED_LOG = "A Brand was created";
    private static final String BRAND_DELETED_LOG = "Brand: {} was deleted";
    private static final String BRAND_UPDATED_LOG = "Brand: {} was updated";

    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public void createBrand(@RequestBody BrandSetDTO brandSetDTO) throws AlreadyExistsException {
        this.brandService.createBrand(brandSetDTO);
        logger.info(BRAND_CREATED_LOG);
    }

    @DeleteMapping(path="/{brandId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBrand(@PathVariable int brandId) throws NoDataFoundException, ExistingForeignKeysException {
        this.brandService.deleteBrand(brandId);
        logger.info(BRAND_DELETED_LOG, brandId);
    }

}