package com.example.jpa_test.model.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ValidationTestDTO {
    @NotEmpty
    private String name;
    @Range(min = 140, max = 190)
    private int height;
    @Max(110)
    private int weight;
    @AssertFalse
    private boolean isCriminal;
    @Size(max = 5)
    private List<LocalDate> visitHistory;

    @Builder
    public ValidationTestDTO(String name, int height, int weight, boolean isCriminal, List<LocalDate> visitHistory) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.isCriminal = isCriminal;
        this.visitHistory = visitHistory;
    }
}
