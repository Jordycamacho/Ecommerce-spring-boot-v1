package com.example.tienda.service;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements IEmailService{

    @Value("${email.sender}") 
    private String myEmail;
 
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(String[] correoU, String asunto, String mensage) {
       
        SimpleMailMessage mailMessage = new SimpleMailMessage();
       
        mailMessage.setFrom(myEmail);
        mailMessage.setTo(correoU);
        mailMessage.setSubject(asunto);
        mailMessage.setText(mensage);
        mailSender.send(mailMessage);
    }

    @Override
    public void sendEmailWithFile(String[] correoU, String asunto, String mensage, File file) {
       
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
            mimeMessageHelper.setFrom(myEmail);
            mimeMessageHelper.setTo(correoU);
            mimeMessageHelper.setSubject(asunto);
            mimeMessageHelper.setText(mensage);

            mimeMessageHelper.addAttachment(file.getName(),file);

            mailSender.send(mimeMessage);
            
        } catch (MessagingException e) {
            
            e.printStackTrace();
        }
    }

    @Override
public void sendEmailContact(String[] correoU, String correo, String asunto, String mensaje, String numero) {
    String formattedMessage = "Enviado por: " + asunto  + "\nNúmero: " + mensaje + "\nAsunto: " + correo+ "\nMensaje: " + numero;

    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setFrom(myEmail);
    mailMessage.setTo(correoU);
    mailMessage.setSubject("Mensaje de contacto");
    mailMessage.setText(formattedMessage);

    mailSender.send(mailMessage);
}

}
