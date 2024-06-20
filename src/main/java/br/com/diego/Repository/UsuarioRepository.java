package br.com.diego.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.diego.Models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

 
    
    @Query ("Select u from Usuario u where u.nome like %?1%")
	   public Usuario findByNome(String username);
}