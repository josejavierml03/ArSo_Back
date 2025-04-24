package arso.eventos.modelo;

import java.time.LocalDateTime;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Evento{
	@Id @GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;
    private String nombre;
    @Lob
    private String descripcion;
    private String organizador;
    private int plazas;
    private boolean cancelado;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    @Embedded
    private Ocupacion ocupacion;
    
    public Evento(String nombre, String descripcion, String organizador, int plazas, Categoria categoria, LocalDateTime fechaInicio, LocalDateTime fechaFin, EspacioFisico espacioFisico) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.organizador = organizador;
        this.plazas = plazas;
        this.cancelado = false;
        this.categoria = categoria;
        this.ocupacion = new Ocupacion(fechaInicio, fechaFin, espacioFisico);
    }
    
    public Evento() {
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Ocupacion getOcupacion() {
		return ocupacion;
	}

	public void setOcupacion(Ocupacion ocupacion) {
		this.ocupacion = ocupacion;
	}
    
}
