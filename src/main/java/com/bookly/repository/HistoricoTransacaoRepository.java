package com.bookly.repository;

import com.bookly.model.HistoricoTransacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoTransacaoRepository extends JpaRepository<HistoricoTransacao, Long> {

    Page<HistoricoTransacao> findByDoadorId(Long doadorId, Pageable pageable);

    Page<HistoricoTransacao> findBySolicitanteId(Long solicitanteId, Pageable pageable);

    HistoricoTransacao findBySolicitacaoId(Long solicitacaoId);
}