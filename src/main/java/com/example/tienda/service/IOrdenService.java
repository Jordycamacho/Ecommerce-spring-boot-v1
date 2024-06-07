package com.example.tienda.service;

import java.util.List;
import java.util.Optional;

import com.example.tienda.model.Orden;
import com.example.tienda.model.Usuario;

public interface IOrdenService {
	List<Orden> findAll();
	Optional<Orden> findById(Long id);
	Orden save (Orden orden);
	String generarNumeroOrden();
	List<Orden> findByUsuaio(Usuario usuario);
}
