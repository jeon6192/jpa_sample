package com.example.jpa_test;

import com.example.jpa_test.model.dto.FamDTO;
import com.example.jpa_test.model.dto.FamMemberDTO;
import com.example.jpa_test.model.dto.FamMembersDTO;
import com.example.jpa_test.service.FamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class FamServiceTest {
    @Autowired
    private FamService famService;

    @Test
    void createFam() throws Exception {
        FamDTO famDTO = FamDTO.builder()
                .name("test-name")
                .description("desc")
                .build();

        FamDTO savedFamDTO = famService.createFam(famDTO);

        assertEquals(famDTO.getName(), savedFamDTO.getName());
    }

    @Test
    void getFamByIdx() throws Exception {
        Long idx = 1L;

        FamDTO famDTO = famService.getFamById(idx);

        assertEquals(idx, famDTO.getIdx());
    }

    @Test
    void addMemberToFam() throws Exception {
        Long famIdx = 1L;
        Long memberIdx = 1L;

        FamMemberDTO famMemberDTO = FamMemberDTO.builder()
                .isMaster(true)
                .description("desc")
                .build();

        FamMembersDTO famMembersDTO = famService.addMemberToFam(famIdx, memberIdx, famMemberDTO);

        assertEquals(famIdx, famMembersDTO.getFam().getIdx());
        assertTrue(famMembersDTO.getFamMemberDTOList().stream().anyMatch(v -> Objects.equals(v.getMember().getIdx(), memberIdx)));
    }
}
