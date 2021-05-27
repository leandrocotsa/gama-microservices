package com.thesis.gamamicroservices.productservice.service;

import com.thesis.gamamicroservices.productservice.dto.BrandSetDTO;
import com.thesis.gamamicroservices.productservice.dto.messages.BrandCreatedMessage;
import com.thesis.gamamicroservices.productservice.dto.messages.BrandDeletedMessage;
import com.thesis.gamamicroservices.productservice.messaging.RoutingKeys;
import com.thesis.gamamicroservices.productservice.model.Brand;
import com.thesis.gamamicroservices.productservice.model.Product;
import com.thesis.gamamicroservices.productservice.repository.BrandRepository;
import com.thesis.gamamicroservices.productservice.repository.ProductRepository;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class BrandService {

    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;
    private final RabbitTemplate rabbitTemplate;
    private final Exchange brandsCategoriesExchange;

    @Autowired
    public BrandService(BrandRepository brandRepository, ProductRepository productRepository, RabbitTemplate rabbitTemplate, @Qualifier("brandsCategoriesExchange") Exchange brandsCategoriesExchange) {
        this.brandRepository = brandRepository;
        this.productRepository = productRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.brandsCategoriesExchange = brandsCategoriesExchange;
    }

    public Brand findById (int id) throws NoDataFoundException {
        Optional<Brand> existingBrand = brandRepository.findById(id);
        if(existingBrand.isPresent()){
            return existingBrand.get();
        }
        else {
            throw new NoDataFoundException("There's no brand with id" + id);
        }
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

/**
    public void createBrand (String name) throws AlreadyExistsException {

        if(brandRepository.findByName(name).isPresent()){
            throw new AlreadyExistsException ("There's a Brand with that name");
        } else {
            this.brandRepository.save(Brand.builder().name(name).build());
        }
    }
 **/

    public void createBrand (BrandSetDTO brandSetDTO) throws AlreadyExistsException {
        if(brandRepository.findByName(brandSetDTO.getName()).isPresent()){
            throw new AlreadyExistsException ("There's a Brand with that name");
        } else {
            Brand brand = Brand.builder().name(brandSetDTO.getName()).build();
            this.brandRepository.save(brand);
            rabbitTemplate.convertAndSend(brandsCategoriesExchange.getName(), RoutingKeys.BRAND_CREATED.getNotation(), new BrandCreatedMessage(brand));

        }
    }

    public void deleteBrand(int brandId) throws NoDataFoundException, ExistingForeignKeysException {

        if(existsById(brandId)) {
            //existingBrand.ifPresent(brand -> this.brandRepository.delete(brand));
            Optional<Product> product = productRepository.findProductByBrand(brandId);
            if (product.isPresent()) {
                throw new ExistingForeignKeysException("There are still products associated with that Brand");
            } else {
                this.brandRepository.deleteById(brandId);
                rabbitTemplate.convertAndSend(brandsCategoriesExchange.getName(), RoutingKeys.BRAND_DELETED.getNotation(), new BrandDeletedMessage(brandId));
            }
        } else {
            throw new NoDataFoundException("There's no brand with that id " + brandId);
        }
    }

    public boolean existsById(int id) {
        return brandRepository.existsById(id);
    }


}


