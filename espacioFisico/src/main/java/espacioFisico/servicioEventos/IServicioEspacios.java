package espacioFisico.servicioEventos;

import java.time.LocalDateTime;
import java.util.List;

import espacioFisico.dominio.EspacioFisico;
import espacioFisico.dominio.PuntoDeInteres;
import espacioFisico.repositorio.EntidadNoEncontrada;
import espacioFisico.repositorio.RepositorioException;


public interface IServicioEspacios {
	
	String altaDeUnEspacioFisico(String nombre, String propietario, int capacidad, String direccion, double longitud, double latitud, String descripcion) throws RepositorioException;
	
	void asignarPuntosDeInteres(String id, List<PuntoDeInteres> puntos ) throws RepositorioException, EntidadNoEncontrada;

	void modificarEspacioFisico(String id, String nombre, Integer capacidad, String descripcion) throws RepositorioException, EntidadNoEncontrada;
	
	void darDeBajaEspacioFisico(String id) throws RepositorioException, EntidadNoEncontrada;
	
	void activarEspacioFisico(String id) throws RepositorioException, EntidadNoEncontrada;
	
	List<EspacioFisico> buscarEspaciosFisicosLibres(LocalDateTime fechaInicio, LocalDateTime fechaFin, int capacidadMinima) throws RepositorioException,EntidadNoEncontrada;
	
	public List<EspacioFisico> obtenerEspaciosPorPropietario(String propietario) throws RepositorioException;
	
	EspacioFisico recuperarEspacioFisico(String id) throws RepositorioException, EntidadNoEncontrada;
}
