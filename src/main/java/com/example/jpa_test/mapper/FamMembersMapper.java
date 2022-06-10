package com.example.jpa_test.mapper;

import com.example.jpa_test.model.dto.FamMemberDTO;
import com.example.jpa_test.model.dto.FamMembersDTO;
import com.example.jpa_test.model.entity.FamMembers;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FamMembersMapper {
    FamMembersMapper INSTANCE = Mappers.getMapper(FamMembersMapper.class);

    List<FamMembers> toEntities(List<FamMembersDTO> famDTOList);

    List<FamMembersDTO> toDtoList(List<FamMembers> famMembers);

    FamMemberDTO toFamMemberDto(FamMembers famMembers);
}
