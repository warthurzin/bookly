package com.bookly.repository;

import com.bookly.model.Livro;
import com.bookly.model.StatusLivro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface LivroRepository extends JpaRepository<Livro, Long> {

    Page<Livro> findByStatusLivro(StatusLivro statusLivro, Pageable pageable);

    Page<Livro> findByTituloContainingIgnoreCaseAndStatusLivro(String titulo, StatusLivro statusLivro, Pageable pageable);

    Page<Livro> findByUsuarioId(Long usuarioId, Pageable pageable);

    @Query("SELECT l FROM Livro l JOIN l.categorias c WHERE c.id = :categoriaId AND l.statusLivro = 'DISPONIVEL'")
    Page<Livro> findByCategoriaIdAndDisponivel(@Param("categoriaId") Long categoriaId, Pageable pageable);
}
