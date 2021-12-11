package com.ibm.academia.apirest.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.academia.apirest.entities.Cliente;
import com.ibm.academia.apirest.entities.Tarjeta;
import com.ibm.academia.apirest.repositories.ClienteRepository;

@Service
public class ClienteDAOImpl extends GenericoDAOImpl<Cliente, ClienteRepository> implements ClienteDAO{

	@Autowired
	public ClienteDAOImpl(ClienteRepository repository) {
		super(repository);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Cliente> findClienteByNombreAndApellido(String nombre, String apellido) {
		return repository.findClienteByNombreAndApellido(nombre, apellido);
	}

	@Override
	@Transactional
	public Cliente actualizar(Cliente clienteEncontrado, Cliente cliente) {
		
		Cliente clienteActualizado = null;
		clienteEncontrado.setEdad(cliente.getEdad());
		clienteEncontrado.setNombre(cliente.getNombre());
		clienteEncontrado.setApellido(cliente.getApellido());
		clienteEncontrado.setDni(cliente.getDni());
		clienteActualizado = repository.save(clienteEncontrado);
		return clienteActualizado;
	}
	
	@Override
	@Transactional
	public Cliente asignarTarjetaClienteManual(Cliente cliente, Tarjeta tarjeta) 
	{
		cliente.setTarjetas((Set<Tarjeta>) tarjeta);
		return repository.save(cliente);
	}
}
