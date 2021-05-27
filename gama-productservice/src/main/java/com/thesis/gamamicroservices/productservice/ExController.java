package com.thesis.gamamicroservices.productservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ExController {

    @Autowired
    Environment env;

    @GetMapping(path="/status") //por request params de de user_id optional, pagination, size, sort
    public String test()  {
        return "secrete token: " + env.getProperty("test.variable");
    }
}

