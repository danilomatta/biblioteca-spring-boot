package com.javateca.bibliotecajava.repository;

import com.javateca.bibliotecajava.entityJPA.Livros;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivrosRepository extends JpaRepository<Livros, Long> {

    List<Livros> findByAtivo(boolean ativo);

    List<Livros> findByAtivoNot(boolean ativo);
}
