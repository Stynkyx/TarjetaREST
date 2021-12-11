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

import com.ibm.academia.apirest.entities.Cliente;
import com.ibm.academia.apirest.entities.Tarjeta;
import com.ibm.academia.apirest.exceptions.BadRequestException;
import com.ibm.academia.apirest.exceptions.NotFoundException;
import com.ibm.academia.apirest.services.ClienteDAO;
import com.ibm.academia.apirest.services.TarjetaDAO;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteDAO clienteDao;
	
	@Autowired
	private TarjetaDAO tarjetaDao;
	/**
	 * Endpoint para crear un objeto Cliente
	 * @param cliente Objeto Cliente con la informacion para crear
	 * @param result En caso de que un campo no se haya llenado correctamente
	 * @return Retorna el objeto Cliente creado junto con código httpstatus 201
	 * @author SACM - 09-12-21
	 */
	@PostMapping
	public ResponseEntity<?> guardarCliente(@Valid @RequestBody Cliente cliente, BindingResult result)
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
		
		Cliente clienteGuardado = clienteDao.guardar(cliente);
		
		return new ResponseEntity<Cliente>(clienteGuardado, HttpStatus.CREATED);
	}
	/**
	 * Endpoint para buscar un Cliente por nombre y apellido
	 * @param nombre Parametro para buscar junto con apellido
	 * @param clientes En caso de no encontrarse en la base de datos
	 * @return Sera el Cliente encontrado en la base de datos junto con código httpstatus 200 
	 * @author SACM - 09-12-21
	 */
	@GetMapping("/nombre-apellido")
	public ResponseEntity<?> buscarClientePorNombreYApellido(@RequestParam String nombre,@RequestParam String apellido)
	{
		List<Cliente> clientes = (List<Cliente>) clienteDao.findClienteByNombreAndApellido(nombre, apellido);
		if(clientes.isEmpty())
			throw new BadRequestException("No existen clientes");
		
		return new ResponseEntity<List<Cliente>>(clientes,HttpStatus.OK);
	}
	/**
	 * EndPoint para mostrar una lista de objetos cliente
	 * @param BadRequestException En caso de que no se encuentre ningun elemento en la base de datos 
	 * @return 	Una lista de clientes
	 * @author SACM - 09-12-21
	 */
	@GetMapping("/lista")
	public ResponseEntity<?> buscarTodosClientes()
	{
		List<Cliente> clientes = (List<Cliente>) clienteDao.buscarTodos();
		if(clientes.isEmpty())
			throw new BadRequestException("No existen clientes");
		
		return new ResponseEntity<List<Cliente>>(clientes,HttpStatus.OK);
	}
	/**
	 * EndPoint para actualizar un objeto de tipo cliente
	 * @param clienteId Parametro para buscar cliente
	 * @param cliente Objeto con la informacion a actualizar
	 * @return Retorna un objeto de tipo cliente con la información actualizada junto con código httpstatus 200 
	 * @author SACM - 10-12-21
	 */
	@PutMapping("/upd/clienteId/{clienteId}")
	public ResponseEntity<?> actualizarCliente(@PathVariable Integer clienteId, @RequestBody Cliente cliente)
	{
		Optional<Cliente> oCliente = clienteDao.buscarPorId(clienteId);
		
		if(!oCliente.isPresent())
			throw new NotFoundException(String.format("El cliente con ID: %d no existe", clienteId));
		
		Cliente clienteActualizado = clienteDao.actualizar(oCliente.get(), cliente); 
		
		return new ResponseEntity<Cliente>(clienteActualizado, HttpStatus.OK); 
	}
	/**
	 * EndPoint para eliminar un objeto de tipo cliente
	 * @param clienteId Parametro para objeto a eliminar
	 * @return Un mensaje con el id que se elimino junto con código httpstatus 202
	 * @author SACM - 10-12-21  
	 */
	@DeleteMapping("/del/clienteId/{clienteId}")
	public ResponseEntity<?> eliminarCliente(@PathVariable Integer clienteId)
	{
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
		Optional<Cliente> cliente = clienteDao.buscarPorId(clienteId);
		
		if(!cliente.isPresent())
			throw new NotFoundException(String.format("El cliente con ID: %d no existe", clienteId));
		
		clienteDao.eliminarPorId(clienteId);
		respuesta.put("OK", "Cliente ID: " + clienteId + " eliminado exitosamente");
		return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.ACCEPTED);
	}
	
	/**			------------- Problema para asiignar tarjeta
	 * EndPoint para agregar una tarjeta a un cliente
	 * @param clienteId Parametro para buscar cliente en base de datos
	 * @param tarjetaId Parametro para buscar tarjeta en base de datos
	 * @return Retorna un objeto de tipo cliente con la información actualizada junto con código httpstatus 200
	 * @author SACM - 11-12-21   
	 */
	@PutMapping("/cliente-asignar/{clienteId}/tarjeta/{tarjetaId}")
	public ResponseEntity<?> asignarTarjetaClienteManual(@PathVariable Integer clienteId, @PathVariable Integer tarjetaId)
	{
		Optional<Cliente> oCliente = clienteDao.buscarPorId(clienteId);
        if(!oCliente.isPresent()) 
            throw new NotFoundException(String.format("Cliente con id %d no existe", clienteId));
                
        Optional<Tarjeta> oTarjeta = tarjetaDao.buscarPorId(tarjetaId);        
        if(!oTarjeta.isPresent())
            throw new NotFoundException(String.format("Tarjeta con id %d no existe", tarjetaId));
       
        Cliente cliente = clienteDao.asignarTarjetaClienteManual(oCliente.get(), oTarjeta.get());
        
        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}
	
}
