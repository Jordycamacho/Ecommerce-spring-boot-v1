package com.example.tienda.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "detalles")
public class DetalleOrden {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private double cantidad;
	private double precio;
	private double total;

	@ManyToOne
	private Orden orden;

	@ManyToOne
	private Producto producto;
	
	public DetalleOrden(String nombre, double cantidad, double precio, double total) {
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.precio = precio;
		this.total = total;
	}
	

	public DetalleOrden() {
		super();
	}
	public Long getId() {
		return id;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public double getCantidad() {
		return cantidad;
	}
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public Orden getOrden() {
		return orden;
	}
	public void setOrden(Orden orden) {
		this.orden = orden;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto prducto) {
		this.producto = prducto;
	}

	@Override
    public String toString() {
        return "DetalleOrden [id=" + id + ", nombre=" + nombre + ", cantidad=" + cantidad + ", precio=" + precio
                + ", total=" + total + "]";
    }
	
}
