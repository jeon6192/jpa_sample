package com.example.jpa_test.mapper;

import com.example.jpa_test.model.dto.MemberDTO;
import com.example.jpa_test.model.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    Member toEntity(MemberDTO memberDTO);

    MemberDTO toDto(Member member);

    List<Member> toEntities(List<MemberDTO> memberDTOList);

    List<MemberDTO> toDtoList(List<Member> members);
}
