package com.example.tienda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tienda.model.Producto;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Long>{

    List<Producto> findByCategoria(String categoria);

}
