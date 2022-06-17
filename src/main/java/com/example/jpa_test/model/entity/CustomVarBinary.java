package com.example.jpa_test.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class CustomVarBinary implements Serializable {
    private String value;
}
