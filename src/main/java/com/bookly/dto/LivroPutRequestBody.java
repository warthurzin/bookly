package com.bookly.dto;

import com.bookly.model.CondicaoLivro;
import com.bookly.model.TipoDisponibilidade;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class LivroPutRequestBody {

    private Long usuarioId;
    private String titulo;
    private String autor;
    private String isbn;
    private String editora;
    private Integer anoPublicacao;
    private String descricao;
    private CondicaoLivro  condicaoLivro;
    private TipoDisponibilidade tipoDisponibilidade;

    private String fotoCapa;
    private Integer numeroPaginas;
    private String idioma;

    private Set<Long> categoriaIds;
}
