package com.example.jpa_test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class CryptoTest {

    /*aes-key=ekFkYWplaWFlaklKSUpFSkVKRkRKa3NhamRlaWFlZWk=
    aes-iv=1234567890123456
    confirm-server-ip=https://121.156.122.223
    reserved-data-aes-key=Z2FsYXhpYVNlY3JldEtleQ==
    reserved-data-aes-iv=2123456789012536*/
    private final String aesKey = new String(Base64.getDecoder().decode("ekFkYWplaWFlaklKSUpFSkVKRkRKa3NhamRlaWFlZWk="));
    private final String aesIv = "1234567890123456";
    private final String reservedDataAesKey = new String(Base64.getDecoder().decode("Z2FsYXhpYVNlY3JldEtleQ=="));
    private final String reservedDataAesIv = "2123456789012536";

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    public String encryptAES256(String key, String iv, String text) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        if (key != null && text != null) {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivParamSpec;
            if (iv == null) {
                byte[] ivByte = new byte[16];
                new SecureRandom().nextBytes(ivByte);
                ivParamSpec = new IvParameterSpec(ivByte);
            } else {
                ivParamSpec = new IvParameterSpec(iv.getBytes());
            }
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

            byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(encrypted);
        } else {
            return null;
        }
    }

    public String decryptAES256(String key, String iv, String text) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        if (key != null && text != null) {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivParamSpec;
            if (iv == null) {
                byte[] ivByte = new byte[16];
                new SecureRandom().nextBytes(ivByte);
                ivParamSpec = new IvParameterSpec(ivByte);
            } else {
                ivParamSpec = new IvParameterSpec(iv.getBytes());
            }
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

            byte[] decodedBytes = Base64.getDecoder().decode(text);
            byte[] decrypted = cipher.doFinal(decodedBytes);

            return new String(decrypted, StandardCharsets.UTF_8);
        } else {
            return null;
        }
    }

    @Test
    void aes256() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String encryptedTxnNo = encryptAES256(aesKey, aesIv, "GT2022070700152");
        String encryptedBarcode = encryptAES256(aesKey, aesIv, "520753506566");
        String encryptedAmt = encryptAES256(aesKey, aesIv, "30000");
        String encryptedReservedData = encryptAES256(reservedDataAesKey, reservedDataAesIv, "18");

        System.out.println("log for breaking point");
    }
}
