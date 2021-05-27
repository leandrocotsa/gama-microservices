package com.thesis.gamamicroservices.paymentservice.repository;

import com.thesis.gamamicroservices.paymentservice.model.foreign.ConfirmedOrderReplica;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfirmedOrderReplicaRepository extends CrudRepository<ConfirmedOrderReplica,Integer> {
    List<ConfirmedOrderReplica> findAll();
}
