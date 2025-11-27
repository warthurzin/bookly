package com.bookly.service;

import com.bookly.dto.AvaliacaoGetResponse;
import com.bookly.dto.AvaliacaoPostRequestBody;
import com.bookly.exception.BadRequestException;
import com.bookly.mapper.AvaliacaoMapper;
import com.bookly.model.Avaliacao;
import com.bookly.model.HistoricoTransacao;
import com.bookly.model.TipoAvaliacao;
import com.bookly.model.Usuario;
import com.bookly.repository.AvaliacaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final AvaliacaoMapper avaliacaoMapper;
    private final UsuarioService usuarioService;
    private final HistoricoTransacaoService historicoTransacaoService;

    @Transactional
    public AvaliacaoGetResponse criarAvaliacao(AvaliacaoPostRequestBody requestBody) {

        Usuario avaliador = usuarioService.buscarUsuarioPorIdOuLancarExcecaoDeSolicitacaoInvalida(requestBody.getAvaliadorId());
        HistoricoTransacao historico = historicoTransacaoService.buscarHistoricoPorIdOuLancarExcecao(requestBody.getHistoricoId());

        Usuario avaliado;
        TipoAvaliacao tipo;

        if (avaliador.getId().equals(historico.getDoador().getId())) {
            avaliado = historico.getSolicitante();
            tipo = TipoAvaliacao.AVALIACAO_RECEBEDOR;

        } else if (avaliador.getId().equals(historico.getSolicitante().getId())) {
            avaliado = historico.getDoador();
            tipo = TipoAvaliacao.AVALIACAO_DOADOR;

        } else {
            throw new BadRequestException("O usuário avaliador não participou desta transação.");
        }

        Optional<Avaliacao> avaliacaoExistente = avaliacaoRepository.findByHistoricoIdAndAvaliadorId(
                historico.getId(), avaliador.getId()
        );
        if (avaliacaoExistente.isPresent()) {
            throw new BadRequestException("Você já avaliou esta transação.");
        }

        Avaliacao novaAvaliacao = Avaliacao.builder()
                .historico(historico)
                .avaliador(avaliador)
                .avaliado(avaliado)
                .nota(requestBody.getNota())
                .comentario(requestBody.getComentario())
                .tipoAvaliacao(tipo)
                .build();

        Avaliacao avaliacaoSalva = avaliacaoRepository.save(novaAvaliacao);

        return avaliacaoMapper.toResponse(avaliacaoSalva);
    }

    public Page<AvaliacaoGetResponse> listarAvaliacoesRecebidas(Long avaliadoId, Pageable pageable) {
        usuarioService.buscarUsuarioPorIdOuLancarExcecaoDeSolicitacaoInvalida(avaliadoId);

        return avaliacaoRepository.findByAvaliadoId(avaliadoId, pageable)
                .map(avaliacaoMapper::toResponse);
    }

    public Double buscarMediaAvaliacoes(Long usuarioId) {
        usuarioService.buscarUsuarioPorIdOuLancarExcecaoDeSolicitacaoInvalida(usuarioId);
        Double media = avaliacaoRepository.calcularMediaAvaliacoes(usuarioId);

        return media != null ? media : 0.0;
    }
}