package com.bookly.repository;

import com.bookly.model.Livro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    Page<Livro> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);

    List<Livro> findByUsuarioId(Long usuarioId);
}
