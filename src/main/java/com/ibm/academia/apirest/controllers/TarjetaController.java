package com.ibm.academia.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.academia.apirest.entities.Tarjeta;
import com.ibm.academia.apirest.exceptions.BadRequestException;
import com.ibm.academia.apirest.exceptions.NotFoundException;
import com.ibm.academia.apirest.services.TarjetaDAO;

@RestController
@RequestMapping("/tarjeta")
public class TarjetaController {
	
	@Autowired
	private TarjetaDAO tarjetaDao;
	
	/**
	 * Endpoint para crear un objeto tareta
	 * @param tarjeta Objeto tarjeta con la informacion para crear
	 * @param result En caso de que un campo no se haya llenado correctamente
	 * @return Retorna el objeto tarjeta creado junto con código httpstatus 201
	 * @author SACM - 10-12-21
	 */
	@PostMapping
	public ResponseEntity<?> guardarTarjeta(@Valid @RequestBody Tarjeta tarjeta, BindingResult result)
	{
		Map<String, Object> validaciones = new HashMap<String, Object>();
		if(result.hasErrors())
		{
			List<String> listaErrores = result.getFieldErrors()
					.stream()
					.map(errores -> "Campo: '" + errores.getField() + "' " + errores.getDefaultMessage())
					.collect(Collectors.toList());
			validaciones.put("Lista Errores", listaErrores);
			return new ResponseEntity<Map<String, Object>>(validaciones, HttpStatus.BAD_REQUEST);
		}
		
		Tarjeta tarjetaGuardada = tarjetaDao.guardar(tarjeta);
		
		return new ResponseEntity<Tarjeta>(tarjetaGuardada, HttpStatus.CREATED);
	}
			
	/**
	 * EndPoint para buscar objeto tarjeta 
	 * @param pasion Parametro a buscar en objeto tarjeta
	 * @param salario Parametro a buscar en objeto tarjeta
	 * @param edad Parametro a buscar en objeto tarjeta
	 * @return Objeto tarjeta en la base de datos junto con código httpstatus 200 
	 */
	
	@GetMapping("/buscar-credito")
	public ResponseEntity<?> buscarCreditoPorPasionYSalarioYEdad(@RequestParam String pasion,@RequestParam Integer salario,@RequestParam Integer edad) {
		
		Tarjeta tarjeta = tarjetaDao.buscarCreditoPorPasionYSalarioYEdad(pasion, salario, edad);
		
		if(tarjeta==null)
			throw new BadRequestException("El credito con los parametros introducidos no existe");
		
		return new ResponseEntity<Tarjeta>(tarjeta, HttpStatus.OK);
	}
	/**
	 * EndPoint para mostrar una lista de objetos tarjeta
	 * @param BadRequestException En caso de que no se encuentre ningun elemento en la base de datos 
	 * @return Una lista de tarjetas
	 * @author SACM - 09-12-21
	 */
	@GetMapping("/lista")
	public ResponseEntity<?> buscarTodas()
	{
		List<Tarjeta> tarjetas = (List<Tarjeta>) tarjetaDao.buscarTodos();
		if(tarjetas.isEmpty())
			throw new BadRequestException("No existen tarjetas");
		
		return new ResponseEntity<List<Tarjeta>>(tarjetas,HttpStatus.OK);
	}
	/**
	 * EndPoint para buscar objeto tarjeta por parametro pasion
	 * @param pasion Parametro a buscar
	 * @return lista de tarjetas
	 * @author SACM - 09-12-21
	 */
	@GetMapping("/credito/pasion")
	public ResponseEntity<?> buscarPasion(@RequestParam String pasion)
	{
		List<Tarjeta> tarjetas = (List<Tarjeta>) tarjetaDao.findCreditoByPasion(pasion);
		if(tarjetas.isEmpty())
			throw new BadRequestException("No existen tarjetas con pasion");
		
		return new ResponseEntity<List<Tarjeta>>(tarjetas,HttpStatus.OK);
	}
	
	/**
	 * EndPoint para actualizar un objeto de tipo tarjeta
	 * @param tarjetaId Parametro para buscar tarjeta
	 * @param tarjeta Objeto con la informacion a actualizar
	 * @return Retorna un objeto de tipo tarjeta con la información actualizada junto con código httpstatus 200 
	 * @author SACM - 10-12-21
	 */
	@PutMapping("/upd/tarjetaId/{tarjetaId}")
	public ResponseEntity<?> actualizarTarjeta(@PathVariable Integer tarjetaId, @RequestBody Tarjeta tarjeta)
	{
		Optional<Tarjeta> oTarjeta = tarjetaDao.buscarPorId(tarjetaId);
		
		if(!oTarjeta.isPresent())
			throw new NotFoundException(String.format("La tarjeta con ID: %d no existe", tarjetaId));
		
		Tarjeta tarjetaActualizada = tarjetaDao.actualizar(oTarjeta.get(), tarjeta); 
		
		return new ResponseEntity<Tarjeta>(tarjetaActualizada, HttpStatus.OK); 
	}
	
	/**
	 * EndPoint para eliminar un objeto de tipo tarjeta
	 * @param tarjetaId Parametro para objeto a eliminar
	 * @return Un mensaje con el id que se elimino junto con código httpstatus 202
	 * @author SACM - 10-12-21  
	 */
	@DeleteMapping("/del/tarjetaId/{tarjetaId}")
	public ResponseEntity<?> eliminarTarjeta(@PathVariable Integer tarjetaId)
	{
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
		Optional<Tarjeta> tarjeta = tarjetaDao.buscarPorId(tarjetaId);
		
		if(!tarjeta.isPresent())
			throw new NotFoundException(String.format("La tarjeta con ID: %d no existe", tarjetaId));
		
		tarjetaDao.eliminarPorId(tarjetaId);
		respuesta.put("OK", "Tarjeta ID: " + tarjetaId + " eliminado exitosamente");
		return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.ACCEPTED);
	}
}
