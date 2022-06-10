package com.example.jpa_test.controller;

import com.example.jpa_test.exception.UserException;
import com.example.jpa_test.model.dto.FamDTO;
import com.example.jpa_test.model.dto.FamMemberDTO;
import com.example.jpa_test.model.dto.FamMembersDTO;
import com.example.jpa_test.model.dto.SimpleResponse;
import com.example.jpa_test.model.enums.UserError;
import com.example.jpa_test.service.FamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
public class FamController {
    
    private final FamService famService;

    public FamController(FamService famService) {
        this.famService = famService;
    }


    @PostMapping("/fam")
    public ResponseEntity<FamDTO> createFam(@RequestBody FamDTO famDTO) {
        return ok(famService.createFam(famDTO));
    }

    @GetMapping("/fam")
    public ResponseEntity<List<FamDTO>> getFams() {
        return ok(famService.getFams());
    }

    @GetMapping("/fam/{famIdx}")
    public ResponseEntity<FamDTO> getFamById(@PathVariable Long famIdx) throws UserException {
        if (famIdx == null) {
            throw new UserException(UserError.BAB_REQUEST);
        }

        return ok(famService.getFamById(famIdx));
    }

    @DeleteMapping("/fam/{famIdx}")
    @PreAuthorize("@famGuard.checkAuthority(#famIdx)")
    public ResponseEntity<SimpleResponse> deleteFam(@PathVariable Long famIdx) throws UserException {
        if (famIdx == null) {
            throw new UserException(UserError.BAB_REQUEST);
        }

        return ok(famService.deleteFam(famIdx));
    }

    @PostMapping("/fam/{famIdx}/{memberIdx}")
    public ResponseEntity<FamMembersDTO> addMemberToFam(@PathVariable Long famIdx, @PathVariable Long memberIdx,
                                                        @RequestBody FamMemberDTO famMemberDTO) throws UserException {
        if (famIdx == null || memberIdx == null || famMemberDTO == null) {
            throw new UserException(UserError.BAB_REQUEST);
        }

        return ok(famService.addMemberToFam(famIdx, memberIdx, famMemberDTO));
    }

    @GetMapping("/fam/member/{famIdx}")
    public ResponseEntity<FamMembersDTO> getMembersByFam(@PathVariable Long famIdx) throws UserException {
        if (famIdx == null) {
            throw new UserException(UserError.BAB_REQUEST);
        }

        return ok(famService.getMembersByFam(famIdx));
    }

    @DeleteMapping("/fam/{famIdx}/{memberIdx}")
    @PreAuthorize("@famGuard.checkAuthority(#famIdx, #memberIdx)")
    public ResponseEntity<SimpleResponse> deleteFamMembers(@PathVariable Long famIdx, @PathVariable Long memberIdx) throws UserException {
        if (famIdx == null) {
            throw new UserException(UserError.BAB_REQUEST);
        }

        return ok(famService.deleteFam(famIdx));
    }

}
