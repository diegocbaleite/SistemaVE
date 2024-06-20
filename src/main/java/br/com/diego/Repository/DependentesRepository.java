package br.com.diego.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.diego.Models.Dependentes;
import br.com.diego.Models.Funcionario;

public interface DependentesRepository extends CrudRepository<Dependentes, Long> {

	Iterable<Dependentes> findByFuncionario(Funcionario funcionario);

	// PENSANDO NO MÉTODO DELETE
	Dependentes findByCpf(String cpf);

	Dependentes findById(long id);

	// CRIADO PARA IMPLEMENTAR
	List<Dependentes> findByNome(String nome);

	// PARA A BUSCA
	@Query(value = "select u from Dependentes u where u.nome like %?1%")
	List<Dependentes> findByNomesDependentes(String nome);

}
