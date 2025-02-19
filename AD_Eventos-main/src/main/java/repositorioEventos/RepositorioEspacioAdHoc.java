package repositorioEventos;

import java.time.LocalDateTime;
import java.util.List;

import dominio.EspacioFisico;
import dominio.Ocupacion;

public interface RepositorioEspacioAdHoc {
	public List<EspacioFisico> buscarEspaciosLibres(LocalDateTime fechaInicio, LocalDateTime fechaFin, int capacidadMinima);
	
	public List<Ocupacion> espacioConOcupacionesActivas(String id);
	
	public List<EspacioFisico> buscarPorPropietario(String propietario) ;
}
