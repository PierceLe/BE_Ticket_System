package com.scaffold.ticketSystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloSpringController {
    @GetMapping("/hello")
    String sayHello() {
        return "Hello Spring Boot 3!";
    }
}
