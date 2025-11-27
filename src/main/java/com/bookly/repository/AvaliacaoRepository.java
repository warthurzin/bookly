package com.bookly.repository;

import com.bookly.model.Avaliacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    Page<Avaliacao> findByAvaliadoId(Long avaliadoId, Pageable pageable);

    Optional<Avaliacao> findByHistoricoIdAndAvaliadorId(Long historicoId, Long avaliadorId);

    @Query("SELECT AVG(a.nota) FROM Avaliacao a WHERE a.avaliado.id = :avaliadoId")
    Double calcularMediaAvaliacoes(@Param("avaliadoId") Long avaliadoId);
}