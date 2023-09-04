package com.example.demo.repository.modelo;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cliente")
@Builder
public class Cliente {
	
	@Id
	@GeneratedValue(generator = "seq_cliente",strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "seq_cliente", sequenceName = "seq_cliente", allocationSize = 1)
	@Column(name = "clie_id")
	private Integer id;
	@Column(name = "clie_cedula")
	private String cedula;
	
	@Column(name = "clie_nombre")
	private String nombre;
	
	@Column(name = "clie_apellido")
	private String apellido;
	
	@Column(name = "clie_fecha_de_nacimiento")
	private LocalDate fechaDeNacimiento;
	
	@Column(name = "clie_genero") 
	private String genero;
	
	@Column(name = "clie_registro")
	private String registro;
	
	@OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
	private List<Reserva> reservasC;

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", cedula=" + cedula + ", nombre=" + nombre + ", apellido=" + apellido
				+ ", fechaDeNacimiento=" + fechaDeNacimiento + ", genero=" + genero + ", registro=" + registro + "]";
	}

	
	

}
