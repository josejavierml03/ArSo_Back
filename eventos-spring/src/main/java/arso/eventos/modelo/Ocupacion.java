package arso.eventos.modelo;

import java.time.LocalDateTime;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class Ocupacion {

	private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    @ManyToOne
    private EspacioFisico espacioFisico;
    
    public Ocupacion(LocalDateTime fechaInicio, LocalDateTime fechaFin, EspacioFisico espacioFisico) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.espacioFisico = espacioFisico;
    }
    
    public Ocupacion() {
    }
    
    public boolean calcularActiva() {
        LocalDateTime ahora = LocalDateTime.now();
        return !fechaInicio.isAfter(ahora) && !fechaFin.isBefore(ahora);
    }

    public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	public EspacioFisico getEspacioFisico() {
		return espacioFisico;
	}

	public void setEspacioFisico(EspacioFisico espacioFisico) {
		this.espacioFisico = espacioFisico;
	}

}
