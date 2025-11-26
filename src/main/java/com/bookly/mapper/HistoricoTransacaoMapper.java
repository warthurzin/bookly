package com.bookly.mapper;

import com.bookly.dto.HistoricoTransacaoGetResponse;
import com.bookly.model.HistoricoTransacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {LivroMapper.class, UsuarioMapper.class})
public interface HistoricoTransacaoMapper {

    @Mapping(source = "solicitacao.id", target = "solicitacaoId")
    HistoricoTransacaoGetResponse toResponse(HistoricoTransacao historico);
}