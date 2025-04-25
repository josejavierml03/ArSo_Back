package arso.eventos.rest.dto;

import javax.validation.constraints.NotNull;

public class ModificarEventoDto {
	
	@NotNull(message = "fechaInicio: no debe ser nula")
    private String fechaInicio;
    @NotNull(message = "fechaFin: no debe ser nula")
    private String fechaFin;
    @NotNull(message = "plazas: no debe ser nula")
    private Integer plazas;
    @NotNull(message = "idEspacio: no debe ser nula")
    private String idEspacio; 
    @NotNull(message = "descripcion: no debe ser nula")
    private String descripcion;

    public ModificarEventoDto() {}
    
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

	public Integer getPlazas() {
        return plazas;
    }

    public void setPlazas(Integer plazas) {
        this.plazas = plazas;
    }

    public String getIdEspacio() {
        return idEspacio;
    }

    public void setIdEspacio(String idEspacio) {
        this.idEspacio = idEspacio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
