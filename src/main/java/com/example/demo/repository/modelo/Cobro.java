package com.example.demo.repository.modelo;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 *
 * LUNES 21/08/2023
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cobro")
public class Cobro {
	
	@Id
	@GeneratedValue(generator = "seq_cobro",strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "seq_cobro", sequenceName = "seq_cobro", allocationSize = 1)
	@Column(name = "cobr_id")
	private Integer id;
	
	@Column(name = "cobr_numero_tarjeta")
	private String numeroTarjeta;
	
	@Column(name = "cobr_fecha_de_cobro")
	private LocalDate fechaDeCobro;
	
	@OneToOne
	@JoinColumn(name = "cobr_id_reserva")
	private Reserva reserva;

	@Override
	public String toString() {
		return "Cobro [id=" + id + ", numeroTarjeta=" + numeroTarjeta
				+ ", fechaDeCobro=" + fechaDeCobro + "]";
	}

	

}
