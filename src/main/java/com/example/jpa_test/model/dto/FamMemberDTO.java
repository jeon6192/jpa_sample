package com.example.jpa_test.model.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FamMemberDTO {
    private MemberDTO member;
    private Boolean isMaster;
    private String description;

    @Builder
    public FamMemberDTO(MemberDTO member, Boolean isMaster, String description) {
        this.member = member;
        this.isMaster = isMaster;
        this.description = description;
    }
}
