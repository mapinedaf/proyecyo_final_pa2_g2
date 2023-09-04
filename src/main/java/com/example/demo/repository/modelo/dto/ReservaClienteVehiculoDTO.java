package com.example.demo.repository.modelo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder    
@ToString
public class ReservaClienteVehiculoDTO {
    private String cedulaCliente;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String placaVehiculo;

    private String redirect;
}
