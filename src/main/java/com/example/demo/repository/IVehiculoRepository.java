package com.example.demo.repository;


import java.util.List;

import com.example.demo.repository.modelo.Vehiculo;

public interface IVehiculoRepository {
	
	public void ingresar(Vehiculo vehiculo);

	public void actualizar(Vehiculo vehiculo);
	
	public void eliminarPorPlaca(String placa);

	public Vehiculo seleccionarPorPlaca(String placa);

	public List<Vehiculo> seleccionarPorMarcaModelo(String marca, String modelo);
	
	public List<Vehiculo> seleccionarTodos();

}
