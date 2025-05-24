package arso.eventos.servicio;

import java.time.LocalDateTime;
import java.util.List;

import arso.eventos.modelo.*;

public class EventoResumen {

	private String id;
	private String nombre;
	private String descripcion;
	private LocalDateTime fechaInicio;
	private Categoria categoria;
	private String nombreEspacioFisico;
	private String direccionEspacioFisico;
	private List<PuntoDeInteres> puntos;
	
	public List<PuntoDeInteres> getPuntos() {
		return puntos;
	}

	public void setPuntos(List<PuntoDeInteres> puntos) {
		this.puntos = puntos;
	}

	@Override
	public String toString() {
		return "EventoResumen [id=" + id + ", nombre=" + nombre + ", descipcion=" + descripcion + ", fechaInicio="
				+ fechaInicio + ", categoria=" + categoria + ", nombreEspacioFisico=" + nombreEspacioFisico
				+ ", direccionEspacioFisico=" + direccionEspacioFisico + ", puntos=" + puntos + "]";
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descipcion) {
		this.descripcion = descipcion;
	}
	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public String getNombreEspacioFisico() {
		return nombreEspacioFisico;
	}
	public void setNombreEspacioFisico(String nombreEspacioFisico) {
		this.nombreEspacioFisico = nombreEspacioFisico;
	}
	public String getDireccionEspacioFisico() {
		return direccionEspacioFisico;
	}
	public void setDireccionEspacioFisico(String direccionEspacioFisico) {
		this.direccionEspacioFisico = direccionEspacioFisico;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
