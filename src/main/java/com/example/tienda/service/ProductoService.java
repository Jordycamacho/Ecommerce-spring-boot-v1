package com.example.tienda.service;

import java.util.List;
import java.util.Optional;

import com.example.tienda.model.Producto;

public interface ProductoService {
    
    public Producto save(Producto producto);
	
	public Optional<Producto> get(Long id);
	
	public void update(Producto producto);
	
	public void delete(Long id);
	
	public List<Producto> findAll();

	public List<Producto> findByCategoria(String categoria);

}
 