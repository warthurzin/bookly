package com.bookly.service;

import com.bookly.model.Usuario;
import com.bookly.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> buscarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarUsuarioPorId(long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario criarNovoUsuario(Usuario novoUsuario) {
        if (usuarioRepository.findByEmail(novoUsuario.getEmail()).isPresent()) {
            throw new IllegalArgumentException("O e-mail " + novoUsuario.getEmail() + " já está em uso.");
        }

        return usuarioRepository.save(novoUsuario);
    }

    public Usuario atualizarUsuario(Long id, Usuario dadosUsuario) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com ID: " + id + " não encontrado"));

        usuarioExistente.setNome(dadosUsuario.getNome());
        usuarioExistente.setEmail(dadosUsuario.getEmail());
        usuarioExistente.setCidade(dadosUsuario.getCidade());
        usuarioExistente.setEstado(dadosUsuario.getEstado());
        usuarioExistente.setTipoUsuario(dadosUsuario.getTipoUsuario());
        usuarioExistente.setFotoPerfil(dadosUsuario.getFotoPerfil());
        usuarioExistente.setBiografia(dadosUsuario.getBiografia());
        usuarioExistente.setStatus(dadosUsuario.getStatus());

        return usuarioRepository.save(usuarioExistente);
    }

    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado para exclusão com ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}
