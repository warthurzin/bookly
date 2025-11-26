package com.bookly.repository;

import com.bookly.model.Notificacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {

    Page<Notificacao> findByUsuarioIdOrderByDataCriacaoDesc(Long usuarioId, Pageable pageable);

    long countByUsuarioIdAndDataLeituraIsNull(Long usuarioId);

    Optional<Notificacao> findByIdAndUsuarioId(Long id, Long usuarioId);

    @Modifying
    @Query("UPDATE Notificacao n SET n.dataLeitura = CURRENT_TIMESTAMP WHERE n.usuario.id = :usuarioId AND n.dataLeitura IS NULL")
    void marcarTodasComoLidas(@Param("usuarioId") Long usuarioId);
}