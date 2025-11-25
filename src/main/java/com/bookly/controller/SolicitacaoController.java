package com.bookly.controller;

import com.bookly.dto.SolicitacaoActionRequestBody;
import com.bookly.dto.SolicitacaoGetResponse;
import com.bookly.dto.SolicitacaoPostRequestBody;
import com.bookly.service.SolicitacaoService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/solicitacoes")
@RequiredArgsConstructor
public class SolicitacaoController {

    private final SolicitacaoService solicitacaoService;

    @PostMapping
    public ResponseEntity<SolicitacaoGetResponse> criarNovaSolicitacao(
            @RequestBody @Valid SolicitacaoPostRequestBody requestBody) {

        SolicitacaoGetResponse response = solicitacaoService.criarNovaSolicitacao(requestBody);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<SolicitacaoGetResponse> atualizarStatusSolicitacao(
            @PathVariable Long id,
            @RequestBody @Valid SolicitacaoActionRequestBody requestBody) {

        SolicitacaoGetResponse response = solicitacaoService.atualizarStatusSolicitacao(id, requestBody);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/solicitante/{solicitanteId}")
    public ResponseEntity<Page<SolicitacaoGetResponse>> listarSolicitacoesFeitasPorUsuario(
            @PathVariable Long solicitanteId,
            @Parameter(hidden = true) Pageable pageable) {

        Page<SolicitacaoGetResponse> solicitacoes = solicitacaoService.listarSolicitacoesFeitasPorUsuario(solicitanteId, pageable);
        return new ResponseEntity<>(solicitacoes, HttpStatus.OK);
    }

    @GetMapping("/doador/{doadorId}")
    public ResponseEntity<Page<SolicitacaoGetResponse>> listarSolicitacoesRecebidasPorUsuario(
            @PathVariable Long doadorId,
            @Parameter(hidden = true) Pageable pageable) {

        Page<SolicitacaoGetResponse> solicitacoes = solicitacaoService.listarSolicitacoesRecebidasPorUsuario(doadorId, pageable);
        return new ResponseEntity<>(solicitacoes, HttpStatus.OK);
    }
}