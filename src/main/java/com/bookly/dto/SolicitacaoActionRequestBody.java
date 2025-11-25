package com.bookly.dto;

import com.bookly.model.StatusSolicitacao;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SolicitacaoActionRequestBody {

    @NotNull(message = "O novo status da solicitação é obrigatório.")
    private StatusSolicitacao statusSolicitacao;

    private String observacoes;
}