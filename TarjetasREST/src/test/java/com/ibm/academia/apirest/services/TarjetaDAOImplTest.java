package com.ibm.academia.apirest.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ibm.academia.apirest.datos.DatosDummy;
import com.ibm.academia.apirest.entities.Tarjeta;
import com.ibm.academia.apirest.repositories.TarjetaRepository;

public class TarjetaDAOImplTest {

	TarjetaDAO tarjetaDAO;
	TarjetaRepository tarjetaRepositorio;
	
	@BeforeEach
    void setUp() 
    {
        tarjetaRepositorio = mock(TarjetaRepository.class);
        tarjetaDAO = new TarjetaDAOImpl(tarjetaRepositorio);
    }
	@Test
	@DisplayName("Test: Busca Tarjeta")
	void buscarCreditoPorPasionAndSalarioAndEdad	(){
			//Given
			String pasion = "Help";
			Integer salario =12500;
			Integer edad = 21;
			when(tarjetaRepositorio.buscarCreditoPorPasionYSalarioYEdad(pasion, salario, edad)).
					thenReturn((DatosDummy.tarjeta13()));
			//When
			Tarjeta expected = tarjetaDAO.buscarCreditoPorPasionYSalarioYEdad(pasion, salario, edad);
			 
			//Then
			assertThat(expected.getCredito()).isEqualTo(DatosDummy.tarjeta13().getCredito());
			verify(tarjetaRepositorio).buscarCreditoPorPasionYSalarioYEdad(pasion, salario, edad);
			System.out.println(expected.getCredito());
			System.out.println(DatosDummy.tarjeta13().getCredito());
	}
	
}
