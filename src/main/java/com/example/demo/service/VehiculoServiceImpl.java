package com.example.demo.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.IReservaRepository;
import com.example.demo.repository.IVehiculoRepository;
import com.example.demo.repository.modelo.Reserva;
import com.example.demo.repository.modelo.Vehiculo;
import com.example.demo.repository.modelo.dto.ReservaDTO;

import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@Service
public class VehiculoServiceImpl implements IVehiculoService {
	
	@Autowired
	private IVehiculoRepository iVehiculoRepository;
	@Autowired
	private IReservaRepository iReservaRepository;

	@Override
	@Transactional(value = TxType.REQUIRED)
	public void agregar(Vehiculo vehiculo) {
		

		
		this.iVehiculoRepository.ingresar(vehiculo);
	}

	@Override
	@Transactional(value = TxType.REQUIRED)
	public void actualizar(Vehiculo vehiculo) {
		this.iVehiculoRepository.actualizar(vehiculo);
		
	}

	@Override
	@Transactional(value = TxType.REQUIRED)
	public void eliminarPorPlaca(String placa) {
		this.iVehiculoRepository.eliminarPorPlaca(placa);
	}

	@Override
	@Transactional(value = TxType.REQUIRED)
	public Vehiculo buscarPorPlaca(String placa) {
		return this.iVehiculoRepository.seleccionarPorPlaca(placa);
	}

	@Override
	@Transactional(value = TxType.REQUIRED)
	public void actualizarEstado(String placa, String nuevoEstado) {
		Vehiculo vehiculoAuxiliar = this.iVehiculoRepository.seleccionarPorPlaca(placa);
		vehiculoAuxiliar.setEstado(nuevoEstado);
		this.iVehiculoRepository.actualizar(vehiculoAuxiliar);
	}

	@Override
	@Transactional(value = TxType.REQUIRED)
	public List<Vehiculo> buscarTodos() {
		return this.iVehiculoRepository.seleccionarTodos();
	}

	@Override
	@Transactional(value = TxType.REQUIRED)
	public List<Vehiculo> buscarPorMarcaModelo(String marca, String modelo) {
		return this.iVehiculoRepository.seleccionarPorMarcaModelo(marca, modelo);
	}

	@Override
	@Transactional(value = TxType.REQUIRED)
	public ReservaDTO retirarReserva(Integer numReserva) {
		
		Reserva reserva = this.iReservaRepository.seleccionarPorId(numReserva);
		reserva.setEstado("Ejecutado");
		Vehiculo vehiculo = reserva.getVehiculo();
		vehiculo.setEstado("Indisponible");

		this.iVehiculoRepository.actualizar(vehiculo);
		this.iReservaRepository.actualizar(reserva);
		return this.iReservaRepository.seleccionarPorIdDTO(numReserva);
	}

}
