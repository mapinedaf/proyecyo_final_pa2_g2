package com.example.demo.repository.modelo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservaDTO {
    
    private Integer numReserva;
    private String placa;
    private String modelo;
    private String estado;
    private LocalDate fechaDeInicio;
    private LocalDate fechaDeFin;
    private String cedulaCliente;
    public ReservaDTO(String placa, String modelo, String estado, LocalDate fechaDeInicio, LocalDate fechaDeFin,
            String cedulaCliente) {
        this.placa = placa;
        this.modelo = modelo;
        this.estado = estado;
        this.fechaDeInicio = fechaDeInicio;
        this.fechaDeFin = fechaDeFin;
        this.cedulaCliente = cedulaCliente;
    }



}
