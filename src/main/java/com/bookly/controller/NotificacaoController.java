package com.bookly.controller;

import com.bookly.dto.NotificacaoGetResponse;
import com.bookly.service.NotificacaoService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notificacoes")
@RequiredArgsConstructor
public class NotificacaoController {

    private final NotificacaoService notificacaoService;

    @GetMapping("/{usuarioId}")
    public ResponseEntity<Page<NotificacaoGetResponse>> listarNotificacoes(
            @PathVariable Long usuarioId,
            @Parameter(hidden = true) Pageable pageable) {

        Page<NotificacaoGetResponse> notificacoes = notificacaoService.listarNotificacoesPorUsuario(usuarioId, pageable);
        return ResponseEntity.ok(notificacoes);
    }

    @Transactional
    @PutMapping("/{notificacaoId}/usuario/{usuarioId}/lida")
    public ResponseEntity<NotificacaoGetResponse> marcarComoLida(
            @PathVariable Long notificacaoId,
            @PathVariable Long usuarioId) {

        NotificacaoGetResponse response = notificacaoService.marcarComoLida(notificacaoId, usuarioId);
        return ResponseEntity.ok(response);
    }

    @Transactional
    @PutMapping("/usuario/{usuarioId}/marcar-todas-lidas")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void marcarTodasComoLidas(@PathVariable Long usuarioId) {
        notificacaoService.marcarTodasComoLidas(usuarioId);
    }

    @GetMapping("/usuario/{usuarioId}/nao-lidas/contagem")
    public ResponseEntity<Long> contarNaoLidas(@PathVariable Long usuarioId) {
        long count = notificacaoService.contarNaoLidas(usuarioId);
        return ResponseEntity.ok(count);
    }
}