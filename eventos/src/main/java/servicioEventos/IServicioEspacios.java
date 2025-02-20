package servicioEventos;

import java.time.LocalDateTime;
import java.util.List;
import dominio.PuntoDeInteres;
import dto.EspacioFisicoDTO;
import repositorio.RepositorioException;
import repositorio.EntidadNoEncontrada;

public interface IServicioEspacios {
	
	String altaDeUnEspacioFisico(String nombre, String propietario, int capacidad, String direccion, double longitud, double latitud, String descripcion) throws RepositorioException;
	
	void asignarPuntosDeInteres(String id, List<PuntoDeInteres> puntos ) throws RepositorioException, EntidadNoEncontrada;

	void modificarEspacioFisico(String id, String nombre, Integer capacidad, String descripcion) throws RepositorioException, EntidadNoEncontrada;
	
	void darDeBajaEspacioFisico(String id) throws RepositorioException, EntidadNoEncontrada;
	
	void activarEspacioFisico(String id) throws RepositorioException, EntidadNoEncontrada;
	
	List<EspacioFisicoDTO> buscarEspaciosFisicosLibres(LocalDateTime fechaInicio, LocalDateTime fechaFin, int capacidadMinima) throws RepositorioException;
	
	public List<EspacioFisicoDTO> obtenerEspaciosPorPropietario(String propietario) throws RepositorioException;
}
