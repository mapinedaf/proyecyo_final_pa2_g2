package com.example.demo.repository.modelo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class BusquedaVehiculoDTO {
    private String marca;
    private String modelo;
}
