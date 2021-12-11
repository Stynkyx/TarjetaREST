package com.ibm.academia.apirest.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ibm.academia.apirest.datos.DatosDummy;
import com.ibm.academia.apirest.entities.Tarjeta;

@DataJpaTest
public class TarjetaRepositoryTest {

	@Autowired
	private TarjetaRepository tarjetaRepositorio;
	
	@BeforeEach
	void setUp()	{
		//Given
		Iterable<Tarjeta> personas = tarjetaRepositorio.saveAll(
                Arrays.asList(
                		DatosDummy.tarjeta01(),
                		DatosDummy.tarjeta02(),
                		DatosDummy.tarjeta03(),
                		DatosDummy.tarjeta04(),
                		DatosDummy.tarjeta05(),
                		DatosDummy.tarjeta06(),
                		DatosDummy.tarjeta07(),
                		DatosDummy.tarjeta08(),
                		DatosDummy.tarjeta09(),
                		DatosDummy.tarjeta10(),
                		DatosDummy.tarjeta11(),
                		DatosDummy.tarjeta12(),
                		DatosDummy.tarjeta13(),
                		DatosDummy.tarjeta14(),
                		DatosDummy.tarjeta15(),
                		DatosDummy.tarjeta16(),
                		DatosDummy.tarjeta17(),
                		DatosDummy.tarjeta18(),
                		DatosDummy.tarjeta19(),
                		DatosDummy.tarjeta20(),
                		DatosDummy.tarjeta21(),
                		DatosDummy.tarjeta22(),
                		DatosDummy.tarjeta23(),
                		DatosDummy.tarjeta24(),
                		DatosDummy.tarjeta25(),
                		DatosDummy.tarjeta26(),
                		DatosDummy.tarjeta27(),
                		DatosDummy.tarjeta28(),
                		DatosDummy.tarjeta29(),
                		DatosDummy.tarjeta30(),
                		DatosDummy.tarjeta31()
                		)
        );

	}
	
	@AfterEach
	void tearDown()	{
		tarjetaRepositorio.deleteAll();
	}
	
	@Test
	@DisplayName("Test: Busca Tarjeta")
	void buscarCreditoPorPasionAndSalarioAndEdad	(){
		
		//When
		String pasion = "Travels";
		Integer salario =12500;
		Integer edad = 21;
		Tarjeta expected = tarjetaRepositorio.buscarCreditoPorPasionYSalarioYEdad(pasion, salario, edad);
		
		//Then
		assertThat(expected).isEqualTo(DatosDummy.tarjeta11());
	}
	
}
