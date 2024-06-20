package br.com.diego.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.diego.Models.Motorista;
import br.com.diego.Models.Veiculo;

public interface MotoristaRepository extends CrudRepository<Motorista, Long> {

	Iterable<Motorista> findByVeiculo(Veiculo veiculo);

	Motorista findBycnh(String cnh);

	Motorista findById(long id);

	// List<Motorista> findByNomeMotorista(String nomeMotorista);

	// PARA A BUSCA
	@Query(value = "select u from Veiculo u where u.nome like %?1%")
	List<Motorista> findByNomesMotoristas(String nomeMotorista);

}
