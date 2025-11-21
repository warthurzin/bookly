package com.bookly.dto;

import lombok.Data;

@Data
public class UsuarioPutRequestBody {

    private String nome;
    private String email;

    private String cidade;
    private String estado;

    private String fotoPerfil;
    private String biografia;
}
