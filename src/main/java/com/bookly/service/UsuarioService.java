package com.bookly.service;

import com.bookly.dto.UsuarioGetResponse;
import com.bookly.dto.UsuarioPostRequestBody;
import com.bookly.dto.UsuarioPutRequestBody;
import com.bookly.exception.BadRequestException;
import com.bookly.mapper.UsuarioMapper;
import com.bookly.model.StatusUsuario;
import com.bookly.model.TipoUsuario;
import com.bookly.model.Usuario;
import com.bookly.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public Page<UsuarioGetResponse> buscarTodosUsuarios(Pageable pageable) {
        return usuarioRepository.findAll(pageable)
                .map(usuarioMapper::toResponse);
    }

    public Page<UsuarioGetResponse> buscarUsuarioPorNome(String nome, Pageable pageable) {
        return usuarioRepository.findByNome(nome, pageable)
                .map(usuarioMapper::toResponse);
    }

    public Usuario buscarUsuarioPorIdOuLancarExcecaoDeSolicitacaoInvalida(long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Usuário Com ID: " + id + " Não Encontrado."));
    }

    public UsuarioGetResponse buscarUsuarioPorIdOuLancarExcecaoDeSolicitacaoInvalidaParaResposta(long id) {
        Usuario usuario = buscarUsuarioPorIdOuLancarExcecaoDeSolicitacaoInvalida(id);
        return usuarioMapper.toResponse(usuario);
    }

    @Transactional
    public UsuarioGetResponse criarNovoUsuario(UsuarioPostRequestBody postRequest) {
        if (usuarioRepository.findByEmail(postRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("O e-mail " + postRequest.getEmail() + " já está em uso");
        }

        Usuario novoUsuario = usuarioMapper.toUsuario(postRequest);

        novoUsuario.setTipoUsuario(TipoUsuario.USUARIO);
        novoUsuario.setStatus(StatusUsuario.ATIVO);

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);
        return usuarioMapper.toResponse(usuarioSalvo);
    }

    public void atualizarUsuario(Long id, UsuarioPutRequestBody putRequest) {
        Usuario usuarioExistente = buscarUsuarioPorIdOuLancarExcecaoDeSolicitacaoInvalida(id);

        Usuario usuarioAtualizado = usuarioMapper.toUsuario(putRequest);

        usuarioAtualizado.setId(usuarioExistente.getId());
        usuarioAtualizado.setSenha(usuarioExistente.getSenha());
        usuarioAtualizado.setDataCadastro(usuarioExistente.getDataCadastro());
        usuarioAtualizado.setTipoUsuario(usuarioExistente.getTipoUsuario());
        usuarioAtualizado.setStatus(usuarioExistente.getStatus());

        usuarioRepository.save(usuarioAtualizado);
    }

    public void deletarUsuario(Long id) {
        Usuario usuario = buscarUsuarioPorIdOuLancarExcecaoDeSolicitacaoInvalida(id);
        usuarioRepository.delete(usuario);
    }
}
