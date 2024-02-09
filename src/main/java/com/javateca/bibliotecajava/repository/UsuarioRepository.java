package com.javateca.bibliotecajava.repository;

import com.javateca.bibliotecajava.entityJPA.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByAtivo(boolean ativo);
    List<Usuario> findByAtivoNot(boolean ativo);
}
