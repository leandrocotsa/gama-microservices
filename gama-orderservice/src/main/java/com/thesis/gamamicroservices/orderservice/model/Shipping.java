package com.thesis.gamamicroservices.orderservice.model;

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
@Table(name="shipping")
public class Shipping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Double cost;
    private String notes;
    @OneToOne
    private Order order;
    private String country;
    private String address;
    //nao tenho objeto address pq nao o tenho aqui lol e porque se um user for apagado quero na mesma ter a order com os dados certinhos, e o address tem cascade com user

    public Shipping(Double cost, String notes, String address, String country) {
        this.cost = cost;
        this.notes = notes;
        this.address = address;
        this.country = country;
    }
}
