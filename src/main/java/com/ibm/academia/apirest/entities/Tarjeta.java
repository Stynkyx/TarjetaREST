package com.ibm.academia.apirest.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
//@Table(name = "tarjetas", schema = "tarjeta")

@Table(name = "tarjetas")
public class Tarjeta implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message = "No puede ser nulo")
	@NotEmpty(message = "No puede ser vacio")
	@Size(min = 5, max = 80)
	@Column(name = "pasion", nullable = false)
	private String pasion;
	
	@Positive(message = "El valor debe ser mayor a 0")
	@Column(name = "salario_minimo", nullable = false)
	private Integer salario_minimo;
	
	@Positive(message = "El valor debe ser mayor a 0")
	@Column(name = "salario_maximo", nullable = false)
	private Integer salario_maximo;
	
	@Positive(message = "El valor debe ser mayor a 0")
	@Column(name = "edad_minima", nullable = false)
	private Integer edad_minima;
	
	@Positive(message = "El valor debe ser mayor a 0")
	@Column(name = "edad_maxima", nullable = false)
	private Integer edad_maxima;
	
	@Column(name = "credito", nullable = false)
	private String credito;
	
	@ManyToOne(optional = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "cliente_id", foreignKey = @ForeignKey(name = "FK_CLIENTE_ID"))
	@JsonIgnoreProperties({"hibernateLazyInitializer", "clientes"})
	private Cliente cliente;
	
	public Tarjeta(String pasion, Integer salario_minimo, Integer salario_maximo, Integer edad_minima,
			Integer edad_maxima, String credito) {
		super();
		this.pasion = pasion;
		this.salario_minimo = salario_minimo;
		this.salario_maximo = salario_maximo;
		this.edad_minima = edad_minima;
		this.edad_maxima = edad_maxima;
		this.credito = credito;
	}

	@Override
	public int hashCode() {
		return Objects.hash(credito, edad_maxima, edad_minima, id, pasion, salario_maximo, salario_minimo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tarjeta other = (Tarjeta) obj;
		return credito == other.credito && Objects.equals(edad_maxima, other.edad_maxima)
				&& Objects.equals(edad_minima, other.edad_minima) && Objects.equals(id, other.id)
				&& Objects.equals(pasion, other.pasion) && Objects.equals(salario_maximo, other.salario_maximo)
				&& Objects.equals(salario_minimo, other.salario_minimo);
	}

	private static final long serialVersionUID = 2911762464333103201L;

}
