package com.bookly.dto;

import com.bookly.model.StatusUsuario;
import com.bookly.model.TipoUsuario;
import lombok.Data;

@Data
public class UsuarioPutRequestBody {

    private String nome;
    private String email;

    private String cidade;
    private String estado;
    private TipoUsuario tipoUsuario;
    private StatusUsuario status;
    private String fotoPerfil;
    private String biografia;
}
