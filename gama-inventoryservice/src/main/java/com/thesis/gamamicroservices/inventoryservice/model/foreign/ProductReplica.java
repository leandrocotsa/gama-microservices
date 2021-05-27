package com.thesis.gamamicroservices.inventoryservice.model.foreign;

import com.thesis.gamamicroservices.inventoryservice.dto.messages.ProductCreatedMessage;
import com.thesis.gamamicroservices.inventoryservice.model.Inventory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="product")
public class ProductReplica {
    @Id
    private int id;
    //private String name;
    //private Double price;
    //private Double promotionPrice;
    //private float weight;
    @OneToMany(mappedBy = "product", cascade= CascadeType.ALL)
    private List<Inventory> inventories;



    public int getStockAmount() {
        int totalStock = 0;
        for(Inventory i : inventories) {
            totalStock+=i.getStockAmount();
        }
        return totalStock;
    }

    public ProductReplica(ProductCreatedMessage productCreatedDTO) {
        this.id = productCreatedDTO.getId();
    }

}
