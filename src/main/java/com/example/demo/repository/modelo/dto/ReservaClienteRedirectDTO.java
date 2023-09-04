package com.example.demo.repository.modelo.dto;

import com.example.demo.repository.modelo.Reserva;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class ReservaClienteRedirectDTO {

    Reserva reserva;

    String redirect;
    
}
