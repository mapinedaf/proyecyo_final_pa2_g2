package com.example.demo.repository.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reserva")
public class Reserva {
	
	@Id
	@GeneratedValue(generator = "seq_reserva",strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "seq_reserva", sequenceName = "seq_reserva", allocationSize = 1)
	@Column(name = "rese_id")
	private Integer id;

	@Column(name = "rese_placa_vehiculo")
	private String placaVehiculo;

	@Column(name = "rese_cedula_cliente")
	private String cedulaCliente;

	@Column(name = "rese_fecha_de_inicio")
	private LocalDate fechaDeInicio;
	
	@Column(name = "rese_fecha_de_fin")
	private LocalDate fechaDeFin;
	
	@Column(name = "rese_valor_subtotal")
	private BigDecimal valorSubtotal;
	
	@Column(name = "rese_valor_IEC")
	private BigDecimal valorIEC;
	
	@Column(name = "rese_valor_total_a_pagar")
	private BigDecimal valorTotalAPagar;

	@Column(name = "rese_estado")
	private String estado;
	
	//CLAVES FORANEAS
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "rese_id_cliente")
	private Cliente cliente;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "rese_id_vehiculo")
	private Vehiculo vehiculo;
	
	@OneToOne(mappedBy = "reserva", cascade = CascadeType.ALL) 
	//Va ciudadano porque es con el que estamos mapeanod en la otra ENTIDAD
	private Cobro cobro;
	//-----

	@Override
	public String toString() {
		return "Reserva [id=" + id  + ", placaVehiculo=" + placaVehiculo
				+ ", cedulaCliente=" + cedulaCliente + ", fechaDeInicio=" + fechaDeInicio + ", fechaDeFin=" + fechaDeFin
				+ ", valorSubtotal=" + valorSubtotal + ", valorIEC=" + valorIEC + ", valorTotalAPagar="
				+ valorTotalAPagar + ", estado=" + estado + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
