package com.javateca.bibliotecajava.entityJPA;

import com.javateca.bibliotecajava.dto.AuthorDTO;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "author")
@Entity(name = "author")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;

    @Column(name = "biography")
    private String biography;

    @Column(name = "ativo")
    private Boolean ativo;

    public Author(AuthorDTO author){
        this.name = author.name();
        this.biography = author.biography();
        this.ativo = true;
    }

    public void inativar() {
        this.ativo = false;
    }

    public void ativar(){
        this.ativo = true;
    }
}
