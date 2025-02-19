package servicioEventos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dominio.EspacioFisico;
import dominio.Estado;
import dominio.PuntoDeInteres;
import repositorio.EntidadNoEncontrada;
import repositorio.Repositorio;
import repositorio.RepositorioException;
import repositorioEventos.RepositorioEspacioAdHoc;
import repositorio.FactoriaRepositorios;

public class ServicioEspacios implements IServicioEspacios{
	
	private Repositorio<EspacioFisico, String> repositorio = FactoriaRepositorios.getRepositorio(EspacioFisico.class);
	RepositorioEspacioAdHoc repositorioAH =  FactoriaRepositorios.getRepositorio(EspacioFisico.class);

	@Override
	public String altaDeUnEspacioFisico(String nombre, String propietario, int capacidad, String direccion,
			double longitud, double latitud, String descripcion) throws RepositorioException {
		
		if (nombre == null || nombre.isEmpty())
			throw new IllegalArgumentException("nombre: no debe ser nulo ni vacio");
			
		if (propietario == null || propietario.isEmpty())
			throw new IllegalArgumentException("propietario: no debe ser nulo ni vacio");
			
		if (direccion == null || direccion.isEmpty())
			throw new IllegalArgumentException("direccion: no debe ser nulo ni vacio");
			 
		if (descripcion == null || descripcion.isEmpty())
				throw new IllegalArgumentException("descripcion: no debe ser nulo ni vacio");
			
		if (capacidad <= 0)
				throw new IllegalArgumentException("capacidad: no debe ser menor o igual a 0");
			 
		EspacioFisico espacioFisico = new EspacioFisico(nombre, propietario, capacidad, direccion, longitud, latitud, descripcion);
			
		String id = repositorio.add(espacioFisico);
			
		return id;
	}

	@Override
	public void asignarPuntosDeInteres(String id, List<PuntoDeInteres> puntos)
			throws RepositorioException, EntidadNoEncontrada {
		
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		
		if (puntos == null || puntos.isEmpty())
			throw new IllegalArgumentException("punots: no debe ser nulo ni vacio");
		
		EspacioFisico espacio = repositorio.getById(id);
		espacio.setPuntosDeInteres(puntos);
		repositorio.update(espacio);
		
	}

	@Override
	public void modificarEspacioFisico(String id, String nombre, Integer capacidad, String descripcion)
	        throws RepositorioException, EntidadNoEncontrada {

	    if (id == null || id.isEmpty()) {
	        throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
	    }

	    EspacioFisico espacio = repositorio.getById(id);

	    if (nombre != null && !nombre.isEmpty()) {
	        espacio.setNombre(nombre);
	    }
	    if (capacidad != null && capacidad > 0) {
	        espacio.setCapacidad(capacidad);
	    }
	    if (descripcion != null && !descripcion.isEmpty()) {
	        espacio.setDescripcion(descripcion);
	    }

	    repositorio.update(espacio);
	}

	@Override
	public void darDeBajaEspacioFisico(String id) throws RepositorioException, EntidadNoEncontrada {
		
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		boolean activas = repositorioAH.espacioConOcupacionesActivas(id).stream().anyMatch(o -> o.calcularActiva());
		if(activas) {
			throw new IllegalArgumentException("este espacioFisico tiene ocupaciones activas");
		}
		EspacioFisico espacio = repositorio.getById(id);
		
		if(espacio.getEstado().equals(Estado.CERRADO_TEMPORALMENTE))
			throw new IllegalArgumentException("este espacioFisico ya esta dado de baja");
		
		espacio.setEstado(Estado.CERRADO_TEMPORALMENTE);
		repositorio.update(espacio);
		
	}

	@Override
	public void activarEspacioFisico(String id) throws RepositorioException, EntidadNoEncontrada {
		
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		
		EspacioFisico espacio = repositorio.getById(id);
		
		if(espacio.getEstado().equals(Estado.ACTIVO))
			throw new IllegalArgumentException("este espacioFisico ya esta activo");
		
		espacio.setEstado(Estado.ACTIVO);
		repositorio.update(espacio);
		
	}

	@Override
	public List<EspacioFisico> buscarEspaciosFisicosLibres(LocalDateTime fechaInicio, LocalDateTime fechaFin,
			int capacidadMinima) throws RepositorioException {
		
		if(fechaInicio.isAfter(fechaFin) || fechaInicio.equals(null) || fechaFin.equals(null)) 
				throw new IllegalArgumentException("fechas incorrectas");
		
		List<EspacioFisico> espacios = repositorioAH.buscarEspaciosLibres(fechaInicio, fechaFin, capacidadMinima);
		return espacios;
	}
	
	@Override
	public List<EspacioFisico> obtenerEspaciosPorPropietario(String propietario) throws RepositorioException {
	    if (propietario == null || propietario.isEmpty())
	        throw new IllegalArgumentException("propietario: no debe ser nulo ni vacio");

	    List<EspacioFisico> espacios = repositorioAH.buscarPorPropietario(propietario);
	    return espacios;
	    
	}




}
