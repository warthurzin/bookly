package com.bookly.service;

import com.bookly.dto.UsuarioPostRequestBody;
import com.bookly.dto.UsuarioPutRequestBody;
import com.bookly.mapper.UsuarioMapper;
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
    private final UsuarioMapper usuarioMapper;

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
        return usuarioRepository.save(usuarioMapper.toUsuario(postRequest));
    }

    public Usuario atualizarUsuario(Long id, UsuarioPutRequestBody putRequest) {
        Usuario usuarioExistente = buscarUsuarioPorIdOuLancarExcecaoDeSolicitacaoInvalida(id);

        Usuario usuarioAtualizado = usuarioMapper.toUsuario(putRequest);
        usuarioAtualizado.setId(usuarioExistente.getId());
        usuarioAtualizado.setSenha(usuarioExistente.getSenha());
        usuarioAtualizado.setDataCadastro(usuarioExistente.getDataCadastro());
        return usuarioRepository.save(usuarioAtualizado);
    }

    public void deletarUsuario(Long id) {
        Usuario usuario = buscarUsuarioPorIdOuLancarExcecaoDeSolicitacaoInvalida(id);
        usuarioRepository.delete(usuario);
    }
}
