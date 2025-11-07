package com.bookly.dto;

import com.bookly.model.StatusUsuario;
import com.bookly.model.TipoUsuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UsuarioGetResponse {

    private long id;
    private String nome;
    private String email;

    private String cidade;
    private String estado;
    private TipoUsuario tipoUsuario;
    private StatusUsuario status;
    private String fotoPerfil;
    private String biografia;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataCadastro;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataAtualizacao;
}
