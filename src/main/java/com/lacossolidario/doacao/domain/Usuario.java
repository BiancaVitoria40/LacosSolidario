package com.lacossolidario.doacao.domain;

import com.lacossolidario.doacao.app.dto.DadosAtualizacaoUsuario;
import com.lacossolidario.doacao.infra.model.DadosCadastroUsuario;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "tb_usuarios")
@Entity(name = "Usuario")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private Boolean ativo;

    public Usuario(DadosCadastroUsuario dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.senha = dados.senha();


    }

    public void atualizarDados(DadosAtualizacaoUsuario dados) {
        if(dados.nome() != null) {
            this.nome = dados.nome();
        }
        if(dados.email() != null) {
            this.email = dados.email();
        }
        if(dados.telefone() != null) {
            this.telefone = dados.telefone();
        }
        if(dados.senha() != null) {
            this.senha = dados.senha();
        }
//        if(dados.endereco() != null) {
//            this.endereco.atualizarEndereco(dados.endereco());
//        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public void desativar() {
        this.ativo = false;
    }
}
