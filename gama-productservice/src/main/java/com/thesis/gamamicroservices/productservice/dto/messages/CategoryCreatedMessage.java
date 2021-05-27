package com.thesis.gamamicroservices.productservice.dto.messages;

import com.thesis.gamamicroservices.productservice.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryCreatedMessage {
    private int id;
    private String name;
    private String typicalSpecifications;

    public CategoryCreatedMessage(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.typicalSpecifications = category.getTypicalSpecifications();
    }
}
