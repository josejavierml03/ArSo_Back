package dto;

import java.io.Serializable;


import dominio.Estado;

@SuppressWarnings("serial")
public class EspacioFisicoDTO implements Serializable{
	
	private String id;
	private String nombre;
    private int capacidad;
    private String direccion;
    private String descripcion;
    private Estado estado;
    private String propietario;
    
	public EspacioFisicoDTO(String id,String nombre, int capacidad, String direccion, String descripcion, String propietario) {
		super();
		this.id=id;
		this.nombre = nombre;
		this.capacidad = capacidad;
		this.direccion = direccion;
		this.descripcion = descripcion;
		this.propietario = propietario;
	}
	
	public EspacioFisicoDTO(String id,String nombre, int capacidad, String direccion, Estado estado) {
		super();
		this.id=id;
		this.nombre = nombre;
		this.capacidad = capacidad;
		this.direccion = direccion;
		this.estado = estado;
	}
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "EspacioFisicoDTO [nombre=" + nombre + ", capacidad=" + capacidad + ", direccion=" + direccion
				+ ", descripcion=" + descripcion + ", estado=" + estado + ", propietario=" + propietario + "]";
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
	public String getDireccion() {
		return direccion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getPropietario() {
		return propietario;
	}
	public void setPropietario(String propietario) {
		this.propietario = propietario;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	


}
