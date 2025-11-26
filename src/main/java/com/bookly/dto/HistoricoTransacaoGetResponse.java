package com.bookly.dto;

import com.bookly.model.TipoTransacao;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoricoTransacaoGetResponse {

    private Long id;

    private LivroGetResponse livro;
    private UsuarioGetResponse doador;
    private UsuarioGetResponse solicitante;

    private Long solicitacaoId;

    private TipoTransacao tipoTransacao;
    private String observacoes;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataTransacao;
}