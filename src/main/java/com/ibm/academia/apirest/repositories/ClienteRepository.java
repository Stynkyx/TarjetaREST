package com.ibm.academia.apirest.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ibm.academia.apirest.entities.Cliente;

@Repository("repositorioClientes")
public interface ClienteRepository extends CrudRepository<Cliente, Integer>{

	//@Query("select c from Cliente c where c.nombre like %?1% and c.apellido %?2%")
	public Iterable<Cliente> findClienteByNombreAndApellido(String nombre, String apellido);
}
