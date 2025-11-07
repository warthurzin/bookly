package com.bookly.controller;

import com.bookly.dto.UsuarioPostRequestBody;
import com.bookly.dto.UsuarioPutRequestBody;
import com.bookly.model.Usuario;
import com.bookly.service.UsuarioService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<Usuario>> listar() {
        List<Usuario> usuarios = usuarioService.buscarTodosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorIdOuLancarExcecaoDeSolicitacaoInvalida(id));
    }

    @PostMapping
    public ResponseEntity<Usuario> salvar(@RequestBody UsuarioPostRequestBody usuarioPostRequestBody) {
        return new ResponseEntity<>(usuarioService.criarNovoUsuario(usuarioPostRequestBody), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> editar(@PathVariable Long id, @RequestBody UsuarioPutRequestBody usuarioPutRequestBody) {
        usuarioService.atualizarUsuario(id, usuarioPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
