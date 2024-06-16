package com.example.tienda.model;

public class Email {

    
    private String [] correoU;
    private String asunto;
    private String mensage;
    
    public Email(String[] correoU, String asunto, String mensage) {
        this.correoU = correoU;
        this.asunto = asunto;
        this.mensage = mensage;
    }
    
    public Email() {
    }

    public String[] getCorreoU() {
        return correoU;
    }
    
    public String getAsunto() {
        return asunto;
    }
    
    public String getMensage() {
        return mensage;
    }

    @Override
    public String toString() {
        return "Email [correoU=" + correoU + ", asunto=" + asunto + ", mensage=" + mensage + "]";
    }
    
}
