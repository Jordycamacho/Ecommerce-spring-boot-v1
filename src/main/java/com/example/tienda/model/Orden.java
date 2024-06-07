package com.example.tienda.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(name = "ordenes")
public class Orden {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String numero;
	private Date fechaCreada;
	private Date fechaRecibida;
	private double total;
	
	@ManyToOne
	private Usuario usuario;
	
	@OneToMany(mappedBy = "orden")
	private List<DetalleOrden> detalle;
	
	public Orden(String numero, Date fechaCreada, Date fechaRecibida, double total, Usuario usuario) {
		super();
		this.numero = numero;
		this.fechaCreada = fechaCreada;
		this.fechaRecibida = fechaRecibida;
		this.total = total;
		this.usuario = usuario;
	}

	public Orden() {
		super();
	}

	public Long getId() {
		return id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Date getFechaCreada() {
		return fechaCreada;
	}

	public void setFechaCreada(Date fechaCreada) {
		this.fechaCreada = fechaCreada;
	}

	public Date getFechaRecibida() {
		return fechaRecibida;
	}

	public void setFechaRecibida(Date fechaRecibida) {
		this.fechaRecibida = fechaRecibida;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public List<DetalleOrden> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<DetalleOrden> detalle) {
		this.detalle = detalle;
	}

	@Override
    public String toString() {
        return "Orden [id=" + id + ", numero=" + numero + ", fechaCreada=" + fechaCreada + ", fechaRecibida="
                + fechaRecibida + ", total=" + total + "]";
    }
	
}
