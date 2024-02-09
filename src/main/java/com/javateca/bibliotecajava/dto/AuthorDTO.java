package com.javateca.bibliotecajava.dto;

import com.javateca.bibliotecajava.entityJPA.Author;
import jakarta.validation.constraints.NotBlank;

public record AuthorDTO(@NotBlank String name, @NotBlank String biography, Boolean ativo) {

    public AuthorDTO(Author author){
        this(author.getName(), author.getBiography(), author.getAtivo());
    }
}
