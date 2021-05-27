package com.thesis.gamamicroservices.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductSetDTO {
    @NotNull(message = "name must be entered")
    private String name;
    private String description;
    @NotNull(message = "price must be entered")
    private Double price;
    @NotNull(message = "weight must be entered")
    private float weight;
    @NotNull(message = "brand must be entered")
    private int brandId;
    @NotNull(message = "category must be entered")
    private int categoryId;
    private List<SpecificationValueSetDTO> specificationValues;
}
