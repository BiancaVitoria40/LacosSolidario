package com.lacossolidario.doacao.infra.repository;

import com.lacossolidario.doacao.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

      Collection<Usuario> findAllByAtivoTrue();

}
