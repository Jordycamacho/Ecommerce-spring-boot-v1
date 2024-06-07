package com.example.tienda.service;

import java.util.List;
import java.util.Optional;

import com.example.tienda.model.Usuario;


public interface IUsuarioService {
	
	List<Usuario> findAll();

	Optional<Usuario> findById(Long id);
	
	Usuario save(Usuario usuario);

	Optional<Usuario> findByEmail(String email);
}
