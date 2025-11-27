package com.bookly.dto;

import com.bookly.model.TipoAvaliacao;
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
public class AvaliacaoGetResponse {

    private Long id;
    private Long historicoId;

    private UsuarioGetResponse avaliador;

    private UsuarioGetResponse avaliado;

    private Integer nota;
    private String comentario;
    private TipoAvaliacao tipoAvaliacao;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataCadastro;
}