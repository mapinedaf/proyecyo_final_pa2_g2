package com.example.demo.service;


import java.util.List;
import com.example.demo.repository.modelo.Vehiculo;
import com.example.demo.repository.modelo.dto.ReservaDTO;

public interface IVehiculoService {
	
	public void agregar(Vehiculo vehiculo );

	public void actualizar(Vehiculo vehiculo);
	
	public void eliminarPorPlaca(String placa);

	public Vehiculo buscarPorPlaca(String placa);

	public List<Vehiculo> buscarPorMarcaModelo(String marca, String modelo);
	
	//METODO PAR ACTUALZIAR ESTADO DEL VEHICULO
	public void actualizarEstado(String placa, String nuevoEstado);
	
	//BUSCAMOS TODOS LOS VEHICULOS
	public List<Vehiculo> buscarTodos();

	public ReservaDTO retirarReserva(Integer numReserva);

}
