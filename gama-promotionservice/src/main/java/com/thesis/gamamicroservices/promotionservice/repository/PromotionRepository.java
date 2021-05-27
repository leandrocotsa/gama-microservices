package com.thesis.gamamicroservices.promotionservice.repository;

import com.thesis.gamamicroservices.promotionservice.model.Promotion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionRepository extends CrudRepository<Promotion, Integer> {

    Optional<Promotion> findById(int id);
    @Query("SELECT p FROM Promotion p WHERE p.state = 'ACTIVE' OR p.state = 'SCHEDULED'")
    List<Promotion> findActiveOrScheduledPromotions();

}
