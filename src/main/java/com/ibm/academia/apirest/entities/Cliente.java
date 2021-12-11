package com.ibm.academia.apirest.entities;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
//@Table(name = "clientes", schema = "tarjeta")

@Table(name = "clientes")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message = "No puede ser nulo")
	@NotEmpty(message = "No puede ser vacio")
	@Size(min = 3, max = 80)
	@Column(name = "nombre", nullable = false, length = 60)
	private String nombre;
	
	@NotNull(message = "No puede ser nulo")
	@NotEmpty(message = "No puede ser vacio")
	@Size(min = 3, max = 80)
	@Column(name = "apellido", nullable = false, length = 60)
	private String apellido;
	
	@NotNull(message = "No puede ser nulo")
	@NotEmpty(message = "No puede ser vacio")
	@Size(min = 5, max = 80)
	@Column(name = "dni", nullable = false, unique = true, length = 10)
	private String dni;
	
	@NotNull(message = "No puede ser nulo")
	@NotEmpty(message = "No puede ser vacio")
	@Size(min = 4, max = 80)
	@Column(name = "pasion", nullable = false, unique = true, length = 10)
	private String pasion;
		
	@Positive(message = "El valor debe ser mayor a 0")
	@Column(name = "edad")
	private Integer edad;
	
	@Positive(message = "El valor debe ser mayor a 0")
	@Column(name = "salario")
	private Integer salario;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
			name = "cliente_tarjeta",
			joinColumns = @JoinColumn(name = "cliente_id"),
			inverseJoinColumns = @JoinColumn(name = "tarjeta_id")
	)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "clientes"})
	private Set<Tarjeta> tarjetas;
	
	@Column(name = "fecha_alta")
	private Date fechaAlta;
	
	@Column(name = "fecha_modificacion")
	private Date fechaModificacion;
	
	public Cliente(Integer id,
			@NotNull(message = "Nombre no puede ser nulo") @NotEmpty(message = "Nombre no puede ser vacio") @Size(min = 5, max = 80) String nombre,
			@NotNull(message = "Apellido no puede ser nulo") @NotEmpty(message = "Apellido no puede ser vacio") @Size(min = 5, max = 80) String apellido,
			@NotNull(message = "DNI no puede ser nulo") @NotEmpty(message = "DNI no puede ser vacio") @Size(min = 5, max = 80) String dni) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(apellido, dni, id, nombre);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(apellido, other.apellido) && Objects.equals(dni, other.dni)
				&& Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre);
	}

	@PrePersist
	private void antesPersistir()
	{
		this.fechaAlta = new Date();
	}
	
	@PreUpdate
	private void antesActualizar()
	{
		this.fechaModificacion = new Date();
	}

}
