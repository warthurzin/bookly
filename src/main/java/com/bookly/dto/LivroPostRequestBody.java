package com.bookly.dto;

import com.bookly.model.CondicaoLivro;
import com.bookly.model.TipoDisponibilidade;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LivroPostRequestBody {

    @NotNull(message = "O ID do usuário proprietário é obrigatório.")
    @Min(value = 1, message = "O ID do usuário deve ser válido.")
    private Long usuarioId;

    @NotBlank(message = "O título é obrigatório.")
    private String titulo;

    @NotBlank(message = "O nome do autor é obrigatório.")
    private String autor;

    @NotBlank(message = "O ISBN é obrigatório.")
    private String isbn;

    @NotBlank(message = "A editora é obrigatória.")
    private String editora;

    @NotNull(message = "O ano de publicação é obrigatório.")
    private Integer anoPublicacao;

    @NotBlank(message = "A descrição é obrigatória.")
    private String descricao;

    @NotNull(message = "A condição do livro é obrigatória.")
    private CondicaoLivro condicaoLivro;

    @NotNull(message = "O tipo de disponibilidade é obrigatório.")
    private TipoDisponibilidade tipoDisponibilidade;

    private String fotoCapa;
    private Integer numeroPaginas;
    private String idioma;

    private Set<Long> categoriaIds;
}
