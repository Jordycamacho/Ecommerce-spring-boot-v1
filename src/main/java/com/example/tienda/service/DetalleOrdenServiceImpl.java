package com.example.tienda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tienda.model.DetalleOrden;
import com.example.tienda.repository.IDetalleOrdenRepository;


@Service
public class DetalleOrdenServiceImpl implements IDetalleOrdenService{

	@Autowired
	private IDetalleOrdenRepository detalleOrdenRepository;
	@Override
	public DetalleOrden save(DetalleOrden detalleOrden) {

		return detalleOrdenRepository.save(detalleOrden);

	}
	
	
}
