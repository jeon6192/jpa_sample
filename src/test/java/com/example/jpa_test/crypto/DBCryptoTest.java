package com.example.jpa_test.crypto;

import com.example.jpa_test.config.TestCryptoConfig;
import com.example.jpa_test.model.entity.AesConst;
import com.example.jpa_test.model.entity.CryptoTest;
import com.example.jpa_test.model.entity.QCryptoTest;
import com.example.jpa_test.repository.AesConstRepository;
import com.example.jpa_test.repository.CryptoTestRepository;
import com.example.jpa_test.repository.CryptoTestRepositoryImpl;
import com.example.jpa_test.service.KeyService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.EntityManager;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.NoSuchElementException;

import static com.example.jpa_test.model.entity.QCryptoTest.cryptoTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Import(TestCryptoConfig.class)
@ExtendWith(SpringExtension.class)
@DataJpaTest
class DBCryptoTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private CryptoTestRepository cryptoTestRepository;

    @Autowired
    private CryptoTestRepositoryImpl cryptoTestRepositoryImpl;

    @Autowired
    private AesConstRepository aesConstRepository;

    JPAQueryFactory queryFactory;

    @MockBean
    private KeyService keyService;


    String decryptedName = "전승우", encryptedName = "BxCjZPYIz9ZSHFXwWasE4w=="
            , decryptedEnFirstName = "seung woo", encryptedEnFirstName = "IcwjivKmbb4uYZKV4f3nag=="
            , decryptedBirth = "19941012", encryptedBirth = "BLthsl1wjhUeGbj/C6Xf/g==";

    private String key = "cGF5aGFkYTA5MjhAQCEh";
    private byte[] iv = "4c208fbe-7b76-26cd-5016-4a820159eb10".getBytes(StandardCharsets.UTF_8);

    @BeforeEach
    public void init() {
        queryFactory = new JPAQueryFactory(em);
    }

    @Test
    void save() {
        // given
        CryptoTest cryptoTest = CryptoTest.builder()
                .origin(decryptedName)
                .cryptoText(decryptedName)
                .algorithm("AES/CBC/PKCS5Padding")
                .secretKey(key)
                .iv(iv)
                .build();

        // when
//        CryptoTest savedCryptoTest = cryptoTestRepository.save(cryptoTest);
        CryptoTest savedCryptoTestByImpl = cryptoTestRepositoryImpl.save(cryptoTest);

        // then
//        assertEquals(encryptedName, savedCryptoTest.getCryptoText());
        assertEquals(encryptedName, savedCryptoTestByImpl.getCryptoText());
    }

    @Test
    void crypto() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        // given
        AesConst aesConst = aesConstRepository.findById(1L).orElseThrow(NoSuchElementException::new);
        byte[] aesConstIv = aesConst.getInitValue();

        // when
        String customEncryptedName1 = encryptAES(decryptedName, key, aesConstIv);
        String customEncryptedName2 = encryptAESOrigin(decryptedName, key, iv);

        // then
        assertEquals(encryptedName, customEncryptedName1);
        assertEquals(encryptedName, customEncryptedName2);
    }

    @Test
    void insertTest() {
        CryptoTest cryptoTestEntity = CryptoTest.builder()
                .origin(decryptedName)
                .cryptoText(decryptedName)
                .algorithm("AES/CBC/PKCS5Padding")
                .secretKey(key)
                .iv(iv)
                .build();
        Long idx = queryFactory.insert(cryptoTest)
                .columns(cryptoTest.algorithm, cryptoTest.origin, cryptoTest.cryptoText, cryptoTest.secretKey, cryptoTest.iv)
                .values(cryptoTestEntity.getAlgorithm(), cryptoTestEntity.getOrigin(),
//                        Expressions.stringTemplate("encrypt({0})", cryptoTest.getCryptoText()),
                        cryptoTestEntity.getCryptoText(),
                        cryptoTestEntity.getSecretKey(), cryptoTestEntity.getIv())
                .execute();

        CryptoTest result = queryFactory.select(cryptoTest)
                .where(cryptoTest.idx.eq(idx))
                .fetchOne();

        assertNotNull(result);
        assertEquals(result.getOrigin(), decryptedName);
    }



    public static String encryptAESOrigin(String text, String key, byte[] iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, generateMySQLAESKey(), getIvParameterSpec(iv));
        byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        return new String(Base64.getEncoder().encode(encrypted));
    }

    public static String encryptAES(String text, String key, byte[] iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(decodeBase64(key)), getIvParameterSpec(iv));
        byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        return new String(Base64.getEncoder().encode(encrypted));
    }

    public static String decryptAES(String text, String key, byte[] iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        cipher.init(Cipher.DECRYPT_MODE, generateMySQLAESKey(), getIvParameterSpec(iv));
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(decodeBase64(key)), getIvParameterSpec(iv));
        byte[] decrypted = Base64.getDecoder().decode(text.getBytes(StandardCharsets.UTF_8));
        return new String(cipher.doFinal(decrypted), StandardCharsets.UTF_8);
    }

    private static String decodeBase64(String origin) {
        return new String(Base64.getDecoder().decode(origin));
    }

    private static SecretKey getSecretKey(String decodedKey) {
        byte[] keyBytes16 = new byte[16];
        byte[] b = decodedKey.getBytes(StandardCharsets.UTF_8);
        int length = b.length;
        if (length > keyBytes16.length) {
            length = keyBytes16.length;
        }

        System.arraycopy(b, 0, keyBytes16, 0, length);

        return new SecretKeySpec(keyBytes16, "AES");
    }

    private static IvParameterSpec getIvParameterSpec(byte[] iv) {
        return new IvParameterSpec(new String(iv).substring(0, 16).getBytes(StandardCharsets.UTF_8));
    }

    public static Key generateMySQLAESKey() {
        byte[] keyBytes16 = new byte[16];
        byte[] b = decodeBase64("cGF5aGFkYTA5MjhAQCEh").getBytes(StandardCharsets.UTF_8);
        int length = b.length;
        if (length > keyBytes16.length) {
            length = keyBytes16.length;
        }

        System.arraycopy(b, 0, keyBytes16, 0, length);

        return new SecretKeySpec(keyBytes16, "AES");
    }
}
