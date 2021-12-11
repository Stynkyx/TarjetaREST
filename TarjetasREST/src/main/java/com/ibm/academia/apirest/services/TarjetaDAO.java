package com.ibm.academia.apirest.services;

import com.ibm.academia.apirest.entities.Tarjeta;

public interface TarjetaDAO extends GenericoDAO<Tarjeta>{

	public Tarjeta buscarCreditoPorPasionYSalarioYEdad(String pasion, Integer salario, Integer edad);
	public Iterable<Tarjeta> findCreditoByPasion(String pasion);
	public Tarjeta actualizar(Tarjeta tarjetaEncontrada, Tarjeta tarjeta);
}
