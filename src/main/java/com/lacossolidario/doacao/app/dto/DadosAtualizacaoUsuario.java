package com.lacossolidario.doacao.app.dto;

import com.lacossolidario.doacao.infra.model.DadosEndereco;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
public record DadosAtualizacaoUsuario(
        @NotNull
        Long id,
        String nome,
        @Email
        String email,
        String senha,
        String telefone

) {
}
