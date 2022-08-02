package com.example.jpa_test.controller;

import com.example.jpa_test.model.dto.ValidationTestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
public class ValidationController {

    @PostMapping("/validation")
    public ResponseEntity<ValidationTestDTO> nameTest(@RequestBody @Valid ValidationTestDTO dto) {
        return ResponseEntity.ok(dto);
    }
}
