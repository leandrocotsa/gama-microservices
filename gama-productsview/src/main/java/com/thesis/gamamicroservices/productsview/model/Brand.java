package com.thesis.gamamicroservices.productsview.model;

import com.thesis.gamamicroservices.productsview.dto.messages.product_service.BrandCreatedMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "brands")
public class Brand {
    private int brandId;
    private String brandName;

    public Brand(BrandCreatedMessage brand) {
        this.brandId = brand.getId();
        this.brandName = brand.getName();
    }
}
