package com.lacossolidario.doacao.infra.repository;

import com.lacossolidario.doacao.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

      @Query("SELECT u FROM Usuario u WHERE u.ativo = true AND u.tipoDeUsuario = :tipoDeUsuario")
      List<Usuario> findAllByAtivoTrueAndTipoDeUsuario(@Param("tipoDeUsuario") String tipoDeUsuario);

      @Query("SELECT u FROM Usuario u WHERE u.id = :id AND u.tipoDeUsuario = :tipoDeUsuario")
      Usuario getReferenceByIdAndTipoDeUsuario(@Param("id") Long id, @Param("tipoDeUsuario") String tipoDeUsuario);

      @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM Doador d WHERE d.cpf = :cpf")
      boolean existsDoadorByCpf(String cpf);

      @Query("SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END FROM Instituicao i WHERE i.cnpj = :cnpj")
      boolean existsInstituicaoByCnpj(String cnpj);


}
