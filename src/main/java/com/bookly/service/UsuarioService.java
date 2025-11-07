package com.bookly.service;

import com.bookly.dto.UsuarioPostRequestBody;
import com.bookly.dto.UsuarioPutRequestBody;
import com.bookly.model.Usuario;
import com.bookly.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> buscarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuarioPorIdOuLancarExcecaoDeSolicitacaoInvalida(long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário Com ID: " + id + " Não Encontrado."));
    }

    public Usuario criarNovoUsuario(UsuarioPostRequestBody postRequest) {
        if (usuarioRepository.findByEmail(postRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("O e-mail " + postRequest.getEmail() + " já está em uso");
        }

        Usuario novoUsuario = Usuario.builder()
                .nome(postRequest.getNome())
                .email(postRequest.getEmail())
                .senha(postRequest.getSenha())
                .cidade(postRequest.getCidade())
                .estado(postRequest.getEstado())
                .tipoUsuario(postRequest.getTipoUsuario())
                .status(postRequest.getStatus())
                .fotoPerfil(postRequest.getFotoPerfil())
                .biografia(postRequest.getBiografia())
                .build();

        return usuarioRepository.save(novoUsuario);
    }

    public Usuario atualizarUsuario(Long id, UsuarioPutRequestBody putRequest) {
        Usuario usuarioExistente = buscarUsuarioPorIdOuLancarExcecaoDeSolicitacaoInvalida(id);

        Usuario usuarioAtualizado = Usuario.builder()
                .id(usuarioExistente.getId())
                .nome(putRequest.getNome())
                .email(putRequest.getEmail())
                .cidade(putRequest.getCidade())
                .estado(putRequest.getEstado())
                .tipoUsuario(putRequest.getTipoUsuario())
                .status(putRequest.getStatus())
                .fotoPerfil(putRequest.getFotoPerfil())
                .biografia(putRequest.getBiografia())
                .senha(usuarioExistente.getSenha())
                .dataCadastro(usuarioExistente.getDataCadastro())
                .build();

        return usuarioRepository.save(usuarioAtualizado);
    }

    public void deletarUsuario(Long id) {
        Usuario usuario = buscarUsuarioPorIdOuLancarExcecaoDeSolicitacaoInvalida(id);
        usuarioRepository.delete(usuario);
    }
}
