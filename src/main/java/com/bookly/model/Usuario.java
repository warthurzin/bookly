package com.bookly.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;

    private String cidade;
    private String estado;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    private String fotoPerfil;

    @Lob
    private String biografia;

    @Enumerated(EnumType.STRING)
    private StatusUsuario status;

    @CreationTimestamp
    @Column(name = "data_cadastro", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataCadastro;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataAtualizacao;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario",
                fetch = FetchType.LAZY,
                cascade = CascadeType.ALL)
    private List<Livro> livros;
}
