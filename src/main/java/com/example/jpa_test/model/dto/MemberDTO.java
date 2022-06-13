package com.example.jpa_test.model.dto;

import com.example.jpa_test.model.entity.Name;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDTO {
    private Long idx;
    @NotBlank(message = "ID는 필수값 입니다.")
    private String memberId;
    @NotBlank(message = "비밀번호는 필수값 입니다.")
//    @Pattern(regexp = "((?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,18})")
    private String password;
    @NotBlank(message = "생일은 필수값 입니다.")
    private String birth;
    private Name name;
    @NotBlank(message = "휴대번호는 필수값 입니다.")
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
