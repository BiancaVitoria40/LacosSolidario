package com.lacossolidario.doacao.app.resource;

import com.lacossolidario.doacao.app.dto.DadosAtualizacaoUsuario;
import com.lacossolidario.doacao.app.dto.DadosListagemUsuario;
import com.lacossolidario.doacao.domain.Doador;
import com.lacossolidario.doacao.domain.Instituicao;
import com.lacossolidario.doacao.infra.model.DadosCadastroUsuario;
import com.lacossolidario.doacao.domain.Usuario;
import com.lacossolidario.doacao.infra.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioResource {

    @Autowired
    private UsuarioRepository repository;

    @PostMapping
    @RequestMapping("/cadastro/{tipoDeUsuario}")
    @Transactional
    public ResponseEntity cadastrarUsuario(@RequestBody @Valid DadosCadastroUsuario dados, @PathVariable String tipoDeUsuario,UriComponentsBuilder uriBuilder){
        Usuario usuario = null;

        if (!tipoDeUsuario.equalsIgnoreCase("instituicao") && !tipoDeUsuario.equalsIgnoreCase("doador")) {
            return ResponseEntity.badRequest().body("Tipo de usuário inválido");
        }

        if (tipoDeUsuario.equalsIgnoreCase("instituicao")) {
            if (dados.getCnpj() == null || dados.getCnpj().isEmpty()) {
                return ResponseEntity.badRequest().body("CNPJ não fornecido");
            }
            if (dados.getCpf() != null && !dados.getCpf().isEmpty()) {
                return ResponseEntity.badRequest().body("Não é possível cadastrar um CPF para uma instituição");
            }
            if (repository.existsInstituicaoByCnpj(dados.getCnpj())) {
                return ResponseEntity.badRequest().body("Já existe uma instituição cadastrada com este CNPJ");
            }
            usuario = new Instituicao(dados, dados.getCnpj());
        } else if (tipoDeUsuario.equalsIgnoreCase("doador")) {
            if (dados.getCpf() == null || dados.getCpf().isEmpty()) {
                return ResponseEntity.badRequest().body("CPF não fornecido");
            }
            if (dados.getCnpj() != null && !dados.getCnpj().isEmpty()) {
                return ResponseEntity.badRequest().body("Não é possível cadastrar um CNPJ para um doador");
            }
            if (repository.existsDoadorByCpf(dados.getCpf())) {
                return ResponseEntity.badRequest().body("Já existe um doador cadastrado com este CPF");
            }
            usuario = new Doador(dados, dados.getCpf());
        }

        if (!usuario.getTipoDeUsuario().equalsIgnoreCase(tipoDeUsuario)) {
            return ResponseEntity.badRequest().body("Tipo de usuário inconsistente com os dados fornecidos");
        }

        repository.save(usuario);

        var uri = uriBuilder.path("/usuario/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosListagemUsuario(usuario));
    }



    @GetMapping("/listar/{tipoDeUsuario}")
    public ResponseEntity<List<DadosListagemUsuario>> listarUsuario(@PathVariable String tipoDeUsuario){
        var list = repository.findAllByAtivoTrueAndTipoDeUsuario(tipoDeUsuario).stream()
                .map(DadosListagemUsuario::new)
                .toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalharPorId(@PathVariable Long id){
        var usuario  = repository.getReferenceById(id);

        return ResponseEntity.ok(new DadosListagemUsuario(usuario));

    }


    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoUsuario dados){
        var usuario = repository.getReferenceById(dados.id());
        usuario.atualizarDados(dados);

        return ResponseEntity.ok(new DadosListagemUsuario(usuario));
        
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity desativarUsuario(@PathVariable Long id){
        var usuario = repository.getReferenceById(id);
        usuario.desativar();

        return ResponseEntity.noContent().build();


    }
}
