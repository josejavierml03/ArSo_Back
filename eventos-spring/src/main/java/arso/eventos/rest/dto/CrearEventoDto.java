package arso.eventos.rest.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import arso.eventos.modelo.Categoria;


public class CrearEventoDto {
	
	@NotBlank(message = "nombre: no debe ser nulo ni vacío")
    private String nombre;

    @NotBlank(message = "descripcion: no debe ser nulo ni vacío")
    private String descripcion;

    @NotBlank(message = "organizador: no debe ser nulo ni vacío")
    private String organizador;

    @Min(value = 1, message = "plazas: no debe ser menor o igual a 0")
    private int plazas;

    @NotNull(message = "categoria: no debe ser nula")
    private Categoria categoria;

    @NotBlank(message = "fechaInicio: no debe ser nula ni vacía")
    private String fechaInicio;

    @NotBlank(message = "fechaFin: no debe ser nula ni vacía")
    private String fechaFin;

    @NotBlank(message = "idEspacio: no debe ser nulo ni vacío")
    private String idEspacio;
    
    
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
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
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getOrganizador() {
		return organizador;
	}
	public void setOrganizador(String organizador) {
		this.organizador = organizador;
	}
	public int getPlazas() {
		return plazas;
	}
	public void setPlazas(int plazas) {
		this.plazas = plazas;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getIdEspacio() {
		return idEspacio;
	}
	public void setIdEspacio(String idEspacio) {
		this.idEspacio = idEspacio;
	}
    
}
