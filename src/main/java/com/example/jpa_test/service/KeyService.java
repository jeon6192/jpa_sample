package com.example.jpa_test.service;

import com.example.jpa_test.repository.AesConstRepository;
import com.example.jpa_test.repository.CryptoTestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KeyService {

    private final AesConstRepository aesConstRepository;

    private String key = "cGF5aGFkYTA5MjhAQCEh";

    public KeyService(AesConstRepository aesConstRepository) {
        this.aesConstRepository = aesConstRepository;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setAesKey() {
        aesConstRepository.setAesKey(this.key);
    }

    public String encrypt(String value) {
        return aesConstRepository.encrypt(value);
    }

    public String decrypt(String value) {
        return aesConstRepository.decrypt(value);
    }
}
