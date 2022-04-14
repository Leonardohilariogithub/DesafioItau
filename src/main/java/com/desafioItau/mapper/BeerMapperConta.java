package com.desafioItau.mapper;

import com.desafioItau.dtos.ContaDto;
import com.desafioItau.entidades.ContaEntidade;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BeerMapperConta {

    BeerMapperConta INSTANCE = Mappers.getMapper(BeerMapperConta.class);

    ContaEntidade toModel(ContaDto contaDto);

    ContaDto toDTO(ContaEntidade contaEntidade);
}
