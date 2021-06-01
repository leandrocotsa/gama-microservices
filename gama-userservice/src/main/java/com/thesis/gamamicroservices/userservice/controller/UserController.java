package com.thesis.gamamicroservices.userservice.controller;

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


@RestController
@RequestMapping("/users")
@Api(tags = "UserController")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private static final String USER_DELETED_LOG = "User was deleted";

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@RequestHeader("Authorization") String authorizationToken) throws NoDataFoundException {
        this.userService.deleteUser(authorizationToken);
        logger.info(USER_DELETED_LOG);
    }
    //change email

    //change password


}