package com.thesis.gamamicroservices.productservice.service;

import com.thesis.gamamicroservices.productservice.dto.CategorySetDTO;
import com.thesis.gamamicroservices.productservice.dto.messages.produced.CategoryCreatedMessage;
import com.thesis.gamamicroservices.productservice.dto.messages.produced.CategoryDeletedMessage;
import com.thesis.gamamicroservices.productservice.messaging.RoutingKeys;
import com.thesis.gamamicroservices.productservice.model.Category;
import com.thesis.gamamicroservices.productservice.model.Product;
import com.thesis.gamamicroservices.productservice.repository.CategoryRepository;
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
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final RabbitTemplate rabbitTemplate;
    private final Exchange brandsCategoriesExchange;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository, RabbitTemplate rabbitTemplate, @Qualifier("brandsCategoriesExchange")Exchange brandsCategoriesExchange) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.brandsCategoriesExchange = brandsCategoriesExchange;
    }

    public Category findById (int id) throws NoDataFoundException {
        Optional<Category> existingCategory = categoryRepository.findById(id);
        if(existingCategory.isPresent()){
            return existingCategory.get();
        }
        else {
            throw new NoDataFoundException("There's no category with id" + id);
        }
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void createCategory(CategorySetDTO categoryDTO) throws AlreadyExistsException {
        if(categoryRepository.findByName(categoryDTO.getName()).isPresent()){
            throw new AlreadyExistsException ("There's a Category with that name");
        } else {
            Category category = new Category(categoryDTO);
            this.categoryRepository.save(category);
            rabbitTemplate.convertAndSend(brandsCategoriesExchange.getName(), "brandsCategories", new CategoryCreatedMessage(category));
        }
    }

    public boolean existsById(int id) {
        return categoryRepository.existsById(id);
    }


    public void deleteCategory(int catId) throws NoDataFoundException, ExistingForeignKeysException {

        if(existsById(catId)) {
            //existingBrand.ifPresent(brand -> this.brandRepository.delete(brand));
            Optional<Product> product = productRepository.findProductByCategory(catId);
            if (product.isPresent()) {
                throw new ExistingForeignKeysException("There are still products associated with that Category");
            } else {
                this.categoryRepository.deleteById(catId);
                rabbitTemplate.convertAndSend(brandsCategoriesExchange.getName(), "brandsCategories", new CategoryDeletedMessage(catId));
            }
        } else {
            throw new NoDataFoundException("There's no category with that id " + catId);
        }
    }
}