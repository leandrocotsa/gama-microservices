package com.thesis.gamamicroservices.productservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.thesis.gamamicroservices.productservice.dto.SpecificationValueSetDTO;
import com.thesis.gamamicroservices.productservice.dto.ProductSetDTO;
import com.thesis.gamamicroservices.productservice.dto.messages.consumed.PromotionStartedMessage;
import com.thesis.gamamicroservices.productservice.dto.messages.produced.*;
import com.thesis.gamamicroservices.productservice.messaging.RoutingKeys;
import com.thesis.gamamicroservices.productservice.model.*;
import com.thesis.gamamicroservices.productservice.repository.ProductRepository;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.*;

@Transactional
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandService brandService;
    private final CategoryService categoryService;
    //rabbit-related
    private final ObjectWriter objectWriter;
    private final RabbitTemplate rabbitTemplate;
    private final Exchange deletedCreatedExchange;
    private final Exchange updatedExchange;

    private static final String PRODUCT_NOT_FOUND = "There is no product with id: ";

   @Autowired
    public ProductService(ProductRepository productRepository, BrandService brandService, CategoryService categoryService, ObjectWriter objectWriter, RabbitTemplate rabbitTemplate, @Qualifier("productCreatedDeletedExchange") Exchange deletedCreatedExchange, @Qualifier("productUpdatedExchange") Exchange updatedExchange) {
        this.productRepository = productRepository;
        this.brandService = brandService;
        this.categoryService = categoryService;
        this.objectWriter = objectWriter;
        this.rabbitTemplate = rabbitTemplate;
        this.deletedCreatedExchange = deletedCreatedExchange;
        this.updatedExchange = updatedExchange;
    }


    private Product getProductById(int id) throws NoDataFoundException {

        return productRepository.findById(id)
                .orElseThrow(() -> new NoDataFoundException(PRODUCT_NOT_FOUND + id));

        /**
        Optional<Product> product = this.productRepository.findById(id);
        if(product.isPresent()) {
            return product.get();
        }
        else {
            throw new NoDataFoundException(PRODUCT_NOT_FOUND + id);
        }
         **/
    }


    public void deleteProduct(int id) throws NoDataFoundException {
        if(productRepository.existsById(id)) {
            productRepository.deleteById(id);
            rabbitTemplate.convertAndSend(deletedCreatedExchange.getName(), RoutingKeys.PRODUCT_DELETED.getNotation(), new ProductDeletedMessage(id));
        }
        else {
            throw new NoDataFoundException(PRODUCT_NOT_FOUND + id);
        }
    }

    public void createProduct(ProductSetDTO productSetDTO) throws NoDataFoundException, JsonProcessingException {
        //hmm verifico se nome do produto já existe?
        //Optional<Product> existingProduct = this.carRepository.findByLicensePlate(carSetDto.getLicensePlate());

        Brand brand = this.brandService.findById(productSetDTO.getBrandId());
        Category category = this.categoryService.findById(productSetDTO.getCategoryId());
        List<SpecificationValue> specificationValues = new ArrayList<>();

        for(SpecificationValueSetDTO s : productSetDTO.getSpecificationValues()) {
            //Specification specification = specificationService.findById(s.getSpecificationId());
            specificationValues.add(new SpecificationValue(s));
        }

        Product p = new Product(productSetDTO, brand, category);
        p.addSpecificationValuesToProduct(specificationValues);
        this.productRepository.save(p);

        //posso enviar product com as specs todas para a view e nos serviços que nao a view as classes que recebem nao têm a info que nao precisam e nao dá erro
        //String productJson = objectWriter.writeValueAsString(new ProductCreatedDTO(p, brand.getId(), category.getId()));

        rabbitTemplate.convertAndSend(deletedCreatedExchange.getName(), RoutingKeys.PRODUCT_CREATED.getNotation(), new ProductCreatedMessage(p, brand.getId(), category.getId()));
        //ver rabbittamplate.receiveandconvert

    }


    //nao funciona para mudar nested entities como category e brand, so muda primitivas
    //posso analisar o map que vem e verificar se tem category e brand e faço set manualmente desses
    //ao modificar o preço tambem, tenho que ter em atenção promotion price que é sempre em relação ao price
    public void editProduct(Map<String, Object> updates, int productId) throws NoDataFoundException {
        Map<String, Object> duplicated_updates = new HashMap<>(updates);
        Product product = this.getProductById(productId);
        Double productOldPrice = product.getPrice();
            try {
                // Map key is field name, v is value
                updates.forEach((k, v) -> {
                    // use reflection to get field k on manager and set it to value v
                    try {
                        Field field = ReflectionUtils.findField(Product.class, k);
                        field.setAccessible(true);
                        ReflectionUtils.setField(field, product, v);
                    } catch (NullPointerException e) {
                        if(!(k.equals("brandId") || k.equals("categoryId"))) {
                            throw new NullPointerException();
                        }
                    }


                    switch (k) {
                        case "price":
                            if (product.getPromotionPrice() != null) {
                                int discountAmount = (int) ((product.getPromotionPrice() * 100) / productOldPrice);
                                Double newPromotionPrice = product.getPrice() * discountAmount;
                                product.setPromotionPrice(newPromotionPrice);
                                duplicated_updates.put("promotionPrice", newPromotionPrice);
                            }
                            break;
                        case "brandId":
                            try {
                                Brand brand = brandService.findById((Integer) v);
                                product.setBrand(brand);
                            } catch (NoDataFoundException e) {
                                duplicated_updates.remove(k);
                                throw new NullPointerException();
                            }
                            break;
                        case "categoryId":
                            try {
                                Category category = categoryService.findById((Integer) v);
                                product.setCategory(category);
                            } catch (NoDataFoundException e) {
                                duplicated_updates.remove(k);
                                throw new NullPointerException();
                            }
                            break;
                    }
                });
                productRepository.save(product);
                duplicated_updates.put("id", productId);
                ProductUpdatedMessage productUpdated = new ProductUpdatedMessage(duplicated_updates);
                rabbitTemplate.convertAndSend(updatedExchange.getName(), RoutingKeys.PRODUCT_UPDATED.getNotation(), productUpdated);
                //EVENTO PARA A VIEW DE PRODUCT UPDATE
            } catch(Exception e) {
                e.printStackTrace();
                throw new NoDataFoundException ("Invalid arguments");
            }

        //evento que só interessa à view
        //so as alterações de preço interessam a outros serviços

    }


    public void setPromotionPrice(PromotionStartedMessage promotionStarted) {
        //ArrayList<Integer> productsId = new ArrayList<>();
        Map<Integer,Double> products_price = new HashMap<>();
        for(int productId : promotionStarted.getProductsIds()) {
            try {
                Product product = this.getProductById(productId);
                Double newPrice = product.getPrice() - (product.getPrice() * (promotionStarted.getDiscountAmount()) / 100);
                product.setPromotionPrice(newPrice);
                productRepository.save(product);
                products_price.put(productId, newPrice);
                //productsId.add(productId);
            } catch (NoDataFoundException e) {
                e.printStackTrace();
            }
        }
        rabbitTemplate.convertAndSend(updatedExchange.getName(), RoutingKeys.PROMOTION_STARTED.getNotation(), new PromotionPriceMessage(products_price));

        //promotionStarted.setProductsIds(productsId);
/**
        try {
            String productsJson = objectWriter.writeValueAsString(new PromotionPriceMessage(products_price));
            rabbitTemplate.convertAndSend(updatedExchange.getName(), RoutingKeys.PROMOTION_STARTED.getNotation(), productsJson);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }

**/
    }

    public void resetPromotionPrice(List<Integer> productsEnded) {
        for(int pId : productsEnded) {
            try {
                Product product = this.getProductById(pId);
                product.setPromotionPrice(null);
                productRepository.save(product);
            } catch (NoDataFoundException e) {
                e.printStackTrace();
            }
        }
        ArrayList<Integer> products = new ArrayList<>(productsEnded);
        rabbitTemplate.convertAndSend(updatedExchange.getName(), RoutingKeys.PROMOTION_ENDED.getNotation(), new PromotionPriceResetMessage(products));

    }

    //quando era só um produto
    public void resetPromotionPrice(int productId) {
        try {
            Product product = this.getProductById(productId);
            product.setPromotionPrice(null);
            productRepository.save(product);
        } catch (NoDataFoundException e) {
            e.printStackTrace();
        }
    }


}
