package com.ibm.academia.apirest.services;

import com.ibm.academia.apirest.entities.Cliente;
import com.ibm.academia.apirest.entities.Tarjeta;

public interface ClienteDAO extends GenericoDAO<Cliente>{
	

	public Iterable<Cliente> findClienteByNombreAndApellido(String nombre, String apellido);
	public Cliente actualizar(Cliente clienteEncontrado, Cliente cliente);
	public Cliente asignarTarjetaClienteManual(Cliente cliente, Tarjeta tarjeta);
	
}
