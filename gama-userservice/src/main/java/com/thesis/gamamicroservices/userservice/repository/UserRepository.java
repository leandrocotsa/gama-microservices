package com.thesis.gamamicroservices.userservice.repository;

import com.thesis.gamamicroservices.userservice.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByAccount_Email(String email);
    //User findByAccount_Email(String email);
}
