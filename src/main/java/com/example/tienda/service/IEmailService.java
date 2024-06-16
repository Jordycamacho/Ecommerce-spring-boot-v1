package com.example.tienda.service;

import java.io.File;

public interface IEmailService {
    

    void sendEmail(String[] correoU, String asunto, String mensage);

    void sendEmailWithFile(String[] correoU, String asunto, String mensage, File file);

    void sendEmailContact(String[] correoU, String correo, String asunto, String mensage, String numero );


}
 