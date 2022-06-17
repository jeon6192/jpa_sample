package com.example.jpa_test.service;

import com.example.jpa_test.exception.UserException;
import com.example.jpa_test.model.entity.CryptoTest;
import com.example.jpa_test.model.entity.Member;
import com.example.jpa_test.model.entity.Name;
import com.example.jpa_test.model.entity.Role;
import com.example.jpa_test.model.enums.UserAuthority;
import com.example.jpa_test.model.enums.UserError;
import com.example.jpa_test.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.ap.internal.util.Collections;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class TestService {
    private final CryptoTestRepository cryptoTestRepository;
    private final CryptoTestRepositoryImpl cryptoTestRepositoryImpl;
    private final AesConstRepository aesConstRepository;

    String decryptedName = "전승우", encryptedName = "BxCjZPYIz9ZSHFXwWasE4w=="
            , decryptedEnFirstName = "seung woo", encryptedEnFirstName = "IcwjivKmbb4uYZKV4f3nag=="
            , decryptedBirth = "19941012", encryptedBirth = "BLthsl1wjhUeGbj/C6Xf/g==";

    private String key = "cGF5aGFkYTA5MjhAQCEh";
    private byte[] iv = "4c208fbe-7b76-26cd-5016-4a820159eb10".getBytes(StandardCharsets.UTF_8);

    public TestService(CryptoTestRepository cryptoTestRepository, CryptoTestRepositoryImpl cryptoTestRepositoryImpl,
                       AesConstRepository aesConstRepository) {
        this.cryptoTestRepository = cryptoTestRepository;
        this.cryptoTestRepositoryImpl = cryptoTestRepositoryImpl;
        this.aesConstRepository = aesConstRepository;
    }

    public Map<String, Object> cryptoTest() {
        String text = "test";

        CryptoTest cryptoTestEntity = CryptoTest.builder()
                .origin(text)
                .cryptoText(text)
                .algorithm("AES/CBC/PKCS5Padding")
                .secretKey(key)
                .iv(iv)
                .build();

        String converted = aesConstRepository.encrypt(cryptoTestEntity.getCryptoText());
//        CryptoTest cryptoTest = cryptoTestRepositoryImpl.select(1L);
//        AesConst aesConst = aesConstRepository.findById(1L).orElse(null);
//        cryptoTestRepositoryImpl.save2(cryptoTestEntity);
//        cryptoTestEntity.encrypt(cryptoTestRepository.encrypt(cryptoTestEntity.getCryptoText()));
        cryptoTestRepository.save(cryptoTestEntity);

        Map<String, Object> result = new HashMap<>();
//        result.put("cryptoTest", cryptoTest);
//        result.put("aesConst", aesConst);
        result.put("converted", converted);


        return result;
    }
}
