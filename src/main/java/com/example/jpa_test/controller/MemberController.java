package com.example.jpa_test.controller;

import com.example.jpa_test.config.security.CustomUserDetails;
import com.example.jpa_test.exception.UserException;
import com.example.jpa_test.model.dto.MemberDTO;
import com.example.jpa_test.model.dto.SimpleResponse;
import com.example.jpa_test.model.enums.UserError;
import com.example.jpa_test.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/me")
    public ResponseEntity<MemberDTO> main(@AuthenticationPrincipal CustomUserDetails customUserDetails) throws UserException {
        Long idx = customUserDetails.getMember().getIdx();
        if (idx == null) {
            throw new UserException(UserError.BAD_REQUEST);
        }

        return ok(memberService.getMemberById(idx));
    }

    @PostMapping("/signup")
    public ResponseEntity<MemberDTO> createMember(@Validated @RequestBody MemberDTO memberDTO, BindingResult bindingResult) throws UserException {
        if (bindingResult.hasErrors()) {
            String message = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new UserException(UserError.BAD_REQUEST, message);
        }
        return ok(memberService.createMember(memberDTO));
    }

    @GetMapping("/member")
    @PreAuthorize("@apiGuard.checkAuthority(null)")
    public ResponseEntity<List<MemberDTO>> getMembers() {
        return ok(memberService.getMembers());
    }

    @GetMapping("/member/{memberIdx}")
    @PreAuthorize("@apiGuard.checkAuthority(#memberIdx)")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable Long memberIdx) throws UserException {
        if (memberIdx == null) {
            throw new UserException(UserError.BAD_REQUEST);
        }

        return ok(memberService.getMemberById(memberIdx));
    }

    @DeleteMapping("/member/{memberIdx}")
    @PreAuthorize("@apiGuard.checkAuthority(#memberIdx)")
    public ResponseEntity<SimpleResponse> deleteMember(@PathVariable Long memberIdx) throws UserException {
        return ok(memberService.deleteMember(memberIdx));
    }

    @PutMapping("/member/{memberIdx}/id")
    @PreAuthorize("@apiGuard.checkAuthority(#memberIdx)")
    public ResponseEntity<MemberDTO> updateMemberId(@PathVariable Long memberIdx, @RequestBody MemberDTO memberDTO)
            throws UserException {
        return ok(memberService.updateMemberId(memberIdx, memberDTO));
    }

    @PutMapping("/member/{memberIdx}/password")
    @PreAuthorize("@apiGuard.checkAuthority(#memberIdx)")
    public ResponseEntity<MemberDTO> updateMemberPassword(@PathVariable Long memberIdx, @RequestBody MemberDTO memberDTO)
            throws UserException {
        return ok(memberService.updateMemberPassword(memberIdx, memberDTO));
    }

    @PutMapping("/member/{memberIdx}/info")
    @PreAuthorize("@apiGuard.checkAuthority(#memberIdx)")
    public ResponseEntity<MemberDTO> updateMemberInfo(@PathVariable Long memberIdx, @RequestBody MemberDTO memberDTO)
            throws UserException {
        return ok(memberService.updateMemberInfo(memberIdx, memberDTO));
    }
}
