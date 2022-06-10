package com.example.jpa_test.mapper;

import com.example.jpa_test.model.dto.FamDTO;
import com.example.jpa_test.model.entity.Fam;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FamMapper {

    FamMapper INSTANCE = Mappers.getMapper(FamMapper.class);

    Fam toEntity(FamDTO famDTO);

    FamDTO toDto(Fam fam);

    List<Fam> toEntities(List<FamDTO> famDTOList);

    List<FamDTO> toDtoList(List<Fam> fams);
}
