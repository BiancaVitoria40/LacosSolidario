package com.lacossolidario.doacao.app.dto;

import com.lacossolidario.doacao.domain.Usuario;

public record DadosListagemUsuario(
         Long id,
         String nome,
         String email,
         String senha,
         String telefone

) {

    public DadosListagemUsuario(Usuario usuario){
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getSenha(),
                usuario.getTelefone());


    }
}
