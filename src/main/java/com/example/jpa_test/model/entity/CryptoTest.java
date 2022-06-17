package com.example.jpa_test.model.entity;

import com.example.jpa_test.util.DBCryptAttributeConverter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CryptoTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String origin;

    @Convert(converter = DBCryptAttributeConverter.class)
    private String cryptoText;

    private String algorithm;

    private String secretKey;

    private byte[] iv;

    @Builder
    public CryptoTest(Long idx, String origin, String cryptoText, String algorithm, String secretKey, byte[] iv) {
        this.idx = idx;
        this.origin = origin;
        this.cryptoText = cryptoText;
        this.algorithm = algorithm;
        this.secretKey = secretKey;
        this.iv = iv;
    }

    public void encrypt(String encrypt) {
        this.cryptoText = encrypt;
    }
}
