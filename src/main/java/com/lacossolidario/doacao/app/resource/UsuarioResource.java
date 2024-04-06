package com.lacossolidario.doacao.app.resource;

import com.lacossolidario.doacao.app.dto.DadosAtualizacaoUsuario;
import com.lacossolidario.doacao.app.dto.DadosListagemUsuario;
import com.lacossolidario.doacao.infra.model.DadosCadastroUsuario;
import com.lacossolidario.doacao.domain.Usuario;
import com.lacossolidario.doacao.infra.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    @RequestMapping("/cadastro")
    @Transactional
    public ResponseEntity cadastrarUsuario(@RequestBody @Valid DadosCadastroUsuario dados, UriComponentsBuilder uriBuilder){
        var usuario = new Usuario(dados);
        repository.save(usuario);

        var uri = uriBuilder.path("/usuario/{id}").buildAndExpand(usuario.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosListagemUsuario(usuario));
    }


    @GetMapping("/listar")
    public ResponseEntity<List<DadosListagemUsuario>> listarUsuario(){
        var list = repository.findAllByAtivoTrue().stream()
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
