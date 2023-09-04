package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.repository.modelo.Reserva;
import com.example.demo.repository.modelo.dto.ReservaClienteRedirectDTO;
import com.example.demo.repository.modelo.dto.ReservaClienteVehiculoDTO;
import com.example.demo.repository.modelo.dto.ReservaDTO;
import com.example.demo.repository.modelo.dto.ReservaDTO2;
import com.example.demo.repository.modelo.dto.ReservaDTO3;
import com.example.demo.repository.modelo.dto.VehiculosVIPDTO;

public interface IReservaService {
    public void agregar(Reserva reserva);
    public void generar(String placa,String cedula,LocalDate fechaIn,LocalDate fechaFn);

    public ReservaDTO buscarPorNumReserva(Integer numFactura);
    
    public List<Reserva> reportesReserva(LocalDate fechaInicio,LocalDate fechaFin);

    public List<ReservaDTO2> reportesClientesVIP();
    
    public List<ReservaDTO3> reporteVehiculosVIP(String mes, String anio);

    public List<VehiculosVIPDTO> reporteVehiculosVIP2(String mes, String anio);
    
    public List<ReservaDTO2> reporteClientesVIP2();

    public boolean estaDisponible(String placa, LocalDate fechaInicio,LocalDate fechaFin);

    public ReservaClienteRedirectDTO reservarVehiculo(ReservaClienteVehiculoDTO dto);
    public Reserva buscarPorId(Integer Id);

    public List<Reserva> buscarTodo();


    public LocalDate buscarPorPlacaUltimaFecha(String placa);
}
