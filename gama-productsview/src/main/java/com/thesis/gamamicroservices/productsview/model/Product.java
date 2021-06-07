package com.thesis.gamamicroservices.productsview.model;

import com.thesis.gamamicroservices.productsview.dto.messages.product_service.ProductCreatedMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "catalog")
public class Product {
    @Id
    private int productId;
    private String name;
    private Double price;
    private Double promotionPrice;
    private Integer promotionId;
    private float weight;
    private int brandId;
    private String brandName;
    private int categoryId;
    private String categoryName;
    private List<Review> reviews;
    private List<Inventory> inventories;

    public Product(ProductCreatedMessage product) {
        this.productId = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.promotionPrice = product.getPromotionPrice();
        this.promotionId = null;
        this.weight = product.getWeight();
        this.brandId = product.getBrandId();
        this.brandName = product.getBrandName();
        this.categoryId = product.getCategoryId();
        this.categoryName = product.getCategoryName();
        this.reviews = new ArrayList<>();
        this.inventories = new ArrayList<>();
    }
}
