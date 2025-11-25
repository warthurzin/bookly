package com.bookly.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoriaGetResponse {

    private Long id;
    private String nome;
    private String descricao;
    private LocalDateTime dataCadastro;
}

