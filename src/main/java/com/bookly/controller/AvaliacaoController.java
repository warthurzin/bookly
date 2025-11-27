package com.bookly.controller;

import com.bookly.dto.AvaliacaoGetResponse;
import com.bookly.dto.AvaliacaoPostRequestBody;
import com.bookly.service.AvaliacaoService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/avaliacoes")
@RequiredArgsConstructor
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    @PostMapping
    @Transactional
    public ResponseEntity<AvaliacaoGetResponse> criarAvaliacao(@RequestBody @Valid AvaliacaoPostRequestBody requestBody) {
        AvaliacaoGetResponse response = avaliacaoService.criarAvaliacao(requestBody);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/recebidas/{avaliadoId}")
    public ResponseEntity<Page<AvaliacaoGetResponse>> listarAvaliacoesRecebidas(
            @PathVariable Long avaliadoId,
            @Parameter(hidden = true) Pageable pageable) {

        Page<AvaliacaoGetResponse> avaliacoes = avaliacaoService.listarAvaliacoesRecebidas(avaliadoId, pageable);
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/media/{usuarioId}")
    public ResponseEntity<Double> buscarMediaAvaliacoes(@PathVariable Long usuarioId) {
        Double media = avaliacaoService.buscarMediaAvaliacoes(usuarioId);
        return ResponseEntity.ok(media);
    }
}   