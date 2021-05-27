package com.thesis.gamamicroservices.inventoryservice.model;

import com.thesis.gamamicroservices.inventoryservice.dto.WarehouseSetDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="warehouse")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "warehouse", cascade=CascadeType.ALL)
    private List<Inventory> inventories;

    public Warehouse(String name, String description) {
        this.name = name;
        this.description = description;
        inventories = new ArrayList<>();
    }

    public Warehouse(WarehouseSetDTO warehouseSetDTO) {
        this.name = warehouseSetDTO.getName();
        this.description = warehouseSetDTO.getDescription();
        inventories = new ArrayList<>();
    }
}
