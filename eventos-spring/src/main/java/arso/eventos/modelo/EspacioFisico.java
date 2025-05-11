package arso.eventos.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import javax.persistence.Id;

@Entity
public class EspacioFisico {
	@Id
	private String id;
    private String nombre;
    private String direccion;
    @ElementCollection
    private List<PuntoDeInteres> puntosDeInteres;
    @Enumerated(EnumType.STRING)
    private Estado estado;
    private int capacidad;

    public EspacioFisico(String nombre, String direccion,int capacidad) {

        this.nombre = nombre;
        this.direccion = direccion;
        this.puntosDeInteres = new ArrayList<PuntoDeInteres>();
        this.estado = Estado.ACTIVO;
        this.setCapacidad(capacidad);
    }
    

	@Override
	public String toString() {
		return "EspacioFisico [id=" + id + ", nombre=" + nombre + ", direccion=" + direccion + ", puntosDeInteres="
				+ puntosDeInteres + ", estado=" + estado + "]";
	}


	public EspacioFisico() {
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

	
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	public List<PuntoDeInteres> getPuntosDeInteres() {
		return puntosDeInteres;
	}

	public void setPuntosDeInteres(List<PuntoDeInteres> puntosDeInteres) {
		this.puntosDeInteres = puntosDeInteres;
	}



	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}


	public int getCapacidad() {
		return capacidad;
	}


	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
    
}
