package com.example.jpa_test.util;

import com.example.jpa_test.repository.AesConstRepository;
import com.example.jpa_test.repository.AesConstRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Component
@Converter
@Configurable
public class DBCryptAttributeConverter implements AttributeConverter<String, String> {

    private static AesConstRepositoryImpl aesConstRepository;

    @Autowired
    public void init(AesConstRepositoryImpl aesConstRepository) {
        DBCryptAttributeConverter.aesConstRepository = aesConstRepository;
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return aesConstRepository.encrypt(attribute);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        try {
            return aesConstRepository.decrypt(dbData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
