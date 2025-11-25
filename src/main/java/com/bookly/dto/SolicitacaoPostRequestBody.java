package com.bookly.dto;

import com.bookly.model.FormaRecebimento;
import com.bookly.model.TipoSolicitacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SolicitacaoPostRequestBody {

    @NotNull(message = "O ID do Livro é obrigatório.")
    private Long livroId;

    @NotNull(message = "O ID do solicitante é obrigatório.")
    private Long solicitanteId;

    @NotNull(message = "O tipo de solicitação é obrigatório.")
    private TipoSolicitacao tipoSolicitacao = TipoSolicitacao.DOACAO;

    @NotNull(message = "A forma de recebimento é obrigatória")
    private FormaRecebimento formaRecebimento;

    private String enderecoEntrega;

    @NotBlank(message = "A mensagem para o doador é obrigatória.")
    private String mensagem;

    private String observacoes;
}