package com.thesis.gamamicroservices.userservice.controller;

import com.thesis.gamamicroservices.userservice.dto.UserGetDTO;
import com.thesis.gamamicroservices.userservice.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
@Api(tags = "UserController")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public UserGetDTO getMyUser(@RequestHeader("Authorization") String authorizationToken) {
        return this.userService.getMyUserDetails(authorizationToken);
    }

    //change email

    //change password


}