package com.example.jpa_test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JpaTestApplicationTests {

    @Test
    void simpleTest() {
        long startTime = System.currentTimeMillis();
        int max = BigDecimal.valueOf(999).divide(BigDecimal.valueOf(2)).setScale(0, RoundingMode.DOWN).intValue();

        int a = 0, b, c;
        int result = 0;

        parentLoop:
        while (a < max) {
            a++;
            b = a + 1;
            while (b < max) {
                c = 1000 - a - b;

                if (Math.pow(a, 2) + Math.pow(b, 2) == Math.pow(c, 2)) {
                    result = a * b * c;
                    break parentLoop;
                }
                b++;
            }
        }

        System.out.println(result);
        long endTime = System.currentTimeMillis();
        BigDecimal runTime = BigDecimal.valueOf(endTime).subtract(BigDecimal.valueOf(startTime));
        System.out.printf("runtime : %d ms%n", runTime.longValue());

    }


    @Test
    void test20() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        long timestamp = new Timestamp(System.currentTimeMillis()).getTime();
        String method = "POST";
        String sort = "sms";
        String path = "ncp:sms:fkr:262133965230:payhada_au/messages";

        String space = " ";
        String newLine = "\n";
        String url = "/" + sort + "/v2/services/" + path;
        String accessKey = "D9430099B5B0480E0CB4";
        String secretKey = "EAEA9B3115127D5964A57257B0197670AF5513D7";

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(String.valueOf(timestamp))
                .append(newLine)
                .append(accessKey)
                .toString();
        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.getEncoder().encodeToString(rawHmac);



        Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-ncp-apigw-timestamp", String.valueOf(timestamp2.getTime()));
        headers.add("x-ncp-iam-access-key", "D9430099B5B0480E0CB4");
        headers.add("x-ncp-apigw-signature-v2", encodeBase64String);

        System.out.println(headers);
    }
}
