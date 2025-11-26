package com.bookly.mapper;

import com.bookly.dto.NotificacaoGetResponse;
import com.bookly.model.Notificacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificacaoMapper {

    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "solicitacao.id", target = "solicitacaoId")
    NotificacaoGetResponse toResponse(Notificacao notificacao);
}