package com.example.jpa_test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
class StringTest {

    @Test
    void stringTest() {
        String status = "3000:Remit Success";
        List<String> notFailureCodes = Arrays.asList("3000", "3050", "3211", "3212", "3213", "3214");
        List<String> notFailureMessages = Arrays.asList("Remit Success", "Remit Acknowledged, status PENDING", "Beneficiary Opt-in Pending",
                "Beneficiary Pending Cashout", "Beneficiary Pending Review", "Beneficiary Pending Registration");

        boolean isSuccessful = notFailureCodes.stream().anyMatch(status::contains) || notFailureMessages.stream().anyMatch(status::contains);

        System.out.println(isSuccessful);
    }
}
