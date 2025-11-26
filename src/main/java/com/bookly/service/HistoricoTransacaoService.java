package com.bookly.service;

import com.bookly.dto.HistoricoTransacaoGetResponse;
import com.bookly.mapper.HistoricoTransacaoMapper;
import com.bookly.model.HistoricoTransacao;
import com.bookly.model.Solicitacao;
import com.bookly.model.TipoTransacao;
import com.bookly.repository.HistoricoTransacaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoricoTransacaoService {

    private final HistoricoTransacaoRepository historicoRepository;
    private final HistoricoTransacaoMapper historicoMapper;
    private final UsuarioService usuarioService;

    @Transactional
    public HistoricoTransacao criarHistoricoAposConclusao(Solicitacao solicitacao) {

        HistoricoTransacao historico = HistoricoTransacao.builder()
                .solicitacao(solicitacao)
                .livro(solicitacao.getLivro())
                .doador(solicitacao.getDoador())
                .solicitante(solicitacao.getSolicitante())
                .tipoTransacao(TipoTransacao.DOACAO)
                .observacoes(solicitacao.getObservacoes())
                .build();

        return historicoRepository.save(historico);
    }

    public Page<HistoricoTransacaoGetResponse> listarHistoricoComoDoador(Long usuarioId, Pageable pageable) {
        usuarioService.buscarUsuarioPorIdOuLancarExcecaoDeSolicitacaoInvalida(usuarioId);
        return historicoRepository.findByDoadorId(usuarioId, pageable)
                .map(historicoMapper::toResponse);
    }

    public Page<HistoricoTransacaoGetResponse> listarHistoricoComoRecebedor(Long usuarioId, Pageable pageable) {
        usuarioService.buscarUsuarioPorIdOuLancarExcecaoDeSolicitacaoInvalida(usuarioId);
        return historicoRepository.findBySolicitanteId(usuarioId, pageable)
                .map(historicoMapper::toResponse);
    }
}