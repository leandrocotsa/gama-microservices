package com.thesis.gamamicroservices.userservice.controller;

import com.thesis.gamamicroservices.userservice.dto.UserSetDTO;
import com.thesis.gamamicroservices.userservice.service.AlreadyExistsException;
import com.thesis.gamamicroservices.userservice.service.UserService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
@Api(tags = "RegistrationController")
public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    private static final String USER_CREATED_LOG = "An User was created";

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@Valid @RequestBody UserSetDTO userSetDto) throws AlreadyExistsException {
        userService.createUser(userSetDto);
        logger.info(USER_CREATED_LOG);
    }

    @PostMapping(path="/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAdmin(@Valid @RequestBody UserSetDTO userSetDto) throws AlreadyExistsException {
        userService.createAdmin(userSetDto);
        logger.info(USER_CREATED_LOG);
    }


}
