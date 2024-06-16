package com.example.tienda.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tienda.model.Producto;
import com.example.tienda.repository.IProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService{
    
	@Autowired
	private IProductoRepository productoRepository;
	
	@Override
	public Producto save(Producto producto) {
		
		return productoRepository.save(producto);
		
	}

	@Override
	public Optional<Producto> get(Long id) {
		
		return productoRepository.findById(id);
	
	}

	@Override
	public void update(Producto producto) {
		
		productoRepository.save(producto);
	
	}

	@Override
	public void delete(Long id) {
		
		productoRepository.deleteById(id);
		
	}

	@Override
	public List<Producto> findAll() {
		
		return productoRepository.findAll();
	
	}

	@Override
public List<Producto> findByCategoria(String categoria) {
    return productoRepository.findByCategoria(categoria);
}

}
