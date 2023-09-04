package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.repository.modelo.Reserva;
import com.example.demo.repository.modelo.dto.BusquedaMesAñoDTO;
import com.example.demo.repository.modelo.dto.ReservaFechasDTO;
import com.example.demo.repository.modelo.dto.VehiculosVIPDTO;
import com.example.demo.service.IReservaService;

@Controller
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    IReservaService reservaService;

    List<Reserva> reservasHolder ;

    List<VehiculosVIPDTO> vehiculosVIPHolder;



    @GetMapping("/")
    public String inicializar(Model model){

        reservasHolder = reservaService.buscarTodo();
        vehiculosVIPHolder = reservaService.reporteVehiculosVIP2("1", "2000");
        return "redirect:/reportes/reservas";
    }

    @GetMapping("/reservas")
    public String cargarVistaReportesReservas(Model model){

        model.addAttribute("dto", new ReservaFechasDTO());
        model.addAttribute("reservas", reservasHolder);
        return "vistaReportesReservas";
    }

    @PutMapping("/filtrar-reporte-reservas")
    public String filtrarReporteReservas(ReservaFechasDTO dto){
        reservasHolder = reservaService.reportesReserva(dto.getFechaInicio(),dto.getFechaFin());

        return "redirect:/reportes/reservas";
    }
 
    
    @GetMapping("/clientes-vip")
    public String cargarVistaReportesClientesVIP(Model model){

        model.addAttribute("vips", reservaService.reporteClientesVIP2());

        return "vistaReportesClientesVIP";
    }


    @GetMapping("/vehiculos-vip")
    public String cargarVistaReportesVehiculosVIP(Model model){

        model.addAttribute("dto", new BusquedaMesAñoDTO());

        model.addAttribute("vips", vehiculosVIPHolder);

        return "vistaReportesVehiculosVIP";
    }

    @PutMapping("/generar-reporte-vehiculos-vip")
    public String generarReporteVehiculosVIP(BusquedaMesAñoDTO dto){
        vehiculosVIPHolder = reservaService.reporteVehiculosVIP2(dto.getMes(), dto.getAño());


        return "redirect:/reportes/vehiculos-vip";
    }
}
