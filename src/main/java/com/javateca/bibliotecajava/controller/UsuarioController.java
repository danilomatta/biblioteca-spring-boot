package com.javateca.bibliotecajava.controller;

import com.javateca.bibliotecajava.dto.UsuarioDTO;
import com.javateca.bibliotecajava.entityJPA.Author;
import com.javateca.bibliotecajava.entityJPA.Usuario;
import com.javateca.bibliotecajava.repository.UsuarioRepository;
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
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @PostMapping("cadastrar")
    @Transactional
    public ResponseEntity<Usuario> cadastrar(@RequestBody @Valid UsuarioDTO usuarioDTO){
        Usuario usuario = new Usuario(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(usuario));
    }

    @GetMapping("listarTodos")
    public ResponseEntity<Object> listarTodos(){
        List<Usuario> list = repository.findAll();
        if(list.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A lista está vazia");
        }
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("listarTodosAtivos")
        public ResponseEntity<Object> listarTodosAtivos(){
            List<Usuario> usuarioList = repository.findByAtivo(true);
            if(usuarioList.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A lista está fazia");
            }
            return ResponseEntity.status(HttpStatus.OK).body(usuarioList);
        }
    @GetMapping("listarTodosInativos")
        public ResponseEntity<Object> listarTodosInativos(){
        List<Usuario> usuarioList = repository.findByAtivoNot(true);
        if(usuarioList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("A lista está fazia");
        }
        return ResponseEntity.status(HttpStatus.OK).body(usuarioList);
    }

    @GetMapping("listarPorId/{id}")
    public ResponseEntity<Object> listarPorId(@PathVariable(value = "id") Long id){
        Optional<Usuario> usuarioOptional = repository.findById(id);
        Usuario usuario = repository.getReferenceById(id);
        if(usuarioOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("usuário não encontrado");
        }
        if(!usuario.getAtivo()){
            return ResponseEntity.status(HttpStatus.OK).body("Usuário está inativo");
        }
        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }


    @PutMapping("atualizar/{id}")
    @Transactional
    public ResponseEntity<Object> atualizar(@PathVariable(value = "id") Long id, @RequestBody @Valid UsuarioDTO usuarioDTO){
        Optional<Usuario> usuarioOptional = repository.findById(id);
        if(usuarioOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("usuário não encontrado");
        }
        if(!usuarioOptional.get().getAtivo()){
            return ResponseEntity.status(HttpStatus.OK).body("Usuário está inativo");
        }
        Usuario usuario = usuarioOptional.get();
        BeanUtils.copyProperties(usuarioDTO, usuario);
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(usuario));
    }

    @PutMapping("ativar/{id}")
    @Transactional
    public ResponseEntity<Object> ativar(@PathVariable(value = "id") Long id){
        Optional<Usuario> optionalUsuario = repository.findById(id);

        if(optionalUsuario.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author não encontrado");
        }
        Usuario usuario = optionalUsuario.get();

        if (usuario.getAtivo()){
            return ResponseEntity.status(HttpStatus.OK).body("Author já se encontra ativo");
        }
        usuario.ativar();
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(usuario));
    }

    @PutMapping("inativar/{id}")
    @Transactional
    public ResponseEntity<Object> inativar(@PathVariable(value = "id") Long id){
        Optional<Usuario> usuarioOptional = repository.findById(id);
        if(usuarioOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author não encontrado");
        }
        if(!usuarioOptional.get().getAtivo()){
            return ResponseEntity.status(HttpStatus.OK).body("Author já se encontra inativo");
        }
        Usuario usuario = usuarioOptional.get();
        usuario.inativar();
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(usuario));
    }

    @DeleteMapping("deletar/{id}")
    @Transactional
    public ResponseEntity<Object> deleteAuthor(@PathVariable(value = "id") Long id){
        Optional<Usuario> optionalUsuario = repository.findById(id);
        if(optionalUsuario.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario not found");
        }
        repository.delete(optionalUsuario.get());
        return ResponseEntity.status(HttpStatus.OK).body("Usuario deleted successfully");
    }


}
