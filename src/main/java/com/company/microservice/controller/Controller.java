package com.company.microservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "https://spring-cloud-gateway-production-9d8a.up.railway.app")
@RestController
public class Controller {

    @GetMapping("/")
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("Hello from Railway + Spring!");
    }
}
