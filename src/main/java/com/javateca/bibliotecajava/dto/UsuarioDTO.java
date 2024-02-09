package com.javateca.bibliotecajava.dto;

import com.javateca.bibliotecajava.entityJPA.Usuario;
import jakarta.validation.constraints.NotBlank;

public record UsuarioDTO(@NotBlank String nome, @NotBlank String cpf) {

    public UsuarioDTO(Usuario usuario){
        this(usuario.getNome(), usuario.getCpf());
    }
}
