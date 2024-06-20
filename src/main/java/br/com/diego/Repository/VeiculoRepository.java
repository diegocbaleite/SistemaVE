package br.com.diego.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.diego.Models.Veiculo;

public interface VeiculoRepository extends CrudRepository<Veiculo, Long> {

	Veiculo findByCodigo(long codigo);


	// PARA A BUSCA
	@Query(value = "select u from Veiculo u where u.nome like %?1%")
	List<Veiculo> findByNomesVeiculos(String nome);
}
