package com.example.jpa_test.model.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FamDTO {
    private Long idx;
    private String name;
    private String description;

    @Builder
    public FamDTO(Long idx, String name, String description) {
        this.idx = idx;
        this.name = name;
        this.description = description;
    }
}
