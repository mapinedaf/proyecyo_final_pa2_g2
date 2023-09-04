package com.example.demo.repository.modelo.dto;

import java.math.BigDecimal;

import com.example.demo.repository.modelo.Vehiculo;

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
public class VehiculosVIPDTO {

    private Vehiculo vehiculo;

    private BigDecimal total;
    private BigDecimal subtotal;
    
}
