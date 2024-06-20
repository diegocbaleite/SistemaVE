package br.com.diego.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.diego.Models.Funcionario;

public interface FuncionarioRepository extends CrudRepository<Funcionario, Long> {

	Funcionario findById(long id);

	// BUSCA
	Funcionario findByNome(String nome);

	// PARA A BUSCA
	@Query(value = "select u from Funcionario u where u.nome like %?1%")
	List<Funcionario> findByNomes(String nome);

}
