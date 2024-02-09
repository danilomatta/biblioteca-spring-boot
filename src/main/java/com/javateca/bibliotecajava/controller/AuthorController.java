package com.javateca.bibliotecajava.controller;

import com.javateca.bibliotecajava.dto.AuthorDTO;
import com.javateca.bibliotecajava.entityJPA.Author;
import com.javateca.bibliotecajava.repository.AuthorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("autor")
public class AuthorController {

    @Autowired
    private AuthorRepository repository;

    //CADASTRA UM NOVO AUTOR
    @PostMapping("cadastrarAutor")
    @Transactional
    public ResponseEntity<Author> saveAuthor(@RequestBody @Valid AuthorDTO authorDTO){
        Author author = new Author(authorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(author));
    }

    //LISTA TODOS OS AUTORES ATIVOS
    @GetMapping("listarTodosAutoresAtivos")
    public ResponseEntity<Object> getAllAuthorAtivos(){
        List<Author> list = repository.findByAtivo(true);
        if(list.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List author not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    //LISTA TODOS OS AUTORES INATIVOS
    @GetMapping("listarTodosAutoresInativos")
    public ResponseEntity<Object> getAllAuthorInativos(){
        List<Author> list = repository.findByAtivoNot(true);
        if(list.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A lista está vazia");
        }
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    //ATUALIZA AUTOR - MODO CORRETO
    @PutMapping("atualizar/{id}")
    @Transactional
    public ResponseEntity<Object> updateAuthor(@PathVariable(value = "id") Long id, @RequestBody @Valid AuthorDTO authorDTO){
        Optional<Author> optionalAuthor = repository.findById(id);
        if(optionalAuthor.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found");
        } else{
            if(!optionalAuthor.get().getAtivo()){
                return ResponseEntity.status(HttpStatus.OK).body("Author está inativo");
            }
        }
        Author author = optionalAuthor.get();
        BeanUtils.copyProperties(authorDTO, author);
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(author));
    }


    //ATIVA UM AUTOR POR ID
    @PutMapping("ativarAuthor/{id}")
    @Transactional
    public ResponseEntity<Object> ativarAuthor(@PathVariable (value = "id") Long id){
        Optional<Author> optionalAuthor = repository.findById(id);
        Author author = repository.getReferenceById(id);
        if(optionalAuthor.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author não encontrado");
        }
        //Author author = optionalAuthor.get();

        if (author.getAtivo()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Author já se encontra ativo");
        }
            author.ativar();
            return ResponseEntity.status(HttpStatus.OK).body(repository.save(author));
        }


    //DELETA DO BANCO DE DADOS UM AUTOR PELA ID
    @DeleteMapping("deletar/{id}")
    @Transactional
    public ResponseEntity<Object> deleteAuthor(@PathVariable(value = "id") Long id){
        Optional<Author> optionalAuthor = repository.findById(id);
        if(optionalAuthor.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found");
        }
        repository.delete(optionalAuthor.get());
        return ResponseEntity.status(HttpStatus.OK).body("Author deleted successfully");
    }

    //EXCLUSÃO LÓGICA DO AUTOR PELA ID
    @PutMapping("inativar/{id}")
    @Transactional
    public ResponseEntity<String> deleteLogic(@PathVariable (value = "id") Long id){
        Optional<Author> optionalAuthor = repository.findById(id);
        if(optionalAuthor.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found");
        }else{
            if(!optionalAuthor.get().getAtivo()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Author já está inativo");
            }
        }
        Author author = repository.getReferenceById(id);
        author.inativar();
        return ResponseEntity.status(HttpStatus.OK).body("Autor inativado com sucesso");
    }
}
