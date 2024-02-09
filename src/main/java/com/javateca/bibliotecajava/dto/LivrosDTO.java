package com.javateca.bibliotecajava.dto;

import com.javateca.bibliotecajava.entityJPA.Livros;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LivrosDTO(@NotBlank String titulo, @NotNull Long isbn, @NotBlank String descricao) {

    public LivrosDTO(Livros livros){
        this(livros.getTitulo(), livros.getIsbn(), livros.getDescricao());

    }
}
