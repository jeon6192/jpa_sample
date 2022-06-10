package com.example.jpa_test.model.entity;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Name {
    private String firstName;

    private String lastName;
}
