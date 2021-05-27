package com.thesis.gamamicroservices.productservice.model;

import com.thesis.gamamicroservices.productservice.dto.ProductSetDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="product")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private Double price;
    private Double promotionPrice;
    private float weight;

    @ManyToOne
    private Brand brand;

    @OneToOne
    private Category category;

    @OneToMany(mappedBy = "product", cascade=CascadeType.ALL)
    private List<SpecificationValue> specificationValues;



    public Product(ProductSetDTO productSetDTO, Brand brand, Category category) {
        this.name = productSetDTO.getName();
        this.description = productSetDTO.getDescription();
        this.price = productSetDTO.getPrice();
        this.promotionPrice = null; //tenho que criar um scheduled job para repor o promotion price quando atingir à data limite da promotion
        this.weight = productSetDTO.getWeight();
        //this.stock = productSetDTO.getStock();
        this.brand = brand;
        this.category = category;
        this.specificationValues = new ArrayList<>();
    }


    public void addSpecificationValuesToProduct (List<SpecificationValue> specificationValues){
        for(SpecificationValue s : specificationValues) {
            s.setProduct(this);
        }
        this.setSpecificationValues(specificationValues);
    }

}
