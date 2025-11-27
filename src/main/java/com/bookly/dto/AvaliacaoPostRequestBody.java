package com.bookly.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvaliacaoPostRequestBody {

    @NotNull(message = "O ID do Histórico da Transação é obrigatório.")
    private Long historicoId;

    @NotNull(message = "O ID do Avaliador é obrigatório.")
    private Long avaliadorId;

    @NotNull(message = "A nota é obrigatória.")
    @Min(value = 1, message = "A nota mínima é 1.")
    @Max(value = 5, message = "A nota máxima é 5.")
    private Integer nota;

    private String comentario;
}