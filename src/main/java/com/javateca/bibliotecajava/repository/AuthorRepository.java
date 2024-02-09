package com.javateca.bibliotecajava.repository;

import com.javateca.bibliotecajava.entityJPA.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByAtivo(boolean ativo);

    List<Author> findByAtivoNot(boolean inativo);
}
