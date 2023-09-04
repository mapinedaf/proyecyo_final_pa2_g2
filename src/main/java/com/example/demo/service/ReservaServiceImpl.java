package com.example.demo.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.IClienteRepository;
import com.example.demo.repository.IReservaRepository;
import com.example.demo.repository.IVehiculoRepository;
import com.example.demo.repository.modelo.Cliente;
import com.example.demo.repository.modelo.Cobro;
import com.example.demo.repository.modelo.Reserva;
import com.example.demo.repository.modelo.Vehiculo;
import com.example.demo.repository.modelo.dto.ReservaClienteRedirectDTO;
import com.example.demo.repository.modelo.dto.ReservaClienteVehiculoDTO;
import com.example.demo.repository.modelo.dto.ReservaDTO;
import com.example.demo.repository.modelo.dto.ReservaDTO2;
import com.example.demo.repository.modelo.dto.ReservaDTO3;
import com.example.demo.repository.modelo.dto.VehiculosVIPDTO;

import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@Service
@Transactional
public class ReservaServiceImpl implements IReservaService {

	@Autowired
	private IReservaRepository iReservaRepository;
	@Autowired
	private IVehiculoRepository iVehiculoRepository;
	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IClienteRepository clienteRepository;

	@Override
	@Transactional(value = TxType.REQUIRED)
	public void agregar(Reserva reserva) {
		this.iReservaRepository.insertar(reserva);
	}

	@Override
	@Transactional(value = TxType.REQUIRED)
	public void generar(String placa, String cedula, LocalDate fechaIn, LocalDate fechaFn) {
		if (fechaIn.isBefore(LocalDate.now())) {
		} else {
			List<Reserva> listReser = this.iReservaRepository.seleccionarFechasPorPlaca(placa);
			List<LocalDate> listFechaInicio = new ArrayList<>();
			List<LocalDate> listFechaFin = new ArrayList<>();
			for (Reserva reserva : listReser) {
				listFechaInicio.add(reserva.getFechaDeInicio());
				listFechaFin.add(reserva.getFechaDeFin());
			}
			Collections.sort(listFechaInicio, Collections.reverseOrder());
			Collections.sort(listFechaFin, Collections.reverseOrder());
			if (listReser.isEmpty()) {
				// Crear
				this.agregar(crearObjectoReserva(placa, cedula, fechaIn, fechaFn));
			} else {
				if (listReser.size() > 1) {
					boolean auxFecha = false;
					for (int i = listFechaInicio.size() - 1; i > 0; i--) {
						if (fechaIn.isAfter(listFechaFin.get(i)) && fechaFn.isBefore(listFechaInicio.get(i - 1))) {
							auxFecha = true;
							break;
						}
					}
					if (auxFecha) {
						// Crear
						this.agregar(crearObjectoReserva(placa, cedula, fechaIn, fechaFn));
					} else {

						if (fechaIn.isAfter(listFechaFin.get(0))) {
							// Crear
							this.agregar(crearObjectoReserva(placa, cedula, fechaIn, fechaFn));
						}
					}
				} else {
					if (fechaIn.isAfter(listFechaFin.get(listFechaFin.size() - 1))) {
						// Crear
						this.agregar(crearObjectoReserva(placa, cedula, fechaIn, fechaFn));
					}
				}
			}
		}
	}

	@Transactional(value = TxType.REQUIRED)
	public Reserva crearObjectoReserva(String placa, String cedula, LocalDate fechaIn, LocalDate fechaFn) {
		Vehiculo vehiculo = this.iVehiculoRepository.seleccionarPorPlaca(placa);
		Cliente cliente = this.clienteService.buscarPorCedula(cedula);

		long tiempo = fechaIn.until(fechaFn, ChronoUnit.DAYS);
		BigDecimal valorSubTotal = vehiculo.getValorPorDia().multiply(new BigDecimal(tiempo));
		BigDecimal valorIce = (valorSubTotal.multiply(new BigDecimal(15)).divide(new BigDecimal(100)));
		BigDecimal valorTotalPagar = valorSubTotal.add(valorIce);

		Reserva reserva = Reserva.builder().placaVehiculo(placa).cedulaCliente(cedula).fechaDeInicio(fechaIn)
				.fechaDeFin(fechaFn).valorSubtotal(valorSubTotal).valorIEC(valorIce).valorTotalAPagar(valorTotalPagar)
				.estado("Generada").cliente(cliente).vehiculo(vehiculo).build();
		// Poner la tarjeta
		// String numeroTarjeta = new Scanner(System.in).nextLine();

		Cobro cobro = Cobro.builder().numeroTarjeta("123456765432").fechaDeCobro(LocalDate.now()).reserva(reserva)
				.build();
		reserva.setCobro(cobro);
		return reserva;
	}

	@Override
	@Transactional(value = TxType.REQUIRED)
	public ReservaDTO buscarPorNumReserva(Integer numFactura) {
		return this.iReservaRepository.seleccionarPorIdDTO(numFactura);
	}

	@Override
	public List<Reserva> reportesReserva(LocalDate fechaInicio, LocalDate fechaFin) {

		return this.iReservaRepository.seleccionarReservaPorFecha(fechaInicio, fechaFin);
	}

	@Override
	public List<ReservaDTO2> reportesClientesVIP() {

		List<Reserva> clientesOrdenados = this.iReservaRepository.seleccionClientesVIP();
		List<String> numerosCedula = this.iReservaRepository.buscarCedulas();
		BigDecimal subtotal = new BigDecimal(0);
		BigDecimal total = new BigDecimal(0);
		List<ReservaDTO2> lista = new ArrayList<>();
		ReservaDTO2 res = null;
		String cedula = "";
		String nombre = "";
		Cliente cliente;
		for (String r : numerosCedula) {

			for (Reserva c : clientesOrdenados) {
				if (r.equals(c.getCedulaCliente())) {
					subtotal = subtotal.add(c.getValorSubtotal());
					total = total.add(c.getValorTotalAPagar());
					cedula = c.getCedulaCliente();
					cliente = this.clienteService.buscarPorCedula(cedula);
					nombre = cliente.getApellido();
				}

				res = ReservaDTO2.builder().cedula(cedula).nombre(nombre).subtotal(subtotal).total(total)
						.build();

			}
			lista.add(res);
			subtotal = new BigDecimal(0);
			total = new BigDecimal(0);
		}

		return lista;
	}

	@Override
	public List<ReservaDTO3> reporteVehiculosVIP(String mes, String anio) {

		LocalDate fechaInicio = LocalDate.of(Integer.parseInt(anio), Integer.parseInt(mes), 1);
		LocalDate fechaFin = LocalDate.of(Integer.parseInt(anio), Integer.parseInt(mes), 31);

		List<Reserva> listaVehiculosEnFecha = this.reportesReserva(fechaInicio, fechaFin);

		List<String> listaPlacaVehiculos = this.iReservaRepository.seleccionPlacasVehiculos();
		List<ReservaDTO3> lista = new ArrayList<>();
		ReservaDTO3 res = null;
		BigDecimal subtotal = new BigDecimal(0);
		BigDecimal total = new BigDecimal(0);
		String placa = "";

		for (String p : listaPlacaVehiculos) {
			for (Reserva c : listaVehiculosEnFecha) {
				if (p.equals(c.getPlacaVehiculo())) {
					subtotal = subtotal.add(c.getValorSubtotal());
					total = total.add(c.getValorTotalAPagar());
					placa = c.getPlacaVehiculo();
				}

				res = ReservaDTO3.builder().placa(placa).subtotal(subtotal).total(total)
						.build();

			}
			lista.add(res);
		}

		return lista;
	}

	@Override
	public List<VehiculosVIPDTO> reporteVehiculosVIP2(String mes, String anio) {

		LocalDate fechaInicio = LocalDate.of(Integer.parseInt(anio), Integer.parseInt(mes), 1);

		LocalDate fechaFin = LocalDate.of(Integer.parseInt(anio), Integer.parseInt(mes), fechaInicio.lengthOfMonth());

		List<VehiculosVIPDTO> vips = iVehiculoRepository
				.seleccionarTodos()
				.parallelStream()
				.map(

						vehiculo -> {

							List<Reserva> reservas = vehiculo.getReservasV()
									.stream()
									.filter(
											reserva -> (fechaInicio.isBefore(reserva.getFechaDeInicio()))
													&& fechaFin.isAfter(reserva.getFechaDeFin()))
									.collect(Collectors.toList());

;

							BigDecimal total = reservas.stream()
									.map(Reserva::getValorTotalAPagar)
									.reduce(BigDecimal.ZERO, BigDecimal::add);

							BigDecimal subTotal = reservas.stream()
									.map(Reserva::getValorSubtotal)
									.reduce(BigDecimal.ZERO, BigDecimal::add);

							VehiculosVIPDTO vip = new VehiculosVIPDTO();
							vip.setTotal(total);
							vip.setSubtotal(subTotal);
							vip.setVehiculo(vehiculo);

							return vip;
						})
				.collect(Collectors.toList());

		Collections.sort(vips, new Comparator<VehiculosVIPDTO>() {
			public int compare(VehiculosVIPDTO r1, VehiculosVIPDTO r2) {
				return r1.getTotal().compareTo(r2.getTotal());
			};
		});

		Collections.reverse(vips);

		return vips;
	}

	@Override
	public List<ReservaDTO2> reporteClientesVIP2() {

		List<Cliente> listaClientes = this.clienteRepository.seleccionarTodos();

		List<ReservaDTO2> reservas = listaClientes.parallelStream().map(
				cliente -> {
					BigDecimal total = cliente.getReservasC()
							.stream()
							.map(r -> r.getValorTotalAPagar())
							.reduce(BigDecimal.ZERO, BigDecimal::add);

					BigDecimal subTotal = cliente.getReservasC()
							.stream()
							.map(r -> r.getValorSubtotal())
							.reduce(BigDecimal.ZERO, BigDecimal::add);

					return ReservaDTO2
							.builder()
							.cedula(cliente.getCedula())
							.total(total)
							.subtotal(subTotal)
							.nombre(cliente.getNombre())
							.build();
				})
				.collect(Collectors.toList());

		Collections.sort(reservas, new Comparator<ReservaDTO2>() {

			public int compare(ReservaDTO2 r1, ReservaDTO2 r2) {
				return r1.getTotal().compareTo(r2.getTotal());
			};

		});
		Collections.reverse(reservas); // Orden inverso por la vista

		return reservas;
	}

	@Override
	@Transactional(TxType.NOT_SUPPORTED)
	public boolean estaDisponible(String placa, LocalDate fechaInicio, LocalDate fechaFin) {


		boolean estaDisponible = true;
		List<Reserva> reservas = new ArrayList<Reserva>();
		try {
			reservas = iReservaRepository.seleccionarPorPlacaYFechas(placa, fechaInicio, fechaFin);
		} catch (Exception e) {
			estaDisponible = true;
		}

		estaDisponible = reservas.size() == 0;

		return estaDisponible;
	}

	@Override

	public ReservaClienteRedirectDTO reservarVehiculo(ReservaClienteVehiculoDTO dto) {

		String redirect = "redirect:/clientes/no-disponible";
		Reserva reserva = new Reserva();

		
		if (this.estaDisponible(dto.getPlacaVehiculo(), dto.getFechaInicio(), dto.getFechaFin())) {

			redirect = "redirect:/clientes/cobro";

			Vehiculo vehiculo = this.iVehiculoRepository.seleccionarPorPlaca(dto.getPlacaVehiculo());
			Cliente cliente = this.clienteService.buscarPorCedula(dto.getCedulaCliente());

			long tiempo = dto.getFechaInicio().until(dto.getFechaFin(), ChronoUnit.DAYS);
			BigDecimal valorSubTotal = vehiculo.getValorPorDia().multiply(new BigDecimal(tiempo));
			BigDecimal valorIce = (valorSubTotal.multiply(new BigDecimal(15)).divide(new BigDecimal(100)));
			BigDecimal valorTotalPagar = valorSubTotal.add(valorIce);

			reserva = Reserva.builder()
					.cedulaCliente(dto.getCedulaCliente())
					.cliente(cliente)
					.estado("G")
					.fechaDeInicio(dto.getFechaInicio())
					.fechaDeFin(dto.getFechaFin())
					.vehiculo(vehiculo)
					.placaVehiculo(dto.getPlacaVehiculo())
					.valorSubtotal(valorSubTotal)
					.valorIEC(valorIce)
					.valorTotalAPagar(valorTotalPagar)
					.build();
		}

		ReservaClienteRedirectDTO dto2 =  new ReservaClienteRedirectDTO(reserva, redirect);
		return dto2;
	}

	@Override
	public Reserva buscarPorId(Integer Id) {
		return iReservaRepository.seleccionarPorId(Id);
	}

	@Override
	public List<Reserva> buscarTodo() {
		return this.iReservaRepository.seleccionarTodo();
	}

	@Override
	public LocalDate buscarPorPlacaUltimaFecha(String placa) {
		return this.iReservaRepository.seleccionarPorPlacaUltimaFecha(placa);
	}

}
