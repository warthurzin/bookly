package com.bookly.service;

import com.bookly.dto.NotificacaoGetResponse;
import com.bookly.exception.BadRequestException;
import com.bookly.mapper.NotificacaoMapper;
import com.bookly.model.Notificacao;
import com.bookly.model.Solicitacao;
import com.bookly.model.TipoNotificacao;
import com.bookly.repository.NotificacaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final NotificacaoMapper notificacaoMapper;
    private final UsuarioService usuarioService;

    @Transactional
    public void criarNotificacaoSolicitacao(Solicitacao solicitacao) {
        String mensagem = String.format(
                "O livro '%s' foi solicitado por %s. Por favor, aceite ou recuse o pedido.",
                solicitacao.getLivro().getTitulo(),
                solicitacao.getSolicitante().getNome()
        );

        Notificacao notificacao = Notificacao.builder()
                .usuario(solicitacao.getDoador())
                .solicitacao(solicitacao)
                .tipoNotificacao(TipoNotificacao.SOLICITACAO)
                .mensagem(mensagem)
                .build();

        notificacaoRepository.save(notificacao);
    }

    @Transactional
    public void criarNotificacaoAceiteRecusa(Solicitacao solicitacao, TipoNotificacao tipo) {
        String statusText = tipo == TipoNotificacao.ACEITE ? "ACEITO" : "RECUSADO";
        String mensagem = String.format(
                "Seu pedido para o livro '%s' foi %s pelo doador %s.",
                solicitacao.getLivro().getTitulo(),
                statusText,
                solicitacao.getDoador().getNome()
        );

        Notificacao notificacao = Notificacao.builder()
                .usuario(solicitacao.getSolicitante())
                .solicitacao(solicitacao)
                .tipoNotificacao(tipo)
                .mensagem(mensagem)
                .build();

        notificacaoRepository.save(notificacao);
    }

    public Page<NotificacaoGetResponse> listarNotificacoesPorUsuario(Long usuarioId, Pageable pageable) {
        usuarioService.buscarUsuarioPorIdOuLancarExcecaoDeSolicitacaoInvalida(usuarioId);

        return notificacaoRepository.findByUsuarioIdOrderByDataCriacaoDesc(usuarioId, pageable)
                .map(notificacaoMapper::toResponse);
    }

    @Transactional
    public NotificacaoGetResponse marcarComoLida(Long notificacaoId, Long usuarioId) {
        Notificacao notificacao = notificacaoRepository.findByIdAndUsuarioId(notificacaoId, usuarioId)
                .orElseThrow(() -> new BadRequestException("Notificação Não Encontrada ou acesso negado."));

        if (notificacao.getDataLeitura() == null) {
            notificacao.setDataLeitura(LocalDateTime.now());
            notificacao = notificacaoRepository.save(notificacao);
        }

        return notificacaoMapper.toResponse(notificacao);
    }

    @Transactional
    public void marcarTodasComoLidas(Long usuarioId) {
        usuarioService.buscarUsuarioPorIdOuLancarExcecaoDeSolicitacaoInvalida(usuarioId);
        notificacaoRepository.marcarTodasComoLidas(usuarioId);
    }

    public long contarNaoLidas(Long usuarioId) {
        usuarioService.buscarUsuarioPorIdOuLancarExcecaoDeSolicitacaoInvalida(usuarioId);
        return notificacaoRepository.countByUsuarioIdAndDataLeituraIsNull(usuarioId);
    }
}