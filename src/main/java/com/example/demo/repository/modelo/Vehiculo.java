package com.example.demo.repository.modelo;

import java.math.BigDecimal;
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


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Builder
@Table(name = "vehiculo")
public class Vehiculo {
	
	@Id
	@GeneratedValue(generator = "seq_vehiculo",strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "seq_cliente", sequenceName = "seq_vehiculo", allocationSize = 1)
	@Column(name = "vehi_id")
	private Integer id;
	
	@Column(name = "vehi_placa")
	private String placa;
	
	@Column(name = "vehi_modelo")
	private String modelo;
	
	@Column(name = "vehi_marca")
	private String marca;
	
	@Column(name = "vehi_anio")
	private String anio; 
	
	@Column(name = "vehi_estado")
	private String estado;
	
	@Column(name = "vehi_idvalor_por_dia")
	private BigDecimal valorPorDia;
	
	@Column(name = "vehi_pais_origen")
	private String paisOrigen;
	
	@Column(name = "vehi_cilindraje")
	private Integer cilindraje;
	
	@Column(name = "vehi_avaluo")
	private BigDecimal avaluo;
	
	@Column(name = "vehi_direccion_imagen")
	private String direccionImagen;
	
	@OneToMany(mappedBy = "vehiculo",  fetch = FetchType.EAGER)
	private List<Reserva> reservasV;

	@Override
	public String toString() {
		return "Vehiculo [id=" + id + ", placa=" + placa + ", modelo=" + modelo + ", marca=" + marca + ", anio=" + anio
				+ ", estado=" + estado + ", valorPorDia=" + valorPorDia + ", paisOrigen=" + paisOrigen + ", cilindraje="
				+ cilindraje + ", avaluo=" + avaluo + ", direccionImagen=" + direccionImagen + "]";
	}

	

}
