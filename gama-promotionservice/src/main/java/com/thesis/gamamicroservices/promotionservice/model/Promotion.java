package com.thesis.gamamicroservices.promotionservice.model;

import com.thesis.gamamicroservices.promotionservice.dto.PromotionSetDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="promotion")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private int discountAmount;
    private Date startDate;
    private Date endDate;
    @Enumerated(EnumType.STRING)
    private PromotionState state;
    @ElementCollection
    private List<Integer> productsIds;

    public Promotion(PromotionSetDTO promotionSetDTO) {
        this.name = promotionSetDTO.getName();
        this.description = promotionSetDTO.getDescription();
        this.discountAmount = promotionSetDTO.getDiscountAmount();
        this.startDate = promotionSetDTO.getStartDate();
        this.endDate = promotionSetDTO.getEndDate();
        this.state = PromotionState.SCHEDULED;
        this.productsIds = new ArrayList<>();
    }

    public void addProductToPromotion(int productId) {
        this.productsIds.add(productId);
        //product.setPromotion(this);
    }

    public void removeProductFromPromotion(int productId) {
        this.productsIds.remove(productId);
    }
}
