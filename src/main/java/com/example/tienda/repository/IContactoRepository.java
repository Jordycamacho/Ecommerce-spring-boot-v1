package com.example.tienda.repository;

import org.springframework.stereotype.Repository;

import com.example.tienda.model.Contacto;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface IContactoRepository  extends JpaRepository<Contacto, Long>{
    
}
