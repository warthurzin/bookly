package com.bookly.controller;

import com.bookly.dto.LivroGetResponse;
import com.bookly.dto.LivroPostRequestBody;
import com.bookly.dto.LivroPutRequestBody;
import com.bookly.service.LivroService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;

    @GetMapping
    public ResponseEntity<Page<LivroGetResponse>> listar(@Parameter(hidden = true) Pageable pageable) {
        Page<LivroGetResponse> livros = livroService.buscarTodosLivros(pageable);
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroGetResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(livroService.buscarLivroPorIdOuLancarExcecaoDeSolicitacaoInvalidaParaResposta(id));
    }

    @GetMapping("/busca")
    public ResponseEntity<Page<LivroGetResponse>> buscarPorTitulo(@RequestParam String titulo, @Parameter(hidden = true)
    Pageable pageable) {

        Page<LivroGetResponse> livros = livroService.buscarLivroPorTitulo(titulo, pageable);
        return ResponseEntity.ok(livros);
    }

    @PostMapping
    public ResponseEntity<LivroGetResponse> salvar(@RequestBody @Valid LivroPostRequestBody livroPostRequestBody) {
        LivroGetResponse livroCriado = livroService.criarNovoLivro(livroPostRequestBody);
        return new  ResponseEntity<>(livroCriado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editar(@PathVariable Long id, @RequestBody @Valid LivroPutRequestBody livroPutRequestBody) {
        livroService.atualizarLivro(id, livroPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        livroService.deletarLivro(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
