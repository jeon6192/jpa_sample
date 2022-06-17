package com.example.jpa_test.controller;

import com.example.jpa_test.exception.UserException;
import com.example.jpa_test.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }


    @PostMapping("/crypto")
    public ResponseEntity<?> insert() throws UserException {

        return ok(testService.cryptoTest());
    }
}
