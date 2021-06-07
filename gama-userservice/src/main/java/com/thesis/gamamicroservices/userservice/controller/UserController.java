package com.thesis.gamamicroservices.userservice.controller;

import com.thesis.gamamicroservices.userservice.dto.AddressSetDTO;
import com.thesis.gamamicroservices.userservice.dto.UserGetDTO;
import com.thesis.gamamicroservices.userservice.service.NoDataFoundException;
import com.thesis.gamamicroservices.userservice.service.UserService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/users")
@Api(tags = "UserController")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private static final String USER_DELETED_LOG = "User was deleted";
    private static final String USER_UPDATED_LOG = "User was updated";
    private static final String USER_ADDRESS_ADDED = "User address was added";
    private static final String USER_ADDRESS_REMOVED = "User address was removed";

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@RequestHeader("Authorization") String authorizationToken) throws NoDataFoundException {
        this.userService.deleteUser(authorizationToken);
        logger.info(USER_DELETED_LOG);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void editUser(@RequestHeader("Authorization") String authorizationToken, @RequestBody Map<String, Object> updates) {
        this.userService.updateUserDetails(authorizationToken, updates);
        logger.info(USER_UPDATED_LOG);
    }

    @PostMapping(path="/address")
    @ResponseStatus(HttpStatus.OK)
    public void addAddress(@RequestHeader("Authorization") String authorizationToken, @RequestBody AddressSetDTO addressSetDTO) {
        this.userService.addAddress(authorizationToken, addressSetDTO);
        logger.info(USER_ADDRESS_ADDED);
    }

    @DeleteMapping(path="/address/{addressID}")
    @ResponseStatus(HttpStatus.OK)
    public void removeAddress(@RequestHeader("Authorization") String authorizationToken, @PathVariable("addressID") int addressID) throws NoDataFoundException {
        this.userService.removeAddress(authorizationToken, addressID);
        logger.info(USER_ADDRESS_REMOVED);
    }
    //change email

    //change password


}