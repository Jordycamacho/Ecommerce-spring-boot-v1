package com.example.tienda.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "contacto")
public class Contacto {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
    private String email;
    private String mensaje;
    private String numero;
    private Date fechaCreada;
    
    public Contacto() {
        super();
    }

    public Contacto(Long id, String nombre, String email, String mensaje,Date fechaCreada ,String numero) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.mensaje = mensaje;
        this.fechaCreada = fechaCreada;
        this.numero = numero;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    public Date getFechaCreada() {
		return fechaCreada;
	}
	public void setFechaCreada(Date fechaCreada) {
		this.fechaCreada = fechaCreada;
	}
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Contacto [id=" + id + ", nombre=" + nombre + ", email=" + email + ", mensaje=" + mensaje + ", numero="
                + numero + "]";
    }
    


}
