package com.ibm.academia.apirest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ibm.academia.apirest.services.TarjetaDAO;

@Component
public class Comandos implements CommandLineRunner{

	@Autowired
	private TarjetaDAO tarjetaDAO;
	
	@Override
	public void run(String... args) throws Exception {

		//System.out.println(tarjetaDAO.buscarPorId(23));
		System.out.println(tarjetaDAO.buscarCreditoPorPasionYSalarioYEdad("shooping", 20000, 28).getCredito());
		//System.out.println(tarjetaDAO.findCreditoByPasion("Shooping"));

		//System.out.println(tarjetaDAO.buscarTodos().toString());
		
	}
}
