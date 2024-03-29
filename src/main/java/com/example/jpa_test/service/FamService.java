package com.example.jpa_test.service;

import com.example.jpa_test.exception.UserException;
import com.example.jpa_test.mapper.FamMapper;
import com.example.jpa_test.mapper.FamMembersMapper;
import com.example.jpa_test.mapper.MemberMapper;
import com.example.jpa_test.model.dto.FamDTO;
import com.example.jpa_test.model.dto.FamMemberDTO;
import com.example.jpa_test.model.dto.FamMembersDTO;
import com.example.jpa_test.model.dto.UserResponse;
import com.example.jpa_test.model.entity.Fam;
import com.example.jpa_test.model.entity.FamMembers;
import com.example.jpa_test.model.entity.Member;
import com.example.jpa_test.model.enums.UserError;
import com.example.jpa_test.repository.FamMembersRepository;
import com.example.jpa_test.repository.FamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FamService {

    private final FamRepository famRepository;
    private final FamMembersRepository famMembersRepository;
    private final MemberService memberService;

    public FamService(FamRepository famRepository, FamMembersRepository famMembersRepository, MemberService memberService) {
        this.famRepository = famRepository;
        this.famMembersRepository = famMembersRepository;
        this.memberService = memberService;
    }


    public FamDTO createFam(FamDTO famDTO) {
        return FamMapper.INSTANCE.toDto(famRepository.save(FamMapper.INSTANCE.toEntity(famDTO)));
    }

    public List<FamDTO> getFams() {
        return FamMapper.INSTANCE.toDtoList(famRepository.findAll());
    }

    public FamDTO getFamById(Long famIdx) throws UserException {
        return FamMapper.INSTANCE.toDto(famRepository.findById(famIdx)
                .orElseThrow(() -> new UserException(UserError.HAVE_NO_DATA)));
    }

    public FamMembersDTO addMemberToFam(Long famIdx, Long memberIdx, FamMemberDTO famMemberDTO) throws UserException {
        Fam fam = famRepository.findById(famIdx).orElseThrow(() -> new UserException(UserError.HAVE_NO_DATA));
        Member member = MemberMapper.INSTANCE.toEntity(memberService.getMemberById(memberIdx));

        FamMembers famMembers = FamMembers.builder()
                .fam(fam)
                .member(member)
                .isMaster(famMemberDTO.getIsMaster())
                .description(famMemberDTO.getDescription())
                .build();

        famMembersRepository.save(famMembers);

        FamMembersDTO temp = getMembersByFam(famIdx);
        return FamMembersDTO.builder()
                .fam(temp.getFam())
                .famMemberDTOList(temp.getFamMemberDTOList().stream()
                        .filter(v -> Objects.equals(v.getMember().getIdx(), memberIdx)).collect(Collectors.toList()))
                .build();
    }

    public FamMembersDTO getMembersByFam(Long famIdx) throws UserException {
        Fam fam = famRepository.findById(famIdx).orElseThrow(() -> new UserException(UserError.HAVE_NO_DATA));
        List<FamMembers> famMembers = famMembersRepository.findByFam(fam);

        List<FamMemberDTO> famMemberDTOList = famMembers.stream()
                .map(FamMembersMapper.INSTANCE::toFamMemberDto)
                .collect(Collectors.toList());

        return FamMembersDTO.builder()
                .fam(FamMapper.INSTANCE.toDto(fam))
                .famMemberDTOList(famMemberDTOList)
                .build();
    }

    public UserResponse deleteFam(Long famIdx) throws UserException {
        Fam fam = famRepository.findById(famIdx)
                .orElseThrow(() -> new UserException(UserError.HAVE_NO_DATA));
        famRepository.delete(fam);

        return UserResponse.createSuccessResponse();
    }

    public UserResponse deleteFamMembers(Long famIdx, Long memberIdx) throws UserException {
        Fam fam = FamMapper.INSTANCE.toEntity(getFamById(famIdx));
        Member member = MemberMapper.INSTANCE.toEntity(memberService.getMemberById(memberIdx));
        FamMembers famMembers = famMembersRepository.findByFamAndMember(fam, member)
                .orElseThrow(() -> new UserException(UserError.HAVE_NO_DATA));

        famMembersRepository.delete(famMembers);

        return UserResponse.createSuccessResponse();
    }

}
