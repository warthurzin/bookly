package com.bookly.dto;

import com.bookly.model.FormaRecebimento;
import com.bookly.model.StatusSolicitacao;
import com.bookly.model.TipoSolicitacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SolicitacaoGetResponse {

    public Long id;

    private LivroGetResponse livro;
    private UsuarioGetResponse solicitante;
    private UsuarioGetResponse doador;

    private TipoSolicitacao tipoSolicitacao;
    private FormaRecebimento formaRecebimento;
    private StatusSolicitacao statusSolicitacao;

    private String enderecoEntrega;
    private String mensagem;
    private String observacoes;

    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
}