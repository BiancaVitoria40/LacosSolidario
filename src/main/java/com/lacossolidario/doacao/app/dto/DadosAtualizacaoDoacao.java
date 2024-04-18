package com.lacossolidario.doacao.app.dto;

import com.lacossolidario.doacao.domain.Endereco;

public record DadosAtualizacaoDoacao (
        Long id,
        String categoria,
        String descricao,
        Endereco endereco
){
}
