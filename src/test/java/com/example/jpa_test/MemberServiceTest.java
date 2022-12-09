package com.example.jpa_test;

import com.example.jpa_test.model.dto.MemberDTO;
import com.example.jpa_test.model.entity.Name;
import com.example.jpa_test.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("멤버 생성")
    void createMember() throws Exception {
        MemberDTO memberDTO = MemberDTO.builder()
                .memberId("test-id")
                .password(passwordEncoder.encode("pass"))
                .birth("990101")
                .name(Name.builder()
                        .firstName("first")
                        .lastName("last")
                        .build())
                .phone("01001010202")
                .build();

        MemberDTO savedMemberDTO = memberService.createMember(memberDTO);

        assertEquals(memberDTO.getName().getFirstName(), savedMemberDTO.getName().getFirstName());
    }

    @Test
    void getMemberByIdx() throws Exception {
        Long idx = 1L;

        MemberDTO memberDTO = memberService.getMemberById(idx);

        assertEquals(idx, memberDTO.getIdx());
    }

    @Test
    void updateTest() throws Exception {
        Long idx = 1L;

        String updateId = "updated";
        String updatePwd = "up123";
        String updateBirth = "010101";
        Name updateName = Name.builder().firstName("up").lastName("date").build();
        String updatePhone = "01199998888";

        MemberDTO updatedIdDto = memberService.updateMemberId(idx, MemberDTO.builder().memberId(updateId).build());
        MemberDTO updatedPwdDto = memberService.updateMemberPassword(idx, MemberDTO.builder().password(updatePwd).build());
        MemberDTO updatedInfoDto = memberService.updateMemberInfo(idx, MemberDTO.builder()
                .birth(updateBirth)
                .name(updateName)
                .phone(updatePhone)
                .build());

        assertEquals(updateId, updatedIdDto.getMemberId());
        assertTrue(passwordEncoder.matches(updatePwd, updatedPwdDto.getPassword()));
        assertEquals(updateBirth, updatedInfoDto.getBirth());
        assertEquals(updateName.getFirstName(), updatedInfoDto.getName().getFirstName());
        assertEquals(updateName.getLastName(), updatedInfoDto.getName().getLastName());
        assertEquals(updatePhone, updatedInfoDto.getPhone());
    }
}
