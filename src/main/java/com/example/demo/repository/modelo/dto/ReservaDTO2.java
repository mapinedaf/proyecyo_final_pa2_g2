package com.example.demo.repository.modelo.dto;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ReservaDTO2 {
	
    private String cedula;
    private String nombre;
    private BigDecimal subtotal;
    private BigDecimal total;
    
}
