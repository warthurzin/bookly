package com.bookly.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore
    private Usuario usuario;

    private String titulo;
    private String autor;
    private String isbn;
    private String editora;
    private Integer anoPublicacao;

    @Lob
    private String descricao;

    @Enumerated(EnumType.STRING)
    private CondicaoLivro condicaoLivro;

    @Enumerated(EnumType.STRING)
    private TipoDisponibilidade tipoDisponibilidade;

    @Enumerated(EnumType.STRING)
    private StatusLivro statusLivro;

    private String fotoCapa;
    private Integer numeroPaginas;
    private String idioma;

    @CreationTimestamp
    @Column(name = "data_cadastro", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataCadastro;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataAtualizacao;

}
