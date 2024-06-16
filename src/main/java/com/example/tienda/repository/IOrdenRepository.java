package com.example.tienda.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tienda.model.Orden;
import com.example.tienda.model.Usuario;


@Repository
public interface IOrdenRepository extends JpaRepository<Orden, Long> {

	List<Orden> findByUsuario (Usuario usuario);
	
}
