package servicioEventos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import dominio.Categoria;
import dominio.EspacioFisico;
import dominio.Estado;
import dominio.Evento;
import dominio.Ocupacion;
import dominio.PuntoDeInteres;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;
import repositorioEventos.RepositorioEventosAdHoc;

public class ServicioEventos implements IServicioEventos {
	
	private Repositorio<EspacioFisico, String> repositorioEspacio = FactoriaRepositorios.getRepositorio(EspacioFisico.class);
	private Repositorio<Evento, String> repositorioEvento = FactoriaRepositorios.getRepositorio(Evento.class);
	RepositorioEventosAdHoc repositorioAH=  FactoriaRepositorios.getRepositorio(Evento.class);

	@Override
	public
	String altaEvento (String nombre, String descripcion, String organizador, Categoria categoria, LocalDateTime fechaInicio, LocalDateTime fechaFin, int plazas, String idEspacio) 
			throws RepositorioException, EntidadNoEncontrada {
		
		if (nombre == null || nombre.isEmpty())
			throw new IllegalArgumentException("nombre: no debe ser nulo ni vacio");
			
		if (organizador == null || organizador.isEmpty())
			throw new IllegalArgumentException("organizador: no debe ser nulo ni vacio");
						 
		if (descripcion == null || descripcion.isEmpty())
			throw new IllegalArgumentException("descripcion: no debe ser nulo ni vacio");
			
		if (categoria.equals(null))
			throw new IllegalArgumentException("categoria: no debe ser nulo");
			
		if(fechaInicio.isAfter(fechaFin) || fechaInicio.equals(null) || fechaFin.equals(null)) 
			throw new IllegalArgumentException("fechas incorrectas");
			 
		if (plazas <= 0)
			throw new IllegalArgumentException("plazas: no debe ser menor o igual a 0");
			 
		if (idEspacio == null || idEspacio.isEmpty())
			throw new IllegalArgumentException("idEspacio: no debe ser nulo ni vacio");
		
		EspacioFisico espacio = repositorioEspacio.getById(idEspacio);
		
		if (espacio.getEstado() == Estado.CERRADO_TEMPORALMENTE) {
			throw new IllegalArgumentException("no se puede crear un evento con un espacio cerrado temporalmente");
		}
			
		Evento evento = new Evento(nombre, descripcion, organizador, plazas, categoria, fechaInicio, fechaFin, espacio);
		String id = repositorioEvento.add(evento);
		return id;
			
	}

	@Override
	public void modificarEvento(String id, LocalDateTime fechaInicio, LocalDateTime fechaFin, Integer plazas,
	                            EspacioFisico espacioFisico, String descripcion)
	        throws RepositorioException, EntidadNoEncontrada {

	    if (id == null || id.isEmpty()) {
	        throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
	    }

	    Evento evento = repositorioEvento.getById(id);

	    if (fechaInicio != null) {
	        evento.getOcupacion().setFechaInicio(fechaInicio);
	    }
	    if (fechaFin != null) {
	        evento.getOcupacion().setFechaFin(fechaFin);
	    }
	    if (plazas != null && plazas > 0) {
	        evento.setPlazas(plazas);
	    }
	    if (espacioFisico != null) {
	        evento.getOcupacion().setEspacioFisico(espacioFisico);
	    }
	    if (descripcion != null && !descripcion.isEmpty()) {
	        evento.setDescripcion(descripcion);
	    }

	    repositorioEvento.update(evento);
	}


	@Override
	public void cancelarEvento(String id) throws RepositorioException, EntidadNoEncontrada {
		
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		
		Evento evento = repositorioEvento.getById(id);
		
		if(evento.isCancelado())
			throw new IllegalArgumentException("este evento ya esta cancelado");
		
		evento.setCancelado(true);
		repositorioEvento.update(evento);
		
	}

	@Override
	public List<EventoResumen> eventosDelMes(int mes, int año) throws RepositorioException {
		
		List<Evento> eventos = repositorioAH.getEventosDelMes(mes, año);
		List<EventoResumen> eventosResumen = new ArrayList<>();
		
		for(Evento e : eventos) {
			EspacioFisico espacio = e.getOcupacion().getEspacioFisico();
			Ocupacion ocupacion = e.getOcupacion();
			EventoResumen eventoR = new EventoResumen();
			eventoR.setNombre(e.getNombre());
			eventoR.setDescipcion(e.getDescripcion());
			eventoR.setCategoria(e.getCategoria());
			eventoR.setFechaInicio(ocupacion.getFechaInicio());												
			eventoR.setDireccionEspacioFisico(espacio.getDireccion());
			eventoR.setNombreEspacioFisico(espacio.getNombre());
			List<PuntoDeInteres> puntosOrdenados = espacio.getPuntosDeInteres()
				    .stream()                                                                     
				    .sorted(Comparator.comparingDouble(PuntoDeInteres::getDistancia))
				    .collect(Collectors.toList()); 

			eventoR.setPuntos(puntosOrdenados);
			eventosResumen.add(eventoR);
		}
		
		return eventosResumen;
	}

}
