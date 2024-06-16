package com.example.tienda.service;

import java.util.List;

import com.example.tienda.model.Contacto;

public interface IContactoService {
    
    public Contacto save (Contacto contacto);
    public List<Contacto> findAll();
    public void delete(Long id);

}
