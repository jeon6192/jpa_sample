package com.example.jpa_test.util;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.KeySpec;
import java.util.Base64;

public class CryptoUtil {

    private static final String AES_DB_KEY = "cGF5aGFkYTA5MjhAQCEh";
    private static final byte[] vectorKey = "4c208fbe-7b76-26cd-5016-4a820159eb10".getBytes(StandardCharsets.UTF_8);

    public static String encrpytSHA512(String text) {
        String encPw = text;

        if (text != null && !text.equals("")) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-512");
                md.update(text.getBytes());
                encPw = String.format("%0128x", new BigInteger(1, md.digest()));
            } catch (NullPointerException e) {
                throw e;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return encPw;
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
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(decodeBase64(key)), getIvParameterSpec(iv));
        byte[] decrypted = Base64.getDecoder().decode(text.getBytes(StandardCharsets.UTF_8));
        return new String(cipher.doFinal(decrypted), StandardCharsets.UTF_8);
    }

    public static String decodeBase64(String origin) {
        return new String(Base64.getDecoder().decode(origin));
    }

    private static Key generateMySQLAESKey(String decodedKey) {
        byte[] keyBytes16 = new byte[16];
        byte[] b = decodedKey.getBytes(StandardCharsets.UTF_8);
        int length = b.length;
        if (length > keyBytes16.length) {
            length = keyBytes16.length;
        }

        System.arraycopy(b, 0, keyBytes16, 0, length);

        return new SecretKeySpec(keyBytes16, "AES");
    }

    private static SecretKey getSecretKey(String decodedKey) {
        byte[] keyBytes16 = new byte[16];
        byte[] b = decodedKey.getBytes(StandardCharsets.UTF_8);
        int length = b.length;
        if (length > keyBytes16.length) {
            length = keyBytes16.length;
        } else if (length < keyBytes16.length) {
            StringBuilder decodedKeyBuilder = new StringBuilder(decodedKey);
            decodedKeyBuilder.append(" ".repeat(16 - length));
            decodedKey = decodedKeyBuilder.toString();
            b = decodedKey.getBytes(StandardCharsets.UTF_8);
        }

        System.arraycopy(b, 0, keyBytes16, 0, length);

        return new SecretKeySpec(keyBytes16, "AES");
    }

    private static IvParameterSpec getIvParameterSpec(byte[] iv) {
        return new IvParameterSpec(new String(iv).substring(0, 16).getBytes(StandardCharsets.UTF_8));
    }
}
