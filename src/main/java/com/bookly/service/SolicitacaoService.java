package com.bookly.service;

import com.bookly.dto.SolicitacaoActionRequestBody;
import com.bookly.dto.SolicitacaoGetResponse;
import com.bookly.dto.SolicitacaoPostRequestBody;
import com.bookly.exception.BadRequestException;
import com.bookly.mapper.SolicitacaoMapper;
import com.bookly.model.Livro;
import com.bookly.model.Solicitacao;
import com.bookly.model.StatusLivro;
import com.bookly.model.StatusSolicitacao;
import com.bookly.model.Usuario;
import com.bookly.repository.SolicitacaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class SolicitacaoService {

    private final SolicitacaoRepository solicitacaoRepository;
    private final SolicitacaoMapper solicitacaoMapper;
    private final LivroService livroService;
    private final UsuarioService usuarioService;
    private final HistoricoTransacaoService historicoTransacaoService;

    public Solicitacao buscarSolicitacaoPorIdOuLancarExcecao(Long id) {
        return solicitacaoRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Solicitação com ID:" + id + " Não Encontrada."));
    }

    public Page<SolicitacaoGetResponse> listarSolicitacoesFeitasPorUsuario(Long solicitanteId, Pageable pageable) {
        usuarioService.buscarUsuarioPorIdOuLancarExcecaoDeSolicitacaoInvalida(solicitanteId);
        return solicitacaoRepository.findBySolicitanteId(solicitanteId, pageable)
                .map(solicitacaoMapper::toResponse);
    }

    public Page<SolicitacaoGetResponse> listarSolicitacoesRecebidasPorUsuario(Long doadorId, Pageable pageable) {
        usuarioService.buscarUsuarioPorIdOuLancarExcecaoDeSolicitacaoInvalida(doadorId);
        return solicitacaoRepository.findByDoadorId(doadorId, pageable)
                .map(solicitacaoMapper::toResponse);
    }

    @Transactional
    public SolicitacaoGetResponse criarNovaSolicitacao(SolicitacaoPostRequestBody requestBody) {

        Livro livro = livroService.buscarLivroPorIdOuLancarExcecaoDeSolicitacaoInvalida(requestBody.getLivroId());
        Usuario solicitante = usuarioService.buscarUsuarioPorIdOuLancarExcecaoDeSolicitacaoInvalida(requestBody.getSolicitanteId());

        if (livro.getStatusLivro() != StatusLivro.DISPONIVEL) {
            throw new BadRequestException("O livro não está disponível para solicitação. Status atual: " + livro.getStatusLivro());
        }

        if (livro.getUsuario().getId().equals(solicitante.getId())) {
            throw new BadRequestException("Você não pode solicitar um livro que você possui.");
        }

        Solicitacao solicitacao = solicitacaoMapper.toSolicitacao(requestBody);

        solicitacao.setLivro(livro);
        solicitacao.setSolicitante(solicitante);

        solicitacao.setDoador(livro.getUsuario());

        solicitacao.setStatusSolicitacao(StatusSolicitacao.PENDENTE);

        Solicitacao solicitacaoSalva = solicitacaoRepository.save(solicitacao);

        livro.setStatusLivro(StatusLivro.RESERVADO);
        livroService.salvarLivro(livro);

        return solicitacaoMapper.toResponse(solicitacaoSalva);
    }

    @Transactional
    public SolicitacaoGetResponse atualizarStatusSolicitacao(Long solicitacaoId, SolicitacaoActionRequestBody requestBody) {
        Solicitacao solicitacao = buscarSolicitacaoPorIdOuLancarExcecao(solicitacaoId);
        StatusSolicitacao novoStatus = requestBody.getStatusSolicitacao();

        Set<StatusSolicitacao> statusFinais = Set.of(StatusSolicitacao.RECUSADA, StatusSolicitacao.CONCLUIDA, StatusSolicitacao.CANCELADA);

        if (statusFinais.contains(solicitacao.getStatusSolicitacao())) {
            throw new BadRequestException("Solicitação já finalizada. Status atual: " + solicitacao.getStatusSolicitacao());
        }

        Livro livro = solicitacao.getLivro();

        if (novoStatus == StatusSolicitacao.ACEITA) {
            if (solicitacao.getStatusSolicitacao() != StatusSolicitacao.PENDENTE) {
                throw new BadRequestException("Solicitação deve estar PENDENTE para ser ACEITA.");
            }
            solicitacao.setStatusSolicitacao(novoStatus);
            solicitacao.setObservacoes(requestBody.getObservacoes());

        } else if (novoStatus == StatusSolicitacao.RECUSADA || novoStatus == StatusSolicitacao.CANCELADA) {
            livro.setStatusLivro(StatusLivro.DISPONIVEL);
            livroService.salvarLivro(livro);
            solicitacao.setStatusSolicitacao(novoStatus);
            solicitacao.setObservacoes(requestBody.getObservacoes());

        } else if (novoStatus == StatusSolicitacao.CONCLUIDA) {

            if (solicitacao.getStatusSolicitacao() != StatusSolicitacao.ACEITA) {
                throw new BadRequestException("Solicitação deve estar ACEITA para ser CONCLUÍDA.");
            }
            livro.setStatusLivro(StatusLivro.DOADO);
            livroService.salvarLivro(livro);
            solicitacao.setStatusSolicitacao(novoStatus);
            solicitacao.setObservacoes(requestBody.getObservacoes());

            historicoTransacaoService.criarHistoricoAposConclusao(solicitacao);

        } else {
            throw new BadRequestException("Ação de status inválida para esta operação.");
        }

        Solicitacao solicitacaoAtualizada = solicitacaoRepository.save(solicitacao);
        return solicitacaoMapper.toResponse(solicitacaoAtualizada);
    }
}