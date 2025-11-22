package com.bookly.controller;

import com.bookly.dto.UsuarioGetResponse;
import com.bookly.dto.UsuarioPostRequestBody;
import com.bookly.dto.UsuarioPutRequestBody;
import com.bookly.service.UsuarioService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Page<UsuarioGetResponse>> listar(@Parameter(hidden = true) Pageable pageable) {
        Page<UsuarioGetResponse> usuarios = usuarioService.buscarTodosUsuarios(pageable);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioGetResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorIdOuLancarExcecaoDeSolicitacaoInvalidaParaResposta(id));
    }

    @GetMapping("/busca")
    public ResponseEntity<Page<UsuarioGetResponse>> buscarPorNome(@RequestParam String nome, @Parameter(hidden = true) Pageable pageable) {
        Page<UsuarioGetResponse> usuarios = usuarioService.buscarUsuarioPorNome(nome, pageable);
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<UsuarioGetResponse> salvar(@RequestBody @Valid UsuarioPostRequestBody usuarioPostRequestBody) {
        return new ResponseEntity<>(usuarioService.criarNovoUsuario(usuarioPostRequestBody), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editar(@PathVariable Long id, @RequestBody UsuarioPutRequestBody usuarioPutRequestBody) {
        usuarioService.atualizarUsuario(id, usuarioPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
