package espacioFisico.servicioEventos;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import espacioFisico.dominio.EspacioFisico;
import espacioFisico.dominio.Estado;
import espacioFisico.dominio.PuntoDeInteres;
import espacioFisico.rabbitMQ.PublicadorEventos;
import espacioFisico.rabbitMQ.PublicadorEventosRabbitMQ;
import espacioFisico.repositorio.EntidadNoEncontrada;
import espacioFisico.repositorio.FactoriaRepositorios;
import espacioFisico.repositorio.Repositorio;
import espacioFisico.repositorio.RepositorioException;
import espacioFisico.repositorioEventos.RepositorioEspacioAdHoc;
import espacioFisico.rest.dto.EspacioLibreDto;

public class ServicioEspacios implements IServicioEspacios{
	
	private Repositorio<EspacioFisico, String> repositorio = FactoriaRepositorios.getRepositorio(EspacioFisico.class);
	RepositorioEspacioAdHoc repositorioAH =  FactoriaRepositorios.getRepositorio(EspacioFisico.class);
	private PublicadorEventos publicador = new PublicadorEventosRabbitMQ();


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
		
		Map<String, Object> datosEvento = Map.of(
			    "nombre", nombre,
			    "propietario", propietario,
			    "capacidad", capacidad,
			    "direccion", direccion,
			    "estado", espacioFisico.getEstado().toString()
			);
		publicador.publicar("espacio-creado", id, datosEvento);
			
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
	    
	    publicador.publicar("espacio-modificado", espacio.getId(), Map.of(
	    	    "nombre", espacio.getNombre(),
	    	    "capacidad", espacio.getCapacidad(),
	    	    "descripcion", espacio.getDescripcion()
	    	));

	    repositorio.update(espacio);
	}

	@Override
	public void darDeBajaEspacioFisico(String id) throws RepositorioException, EntidadNoEncontrada {
		
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		 try {
		        boolean activas = ServicioEventoProvider.getClient()
		            .tieneOcupacionesActivas(id)
		            .execute()
		            .body();

		        if (activas) {
		            throw new IllegalArgumentException("este espacioFisico tiene ocupaciones activas");
		        }
		    } catch (IOException e) {
		        throw new RepositorioException("Error al consultar el servicio de eventos", e);
		}
		EspacioFisico espacio = repositorio.getById(id);
		
		if(espacio.getEstado().equals(Estado.CERRADO_TEMPORALMENTE))
			throw new IllegalArgumentException("este espacioFisico ya esta dado de baja");
		
		espacio.setEstado(Estado.CERRADO_TEMPORALMENTE);
		
		publicador.publicar("espacio-desactivado", espacio.getId(), Map.of(
			    "estado", espacio.getEstado().toString()
			));

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
		publicador.publicar("espacio-activado", espacio.getId(), Map.of(
			    "estado", espacio.getEstado().toString()
			));

		repositorio.update(espacio);
		
	}

	@Override
	public List<EspacioFisico> buscarEspaciosFisicosLibres(LocalDateTime fechaInicio, LocalDateTime fechaFin,
	        int capacidadMinima) throws RepositorioException, EntidadNoEncontrada {
	    
	    if (fechaInicio.isAfter(fechaFin) || fechaInicio.equals(null) || fechaFin.equals(null)) 
	        throw new IllegalArgumentException("Fechas incorrectas");
	    
	    try {
	        String fechaInicioStr = fechaInicio.toString();
	        String fechaFinStr = fechaFin.toString();

	        List<EspacioLibreDto> respuesta = ServicioEventoProvider.getClient()
	            .obtenerEspaciosLibres(fechaInicioStr, fechaFinStr, capacidadMinima)
	            .execute()
	            .body();
	        if (respuesta == null ||  respuesta.isEmpty()) {
	        	throw new RepositorioException("No hay espacios libres");
	        }
	        List<EspacioFisico> espaciosFisicos = new ArrayList<>();

	        for (EspacioLibreDto dto : respuesta) {
	            EspacioFisico espacio = this.recuperarEspacioFisico(dto.getId());
	            espaciosFisicos.add(espacio);
	        }

	        return espaciosFisicos;

	    } catch (IOException e) {
	        throw new RepositorioException("Error al consultar espacios libres desde servicio de eventos", e);
	    }
	}

	
	@Override
	public List<EspacioFisico> obtenerEspaciosPorPropietario(String propietario) throws RepositorioException {
	    if (propietario == null || propietario.isEmpty())
	        throw new IllegalArgumentException("propietario: no debe ser nulo ni vacio");

	    List<EspacioFisico> espacios = repositorioAH.buscarPorPropietario(propietario);
	    return espacios;
	    
	}
	
	@Override
	public EspacioFisico recuperarEspacioFisico(String id) throws RepositorioException, EntidadNoEncontrada {
	    return repositorio.getById(id);
	}

}
