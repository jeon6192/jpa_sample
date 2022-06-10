package com.example.jpa_test.model.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FamMembersDTO {
    private FamDTO fam;
    private List<FamMemberDTO> famMemberDTOList;

    @Builder
    public FamMembersDTO(FamDTO fam, List<FamMemberDTO> famMemberDTOList) {
        this.fam = fam;
        this.famMemberDTOList = famMemberDTOList;
    }
}
