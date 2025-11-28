package com.bookly.service;

import com.bookly.dto.LivroGetResponse;
import com.bookly.dto.LivroPostRequestBody;
import com.bookly.dto.LivroPutRequestBody;
import com.bookly.exception.BadRequestException;
import com.bookly.exception.ResourceNotFoundException;
import com.bookly.mapper.LivroMapper;
import com.bookly.model.Livro;
import com.bookly.model.StatusLivro;
import com.bookly.repository.LivroRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final LivroMapper livroMapper;
    private final UsuarioService usuarioService;
    private final CategoriaService categoriaService;

    public Livro salvarLivro(Livro livro) {
        return livroRepository.save(livro);
    }

    public Page<LivroGetResponse> buscarTodosLivrosDisponiveis(Pageable pageable) {
        Page<Livro> livros = livroRepository.findByStatusLivro(StatusLivro.DISPONIVEL, pageable);
        return livros.map(livroMapper::toResponse);
    }

    public LivroGetResponse buscarLivroDisponivelPorIdParaResposta(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro com ID:" + id + " Não Encontrado."));

        if (livro.getStatusLivro() != StatusLivro.DISPONIVEL) {
            throw new ResourceNotFoundException("Livro não está disponível para doação: " + id);
        }

        return livroMapper.toResponse(livro);
    }

    public Page<LivroGetResponse> listarLivrosDisponiveisPorCategoria(Long categoriaId, Pageable pageable) {
        Page<Livro> livros = livroRepository.findByCategoriaIdAndDisponivel(categoriaId, pageable);
        return livros.map(livroMapper::toResponse);
    }

    public Page<LivroGetResponse> listarTodosOsLivrosDoUsuario(Long usuarioId, Pageable pageable) {
        usuarioService.buscarUsuarioPorIdOuLancarExcecaoDeSolicitacaoInvalida(usuarioId);
        return livroRepository.findByUsuarioId(usuarioId, pageable)
                .map(livroMapper::toResponse);
    }

    public Page<LivroGetResponse> buscarLivrosDisponiveisPorTitulo(String titulo, Pageable pageable) {
        return livroRepository.findByTituloContainingIgnoreCaseAndStatusLivro(titulo, StatusLivro.DISPONIVEL, pageable)
                .map(livroMapper::toResponse);
    }

    public Livro buscarLivroPorIdOuLancarExcecaoDeSolicitacaoInvalida(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Livro com ID:" + id + " Não Encontrado."));
    }

    @Transactional
    public LivroGetResponse criarNovoLivro(LivroPostRequestBody livroPostRequestBody) {
        Livro livro = livroMapper.toLivro(livroPostRequestBody);

        livro.setUsuario(usuarioService.buscarUsuarioPorIdOuLancarExcecaoDeSolicitacaoInvalida(livroPostRequestBody.getUsuarioId()));
        livro.setCategorias(categoriaService.buscarCategoriasPorIds(livroPostRequestBody.getCategoriaIds()));
        livro.setStatusLivro(StatusLivro.DISPONIVEL);

        Livro livroSalvo = livroRepository.save(livro);
        return livroMapper.toResponse(livroSalvo);
    }

    @Transactional
    public void atualizarLivro(Long id, LivroPutRequestBody livroPutRequestBody) {
        Livro livroSalvo = buscarLivroPorIdOuLancarExcecaoDeSolicitacaoInvalida(id);

        Livro livroAtualizado = livroMapper.toLivro(livroPutRequestBody);

        livroAtualizado.setId(livroSalvo.getId());
        livroAtualizado.setUsuario(livroSalvo.getUsuario());
        livroAtualizado.setCategorias(categoriaService.buscarCategoriasPorIds(livroPutRequestBody.getCategoriasId()));
        livroAtualizado.setDataCadastro(livroSalvo.getDataCadastro());

        livroRepository.save(livroAtualizado);
    }

    public void deletarLivro(Long id) {
        Livro livroParaDeletar = buscarLivroPorIdOuLancarExcecaoDeSolicitacaoInvalida(id);
        livroRepository.delete(livroParaDeletar);
    }
}
