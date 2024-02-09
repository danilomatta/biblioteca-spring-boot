package com.javateca.bibliotecajava.entityJPA;

import com.javateca.bibliotecajava.dto.UsuarioDTO;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "usuario")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "ativo")
    private Boolean ativo;

    public Usuario(UsuarioDTO usuarioDTO){
    this.nome = usuarioDTO.nome();
    this.cpf = usuarioDTO.cpf();
    this.ativo = true;
    }

    public void ativar(){
        this.ativo = true;
    }

    public void inativar(){
        this.ativo = false;
    }




}
