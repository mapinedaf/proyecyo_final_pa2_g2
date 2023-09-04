package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.repository.modelo.Cliente;
import com.example.demo.repository.modelo.Cobro;
import com.example.demo.repository.modelo.Reserva;
import com.example.demo.repository.modelo.Vehiculo;
import com.example.demo.repository.modelo.dto.BusquedaVehiculoDTO;
import com.example.demo.repository.modelo.dto.ReservaClienteRedirectDTO;
import com.example.demo.repository.modelo.dto.ReservaClienteVehiculoDTO;
import com.example.demo.service.IClienteService;
import com.example.demo.service.IReservaService;
import com.example.demo.service.IVehiculoService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private static final Logger LOG = Logger.getLogger(ClienteController.class);

    List<Vehiculo> vehiculos = new ArrayList<Vehiculo>();

    Reserva reservaHolder;

    BusquedaVehiculoDTO holderBusquedaVehiculoDTO = new BusquedaVehiculoDTO();

    ReservaClienteVehiculoDTO reservaClienteVehiculoDTOHolder = new ReservaClienteVehiculoDTO();

    @Autowired
    IVehiculoService vehiculoService;
    @Autowired
    IClienteService clienteService;

    @Autowired
    IReservaService reservaService;

    @GetMapping("/home")
    public String inicio(Model model) {
        LOG.info("Carga pagina de inicio");
        vehiculos = vehiculoService.buscarTodos();
        model.addAttribute("vehiculos", vehiculos);
        return "vistaClientesHome";
    }

    @GetMapping("/vehiculos-disponibles")
    public String cargarVistaVehiculosDisponibles(Model model) {
        LOG.info("Carga pagina de vehiculos disponibles");
        model.addAttribute("vehiculos", vehiculos);
        model.addAttribute("dto", holderBusquedaVehiculoDTO);
        return "vistaClientesVehiculosDisponibles";
    }

    @PostMapping("/realizar-busqueda-vehiculos")
    public String buscarVehiculos(@ModelAttribute BusquedaVehiculoDTO dto) {
        vehiculos = vehiculoService.buscarPorMarcaModelo(dto.getMarca(), dto.getModelo());
        return "redirect:/clientes/vehiculos-disponibles";
    }

    @GetMapping("/reservar-vehiculo")
    public String cargarVistaReservarVehiculo(Model model) {
        LOG.info("Carga pagina de reserva de vehiculo");
        model.addAttribute("dto", reservaClienteVehiculoDTOHolder);
        return "vistaClientesReservarVehiculo";
    }

    @GetMapping("/registrarse")
    public String cargarVistaClienteRegistrarse(Model model) {
        LOG.info("Carga pagina de registro");
        model.addAttribute("cliente", new Cliente());
        return "vistaClientesRegistrarse";
    }

    @PostMapping("/registrar-cliente")
    public String registrarCliente(Cliente cliente) {

        cliente.setRegistro("Cliente (C)");

        clienteService.agregar(cliente);
        return "redirect:/clientes/home";
    }

    @PostMapping("/reservar")
    public String realizarReserva(ReservaClienteVehiculoDTO dto) {

        ReservaClienteRedirectDTO redirectReserva = reservaService.reservarVehiculo(dto);

        reservaClienteVehiculoDTOHolder = dto;

        reservaHolder = redirectReserva.getReserva();

        return redirectReserva.getRedirect();
    }

    @GetMapping("/cobro")
    public String paginaCobro(Model model) {
        LOG.info("Carga pagina de cobro");
        model.addAttribute("reserva", reservaHolder);

        model.addAttribute(
                "cobro",
                Cobro.builder()
                        .fechaDeCobro(LocalDate.now()).build());
        return "vistaClientesCobro";
    }

    @PostMapping("/cobrar")
    public String generarCobro(Cobro cobro) {

        cobro.setReserva(reservaHolder);
        reservaHolder.setCobro(cobro);
        reservaService.agregar(reservaHolder);
        return "redirect:/clientes/exito";
    }

    @GetMapping("/exito")
    public String cargarVistaExito() {
        LOG.info("Carga pagina de exito de cobro");
        reservaHolder = null;
        return "vistaClientesExito";

    }

    @GetMapping("/no-disponible")

    public String cargarVistaFracaso(Model model) {
        LOG.info("Carga pagina de fracaso de cobro;");

        model.addAttribute("fecha",
                reservaService.buscarPorPlacaUltimaFecha(reservaClienteVehiculoDTOHolder.getPlacaVehiculo()));
        return "vistaClientesFracaso";
    }
}
