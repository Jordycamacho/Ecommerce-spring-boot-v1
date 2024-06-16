package com.example.tienda.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tienda.model.Email;
import com.example.tienda.service.IEmailService;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private IEmailService emailService;

    @PostMapping("enviarMensage")
    public ResponseEntity<?>  requestEmail(@RequestParam Email email){

        System.out.println("Mensaje Recivido" + email);

        emailService.sendEmail(email.getCorreoU(), email.getAsunto(), email.getMensage());

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("estado", "Enviado");

        return ResponseEntity.ok(respuesta);
    
    }
}
