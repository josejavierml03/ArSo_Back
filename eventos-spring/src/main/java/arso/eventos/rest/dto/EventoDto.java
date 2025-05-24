package arso.eventos.rest.dto;

import arso.eventos.modelo.Evento;

public class EventoDto {
	
	private String id;
    private String nombre;
    private String descripcion;
    private String organizador;
    private int plazas;
    private boolean cancelado;
    private String categoria;
    private String fechaInicio;
    private String fechaFin;
    private String espacioFisicoId;

    public EventoDto() {
    }

    public EventoDto(String id, String nombre, String descripcion, String organizador, int plazas, boolean cancelado,
                     String categoria, String fechaInicio, String fechaFin,String espacioFisicoId) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.organizador = organizador;
        this.plazas = plazas;
        this.cancelado = cancelado;
        this.categoria = categoria;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.setEspacioFisicoId(espacioFisicoId);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isCancelado() {
        return cancelado;
    }

    public void setCancelado(boolean cancelado) {
        this.cancelado = cancelado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

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

    public static EventoDto fromEntity(Evento evento) {
        return new EventoDto(
            evento.getId(),
            evento.getNombre(),
            evento.getDescripcion(),
            evento.getOrganizador(),
            evento.getPlazas(),
            evento.isCancelado(),
            evento.getCategoria().toString(),
            evento.getOcupacion().getFechaInicio().toString(),
            evento.getOcupacion().getFechaFin().toString(),
            evento.getOcupacion().getEspacioFisico().getId()
        );
    }

	public String getEspacioFisicoId() {
		return espacioFisicoId;
	}

	public void setEspacioFisicoId(String espacioFisicoId) {
		this.espacioFisicoId = espacioFisicoId;
	}

}
