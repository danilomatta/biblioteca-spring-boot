package com.javateca.bibliotecajava.entityJPA;

import com.javateca.bibliotecajava.dto.LivrosDTO;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "livros")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Livros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "isbn")
    private Long isbn;
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "ativo")
    private Boolean ativo;

    public Livros(LivrosDTO livros){
        this.ativo = true;
        this.titulo = livros.titulo();
        this.isbn = livros.isbn();
        this.descricao = livros.descricao();
    }

    public void ativar(){
        this.ativo = true;
    }

    public void inativar(){
        this.ativo = false;
    }

}
