package com.bookly.mapper;

import com.bookly.dto.AvaliacaoGetResponse;
import com.bookly.model.Avaliacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UsuarioMapper.class})
public interface AvaliacaoMapper {

    @Mapping(source = "historico.id", target = "historicoId")
    AvaliacaoGetResponse toResponse(Avaliacao avaliacao);
}