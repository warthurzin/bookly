package com.bookly.dto;

import com.bookly.model.TipoNotificacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificacaoGetResponse {

    private Long id;

    private Long usuarioId;

    private Long solicitacaoId;

    private TipoNotificacao tipoNotificacao;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataLeitura;

    public boolean isLida() {
        return dataLeitura != null;
    }
}