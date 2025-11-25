package com.bookly.repository;

import com.bookly.model.Solicitacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {

    Page<Solicitacao> findByDoadorId(Long doadorId, Pageable pageable);

    Page<Solicitacao> findBySolicitanteId(Long solicitanteId, Pageable pageable);
}