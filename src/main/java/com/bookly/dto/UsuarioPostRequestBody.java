package com.bookly.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioPostRequestBody {

    @NotBlank(message = "O campo 'nome' não pode ser vazio.")
    private String nome;

    @NotBlank(message = "O campo 'email' não pode ser vazio.")
    @Email(message = "O e-mail fornecido não é válido.")
    private String email;

    @NotBlank(message = "A 'senha' é obrigatória e não pode ser vazia.")
    private String senha;

    private String cidade;
    private String estado;

    private String fotoPerfil;
    private String biografia;
}
