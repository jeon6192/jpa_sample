package com.example.jpa_test.model.dto;

import com.example.jpa_test.model.entity.Name;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDTO {
    private Long idx;
    private String memberId;
    private String password;
    private String birth;
    private Name name;
    private String phone;

    @Builder
    public MemberDTO(Long idx, String memberId, String password, String birth, Name name, String phone) {
        this.idx = idx;
        this.memberId = memberId;
        this.password = password;
        this.birth = birth;
        this.name = name;
        this.phone = phone;
    }
}
