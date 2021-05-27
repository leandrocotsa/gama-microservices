package com.thesis.gamamicroservices.paymentservice.model.foreign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="order_replica")
public class ConfirmedOrderReplica {
    @Id
    private int id;
    private Double price;
}
