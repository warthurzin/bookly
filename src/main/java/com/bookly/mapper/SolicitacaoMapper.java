package com.bookly.mapper;

import com.bookly.dto.SolicitacaoGetResponse;
import com.bookly.dto.SolicitacaoPostRequestBody;
import com.bookly.model.Solicitacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {LivroMapper.class, UsuarioMapper.class})
public interface SolicitacaoMapper {

    SolicitacaoGetResponse toResponse(Solicitacao solicitacao);

    @Mapping(target = "livro", ignore = true)
    @Mapping(target = "solicitante", ignore = true)
    @Mapping(target = "doador", ignore = true)
    Solicitacao toSolicitacao(SolicitacaoPostRequestBody solicitacaoPostRequestBody);
}