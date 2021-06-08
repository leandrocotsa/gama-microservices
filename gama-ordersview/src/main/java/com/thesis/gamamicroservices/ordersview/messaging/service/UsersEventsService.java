package com.thesis.gamamicroservices.ordersview.messaging.service;

import com.thesis.gamamicroservices.ordersview.dto.messages.user_service.*;
import com.thesis.gamamicroservices.ordersview.model.Address;
import com.thesis.gamamicroservices.ordersview.model.User;
import com.thesis.gamamicroservices.ordersview.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

@Service
public class UsersEventsService {

    @Autowired
    UserRepository userRepository;

    public void userCreated(UserCreatedMessage userCreatedMessage) {
        User user = new User(userCreatedMessage);
        userRepository.save(user);
    }

    public void userDeleted(UserDeletedMessage userDeletedMessage) {
        userRepository.deleteById(userDeletedMessage.getUserId());
    }

    public void userUpdated(UserUpdatedMessage userUpdatedMessage) {
        Map<String, Object> updates = userUpdatedMessage.getUpdates();
        int userId = userUpdatedMessage.getUserId();
        User user = userRepository.findById(userId).get();
        try {
            // Map key is field name, v is value
            updates.forEach((k, v) -> {
                // use reflection to get field k on manager and set it to value v
                try {
                    Field field = ReflectionUtils.findField(User.class, k);
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, user, v);
                } catch (NullPointerException e) {
                    throw new NullPointerException();
                }
            });
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addressAdded(AddressCreatedMessage addressCreatedMessage) {
        User user = userRepository.findById(addressCreatedMessage.getUserId()).get();
        user.getAddresses().add(new Address(addressCreatedMessage));
        userRepository.save(user);
    }

    public void addressRemoved(AddressDeletedMessage addressDeletedMessage) {
        User user = userRepository.findById(addressDeletedMessage.getUserId()).get();
        user.getAddresses().removeIf(a -> a.getAddressId() == addressDeletedMessage.getAddressId());
        userRepository.save(user);
    }
}
