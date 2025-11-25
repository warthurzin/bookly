package com.bookly.dto;

import com.bookly.model.CondicaoLivro;
import com.bookly.model.StatusLivro;
import com.bookly.model.TipoDisponibilidade;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class LivroGetResponse {
    private Long id;
    private Long usuarioId;
    private String titulo;
    private String autor;
    private String isbn;
    private String editora;
    private Integer anoPublicacao;
    private String descricao;
    private CondicaoLivro condicaoLivro;
    private TipoDisponibilidade tipoDisponibilidade;
    private StatusLivro statusLivro;
    private String fotoCapa;
    private Integer numeroPaginas;
    private String idioma;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataCadastro;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataAtualizacao;

    private Set<CategoriaGetResponse> categorias;
}
