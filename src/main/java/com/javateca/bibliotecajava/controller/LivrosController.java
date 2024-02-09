package com.javateca.bibliotecajava.controller;

import com.javateca.bibliotecajava.dto.LivrosDTO;
import com.javateca.bibliotecajava.entityJPA.Author;
import com.javateca.bibliotecajava.entityJPA.Livros;
import com.javateca.bibliotecajava.repository.LivrosRepository;
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
@RequestMapping("livros")
public class LivrosController {

    @Autowired
    private LivrosRepository repository;

    @PostMapping("cadastrar")
    @Transactional
    public ResponseEntity<Livros> cadastrar(@RequestBody @Valid LivrosDTO livrosDTO){
        Livros livros = new Livros(livrosDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(livros));
    }

    @GetMapping("listarTodosLivros")
    public ResponseEntity<Object> listarTodosLivros(){
        List<Livros> listaLivros = repository.findAll();
        if(listaLivros.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A lista está vazia");
        }
        return ResponseEntity.status(HttpStatus.OK).body(listaLivros);
    }

    @GetMapping("listarLivrosAtivos")
    public ResponseEntity<Object> listarLivrosAtivos(){
        List<Livros> listaLivros = repository.findByAtivo(true);
        if(listaLivros.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A lista está vazia");
        }
        return ResponseEntity.status(HttpStatus.OK).body(listaLivros);
    }

    @GetMapping("listarLivrosInativos")
    public ResponseEntity<Object> listarLivrosInativos(){
        List<Livros> listarLivros = repository.findByAtivoNot(true);
        if(listarLivros.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A lista está vazia");
        }
        return ResponseEntity.status(HttpStatus.OK).body(listarLivros);
    }

    @PutMapping("atualizar/{id}")
    @Transactional
    public ResponseEntity<Object> atualizar(@PathVariable(value = "id") Long id, @RequestBody @Valid LivrosDTO livrosDTO){
        Optional<Livros> optionalLivros = repository.findById(id);
        if(optionalLivros.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado");
        }
        if(!optionalLivros.get().getAtivo()){
            return ResponseEntity.status(HttpStatus.OK).body("O livro está inativo");
        }
        Livros livros = optionalLivros.get();
        BeanUtils.copyProperties(livrosDTO, livros);
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(livros));

    }

    @PutMapping("ativarLivro/{id}")
    @Transactional
    public ResponseEntity<Object> ativarLivro(@PathVariable(value = "id") Long id){
        Optional<Livros> optionalLivros = repository.findById(id);
        Livros livros = repository.getReferenceById(id);
        if(optionalLivros.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado");
        }
        if(livros.getAtivo()){
            return ResponseEntity.status(HttpStatus.OK).body("Livro já se encontra ativo");
        }
        livros.ativar();
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(livros));
    }

    @DeleteMapping("inativar/{id}")
    @Transactional
    public ResponseEntity<String> deleteLogic(@PathVariable (value = "id") Long id){
        Optional<Livros> optionalLivros = repository.findById(id);
        if(optionalLivros.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro not found");
        }else{
            if(!optionalLivros.get().getAtivo()){
                return ResponseEntity.status(HttpStatus.OK).body("Livro já está inativo");
            }
        }
        Livros livros = repository.getReferenceById(id);
        livros.inativar();
        repository.saveAndFlush(livros);
        return ResponseEntity.status(HttpStatus.OK).body("Livro inativado com sucesso");
    }

    @DeleteMapping("deletar/{id}")
    @Transactional
    public ResponseEntity<String> deletarLivro(@PathVariable(value = "id") Long id){
        Optional<Livros> livrosOptional = repository.findById(id);
        if(livrosOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado");
        }
        repository.delete(livrosOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Livro excluído com sucesso");
    }
}
