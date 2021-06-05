package com.thesis.gamamicroservices.productsview.model;

import com.thesis.gamamicroservices.productsview.dto.messages.product_service.CategoryCreatedMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "categories")
public class Category {
    private int categoryId;
    private String categoryName;
    private String typicalSpecifications;

    public Category(CategoryCreatedMessage category) {
        this.categoryId = category.getId();
        this.categoryName = category.getName();
        this.typicalSpecifications = category.getTypicalSpecifications();
    }
}
