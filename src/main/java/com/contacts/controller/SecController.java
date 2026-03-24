package com.contacts.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class SecController {


    @GetMapping("/about")
    public String index() {
        return "Hello World";
    }

    @GetMapping("/test")
    public String test() {
        return "Hello World testing";
    }
}
