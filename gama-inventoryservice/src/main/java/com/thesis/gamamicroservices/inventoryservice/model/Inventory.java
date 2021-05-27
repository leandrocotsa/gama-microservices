package com.thesis.gamamicroservices.inventoryservice.model;

import com.thesis.gamamicroservices.inventoryservice.model.foreign.ProductReplica;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    Warehouse warehouse;
    @ManyToOne
    @JoinColumn(name = "product_id")
    ProductReplica product;

    int stockAmount;

    public Inventory(Warehouse warehouse, ProductReplica product, int stockAmount) {
        this.warehouse = warehouse;
        this.product = product;
        this.stockAmount = stockAmount;
    }
}
