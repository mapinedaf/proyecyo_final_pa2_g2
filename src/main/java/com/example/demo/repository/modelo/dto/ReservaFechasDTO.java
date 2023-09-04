package com.example.demo.repository.modelo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class ReservaFechasDTO {

    LocalDate fechaInicio;
    LocalDate fechaFin;
    
}
