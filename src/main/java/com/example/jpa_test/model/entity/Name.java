package com.example.jpa_test.model.entity;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Name {
    @NotEmpty(message = "이름은 필수값 입니다.")
    private String firstName;
    @NotEmpty(message = "이름은 필수값 입니다.")
    private String lastName;

    @Builder
    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
