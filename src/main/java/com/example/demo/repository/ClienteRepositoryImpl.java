package com.example.demo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.repository.modelo.Cliente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@Repository
@Transactional
public class ClienteRepositoryImpl implements IClienteRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional(value = TxType.MANDATORY)
	public void ingresar(Cliente cliente) {

		this.entityManager.persist(cliente);
	}

	@Override
	@Transactional(value = TxType.MANDATORY)
	public void actualizar(Cliente cliente) {

		this.entityManager.merge(cliente);
	}

	@Override
	@Transactional(value = TxType.MANDATORY)
	public void eliminarPorCedula(String cedula) {

		Cliente clienteAux = this.seleccionarPorCedula(cedula);
		this.entityManager.remove(clienteAux);

	}

	@Override
	@Transactional(value = TxType.NOT_SUPPORTED)
	public Cliente seleccionarPorCedula(String cedula) {
		
		TypedQuery<Cliente> query = this.entityManager.createQuery("SELECT c FROM Cliente c WHERE c.cedula =: datoCedula", Cliente.class);
		query.setParameter("datoCedula", cedula);	
		return query.getSingleResult();
		
	}

	@Override
	public List<Cliente> seleccionarTodos() {
		
		TypedQuery<Cliente> query = this.entityManager.createQuery("SELECT c FROM Cliente c ", Cliente.class);
		
		return query.getResultList();
	}

}
