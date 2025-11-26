package com.bookly.controller;

import com.bookly.dto.HistoricoTransacaoGetResponse;
import com.bookly.service.HistoricoTransacaoService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/historico")
@RequiredArgsConstructor
public class HistoricoTransacaoController {

    private final HistoricoTransacaoService historicoService;

    @GetMapping("/doador/{usuarioId}")
    public ResponseEntity<Page<HistoricoTransacaoGetResponse>> listarHistoricoComoDoador(
            @PathVariable Long usuarioId,
            @Parameter(hidden = true) Pageable pageable) {

        Page<HistoricoTransacaoGetResponse> historico = historicoService.listarHistoricoComoDoador(usuarioId, pageable);
        return ResponseEntity.ok(historico);
    }

    @GetMapping("/recebedor/{usuarioId}")
    public ResponseEntity<Page<HistoricoTransacaoGetResponse>> listarHistoricoComoRecebedor(
            @PathVariable Long usuarioId,
            @Parameter(hidden = true) Pageable pageable) {

        Page<HistoricoTransacaoGetResponse> historico = historicoService.listarHistoricoComoRecebedor(usuarioId, pageable);
        return ResponseEntity.ok(historico);
    }
}