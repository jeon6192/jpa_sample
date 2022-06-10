package com.example.jpa_test.service;

import com.example.jpa_test.exception.UserException;
import com.example.jpa_test.mapper.MemberMapper;
import com.example.jpa_test.model.dto.MemberDTO;
import com.example.jpa_test.model.dto.SimpleResponse;
import com.example.jpa_test.model.entity.Member;
import com.example.jpa_test.model.entity.Role;
import com.example.jpa_test.model.enums.UserAuthority;
import com.example.jpa_test.model.enums.UserError;
import com.example.jpa_test.repository.MemberRepository;
import com.example.jpa_test.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public MemberDTO createMember(MemberDTO memberDTO) throws UserException {
        Member member = MemberMapper.INSTANCE.toEntity(memberDTO);
        Role role = roleRepository.findByUserAuthority(UserAuthority.ROLE_USER)
                .orElseThrow(() -> new UserException(UserError.HAVE_NO_DATA));
        member.addRole(role);
        member.encodePassword(passwordEncoder.encode(member.getPassword()));

        return MemberMapper.INSTANCE.toDto(memberRepository.save(member));
    }

    public List<MemberDTO> getMembers() {
        return MemberMapper.INSTANCE.toDtoList(memberRepository.findAll());
    }

    public MemberDTO getMemberById(Long memberIdx) throws UserException {
        return MemberMapper.INSTANCE.toDto(memberRepository.findById(memberIdx)
                .orElseThrow(() -> new UserException(UserError.HAVE_NO_DATA)));
    }

    public SimpleResponse deleteMember(Long memberIdx) throws UserException {
        Member member = memberRepository.findById(memberIdx)
                .orElseThrow(() -> new UserException(UserError.HAVE_NO_DATA));
        memberRepository.delete(member);

        return SimpleResponse.createSuccessResponse();
    }
}
