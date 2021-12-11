package com.ibm.academia.apirest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.academia.apirest.entities.Tarjeta;
import com.ibm.academia.apirest.repositories.TarjetaRepository;

@Service
public class TarjetaDAOImpl extends GenericoDAOImpl<Tarjeta, TarjetaRepository> implements TarjetaDAO{
	
	@Autowired
	public TarjetaDAOImpl(TarjetaRepository repository) {
		super(repository);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Tarjeta buscarCreditoPorPasionYSalarioYEdad(String pasion, Integer salario, Integer edad) {
		return repository.buscarCreditoPorPasionYSalarioYEdad(pasion, salario, edad);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Tarjeta> findCreditoByPasion(String pasion) {
		return repository.findCreditoByPasion(pasion);
	}
	
	@Override
	@Transactional
	public Tarjeta actualizar(Tarjeta tarjetaEncontrada, Tarjeta tarjeta) {
		
		Tarjeta tarjetaActualizada = null;
		
		tarjetaEncontrada.setCredito(tarjeta.getCredito());
		tarjetaEncontrada.setEdad_minima(tarjeta.getEdad_minima());
		tarjetaEncontrada.setEdad_maxima(tarjeta.getEdad_maxima());
		tarjetaEncontrada.setSalario_minimo(tarjeta.getSalario_minimo());
		tarjetaEncontrada.setSalario_maximo(tarjeta.getSalario_maximo());
		tarjetaEncontrada.setPasion(tarjeta.getPasion());
		
		tarjetaActualizada = repository.save(tarjetaEncontrada);
		return tarjetaActualizada;
	}
}
