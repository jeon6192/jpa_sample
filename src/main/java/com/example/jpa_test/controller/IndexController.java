package com.example.jpa_test.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class IndexController {

    @GetMapping("/ping")
    public String check() {
        return "pong";
    }
}
