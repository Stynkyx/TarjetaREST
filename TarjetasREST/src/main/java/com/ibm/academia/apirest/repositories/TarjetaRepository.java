package com.ibm.academia.apirest.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ibm.academia.apirest.entities.Tarjeta;

@Repository("repositorioTarjetas")
public interface TarjetaRepository extends CrudRepository<Tarjeta, Integer>{

	@Query(value = "select t from Tarjeta t where t.pasion like %?1% "
			+ "AND ?2 between t.salario_minimo and t.salario_maximo "
			+ "AND ?3 between t.edad_minima and t.edad_maxima")
	public Tarjeta buscarCreditoPorPasionYSalarioYEdad(String pasion, Integer salario, Integer edad);
	
	@Query("select t from Tarjeta t where t.pasion like %?1% ")
	public Iterable<Tarjeta> findCreditoByPasion(String pasion);
}
