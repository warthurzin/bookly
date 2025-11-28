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
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;

    @GetMapping("/livros")
    public ResponseEntity<Page<LivroGetResponse>> listar(@Parameter(hidden = true) Pageable pageable) {
        Page<LivroGetResponse> livros = livroService.buscarTodosLivros(pageable);
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<Page<LivroGetResponse>> listarLivrosPorCategoria
            (@PathVariable Long categoriaId,
             @Parameter(hidden = true) Pageable pageable) {
        Page<LivroGetResponse> livros = livroService.listarLivrosPorCategoria(categoriaId, pageable);
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/usuarios/{usuarioId}/livros")
    public ResponseEntity<Page<LivroGetResponse>> listarLivrosDoUsuario(
            @PathVariable Long usuarioId,
            @Parameter(hidden = true) Pageable pageable) {

        Page<LivroGetResponse> livros = livroService.listarTodosOsLivrosDoUsuario(usuarioId, pageable);
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/livros/{id}")
    public ResponseEntity<LivroGetResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(livroService.buscarLivroPorIdOuLancarExcecaoDeSolicitacaoInvalidaParaResposta(id));
    }

    @GetMapping("/livros/busca")
    public ResponseEntity<Page<LivroGetResponse>> buscarPorTitulo(@RequestParam String titulo, @Parameter(hidden = true)
    Pageable pageable) {

        Page<LivroGetResponse> livros = livroService.buscarLivroPorTitulo(titulo, pageable);
        return ResponseEntity.ok(livros);
    }

    @PostMapping("/livros")
    public ResponseEntity<LivroGetResponse> salvar(@RequestBody @Valid LivroPostRequestBody livroPostRequestBody) {
        LivroGetResponse livroCriado = livroService.criarNovoLivro(livroPostRequestBody);
        return new  ResponseEntity<>(livroCriado, HttpStatus.CREATED);
    }

    @PutMapping("/livros/{id}")
    public ResponseEntity<Void> editar(@PathVariable Long id, @RequestBody @Valid LivroPutRequestBody livroPutRequestBody) {
        livroService.atualizarLivro(id, livroPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/livros/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        livroService.deletarLivro(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
