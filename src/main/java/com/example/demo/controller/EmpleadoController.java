package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.repository.modelo.Cliente;
import com.example.demo.repository.modelo.Cobro;
import com.example.demo.repository.modelo.Reserva;
import com.example.demo.repository.modelo.Vehiculo;
import com.example.demo.repository.modelo.dto.BusquedaVehiculoDTO;
import com.example.demo.repository.modelo.dto.ReservaClienteRedirectDTO;
import com.example.demo.repository.modelo.dto.ReservaClienteVehiculoDTO;
import com.example.demo.repository.modelo.dto.ReservaDTO;
import com.example.demo.service.IClienteService;
import com.example.demo.service.IReservaService;
import com.example.demo.service.IVehiculoService;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    private Cliente holderClienteBusqueda = new Cliente();

    private Vehiculo holderVehiculo = new Vehiculo();

    private ReservaDTO holderReservaDTO = new ReservaDTO();

    List<Vehiculo> vehiculos = new ArrayList<Vehiculo>();

    Reserva reservaHolder;

    @Autowired
    IClienteService clienteService;

    @Autowired
    IVehiculoService vehiculoService;

    @Autowired
    IReservaService reservaService;

    @GetMapping("/home")
    public String cargarVistaInicio() {
        vehiculos = vehiculoService.buscarTodos();
        return "vistaEmpleadosHome";
    }

    @GetMapping("/registrar-cliente")
    public String cargarVistaRegistrarCliente(Model model) {

        model.addAttribute("cliente", new Cliente());
        return "vistaEmpleadosRegistrarCliente";
    }

    @PostMapping("/realizar-registro")
    public String registrarCliente(Cliente cliente) {

        cliente.setRegistro("Empleado (E)");

        clienteService.agregar(cliente);
        return "redirect:/empleados/home";
    }

    @GetMapping("/buscar-cliente")
    public String cargarVistaBuscarCliente(Model model) {

        model.addAttribute("cliente", holderClienteBusqueda);
        return "vistaEmpleadosBuscarCliente";
    }

    @PutMapping("/realizar-busqueda-cliente")
    public String buscarCliente(Cliente cliente) {

        holderClienteBusqueda = clienteService.buscarPorCedula(cliente.getCedula());

        return "redirect:/empleados/buscar-cliente";
    }

    @GetMapping("/ingresar-vehiculo")
    public String cargarIngresarVehiculo(Model model) {
        model.addAttribute("vehiculo", new Vehiculo());
        return "vistaEmpleadosIngresarVehiculo";
    }

    @PostMapping("/realizar-ingreso-vehiculo")
    public String ingresarVehiculo(Vehiculo vehiculo) {

        vehiculo.setEstado("D");

        vehiculoService.agregar(vehiculo);

        return "redirect:/empleados/ingresar-vehiculo";
    }

    @GetMapping("/buscar-vehiculo")
    public String cargarVistaBuscarVehiculo(Model model) {
        model.addAttribute("vehiculo", holderVehiculo);
        return "vistaEmpleadosBuscarVehiculo";
    }

    @PutMapping("/realizar-busqueda-vehiculo")
    public String bucarVehiculo(Vehiculo vehiculo) {

        holderVehiculo = vehiculoService.buscarPorPlaca(vehiculo.getPlaca());
        return "redirect:/empleados/buscar-vehiculo";
    }

    @GetMapping("/retirar-vehiculo-reservado")
    public String cargarVistaRetirarVehiculoReservado(Model model) {

        model.addAttribute("reservaDTO", holderReservaDTO);
        return "vistaEmpleadosRetiroReserva";
    }

    @PutMapping("/cargar-informacion-reserva")

    public String cargarInformacionReserva(ReservaDTO reservaDTO) {


        holderReservaDTO = reservaService.buscarPorNumReserva(reservaDTO.getNumReserva());

        holderReservaDTO.setNumReserva(reservaDTO.getNumReserva());
        return "redirect:/empleados/retirar-vehiculo-reservado";
    }

    @PutMapping("/realizar-retiro-reserva")
    public String retirarVehiculoReservado(ReservaDTO reservaDTO) {

        vehiculoService.retirarReserva(reservaDTO.getNumReserva());
        return "redirect:/empleados/home";
    }

    @GetMapping("/retirar-vehiculo-sin-reserva")
    public String cargarVistaVehiculosDisponibles(Model model) {
        model.addAttribute("vehiculos", vehiculos);
        model.addAttribute("dto", new BusquedaVehiculoDTO());
        return "vistaEmpleadosRetiroNoReserva";
    }

    @PostMapping("/realizar-busqueda-vehiculos")
    public String buscarVehiculos(@ModelAttribute BusquedaVehiculoDTO dto) {
        vehiculos = vehiculoService.buscarPorMarcaModelo(dto.getMarca(), dto.getModelo());
        return "redirect:/empleados/retirar-vehiculo-sin-reserva";
    }

    @GetMapping("/reservar-vehiculo")
    public String cargarVistaReservarVehiculo(Model model) {
        model.addAttribute("dto", new ReservaClienteVehiculoDTO());
        return "vistaEmpleadosReservarVehiculo";
    }

    @PostMapping("/reservar")
    public String realizarReserva(ReservaClienteVehiculoDTO dto) {
        ReservaClienteRedirectDTO redirectReserva = reservaService.reservarVehiculo(dto);
        reservaHolder = redirectReserva.getReserva();
        String [] redirect = redirectReserva.getRedirect().split("/");
        return "redirect:/empleados/"+redirect[2] ;
    }

    @GetMapping("/cobro")
    public String paginaCobro(Model model) {

        model.addAttribute("reserva", reservaHolder);
        model.addAttribute(
                "cobro",
                Cobro.builder()
                        .fechaDeCobro(LocalDate.now()).build());
        return "vistaEmpleadosCobro";
    }


    @PostMapping("/cobrar")
    public String generarCobro(Cobro cobro){

        cobro.setReserva(reservaHolder);
        reservaHolder.setCobro(cobro);

        reservaService.agregar(reservaHolder);
        
        reservaHolder = null;

        return "redirect:/empleados/retirar-vehiculo-reservado";
    }
}
