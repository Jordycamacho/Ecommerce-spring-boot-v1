package com.example.tienda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tienda.model.Contacto;
import com.example.tienda.repository.IContactoRepository;

@Service
public class IContactoServiceImpl implements IContactoService{
    
    @Autowired
    private IContactoRepository contactoRepository;

    @Override
    public Contacto save(Contacto contacto) {
        return contactoRepository.save(contacto);  
    }

    @Override
    public List<Contacto> findAll() {

        return contactoRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        contactoRepository.deleteById(id);
    }

    
    
}
