package arso.eventos.servicio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import arso.eventos.modelo.*;
import arso.eventos.repositorio.RepositorioEspacio;
import arso.eventos.repositorio.RepositorioEvento;
import arso.eventos.repositorio.RepositorioEventoJPA;
import repositorio.EntidadNoEncontrada;

@Service
@Transactional
public class ServicioEventos implements IServicioEventos {
	
	private RepositorioEvento repositorioEvento;
	private RepositorioEspacio repositorioEspacio;
	RepositorioEventoJPA repositorioAH;
	
	@Autowired
	public ServicioEventos(RepositorioEvento repositorioEvento,RepositorioEspacio repositorioEspacio,RepositorioEventoJPA repositorioAH) {
		this.repositorioEvento = repositorioEvento;
		this.repositorioEspacio = repositorioEspacio;
		this.repositorioAH = repositorioAH;
	}

	@Override
	public
	String altaEvento (String nombre, String descripcion, String organizador, Categoria categoria, LocalDateTime fechaInicio, LocalDateTime fechaFin, int plazas, String idEspacio) 
			throws EntidadNoEncontrada {
		
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
		
		EspacioFisico espacio = repositorioEspacio.findById(idEspacio)
                .orElseThrow(() -> new EntidadNoEncontrada("Espacio no encontrado: " + idEspacio));

        if (espacio.getEstado() == Estado.CERRADO_TEMPORALMENTE) {
            throw new IllegalArgumentException("No se puede crear un evento con un espacio cerrado temporalmente");
        }
			
		Evento evento = new Evento(nombre, descripcion, organizador, plazas, categoria, fechaInicio, fechaFin, espacio);
		String id = repositorioEvento.save(evento).getId();
		return id;
			
	}
	
	public Evento getEvento(String id) throws EntidadNoEncontrada {
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		Optional<Evento> resultado = repositorioEvento.findById(id);
		if (resultado.isPresent() == false)
			throw new EntidadNoEncontrada("No existe encuesta con id: " + id);
		else
			return resultado.get();
	}

	@Override
	public void modificarEvento(String id, LocalDateTime fechaInicio, LocalDateTime fechaFin, Integer plazas,
	                            EspacioFisico espacioFisico, String descripcion)
	        throws EntidadNoEncontrada {

	    if (id == null || id.isEmpty()) {
	        throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
	    }

	    Evento evento = this.getEvento(id);

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

	    repositorioEvento.save(evento);
	}


	@Override
	public void cancelarEvento(String id) throws EntidadNoEncontrada {
		
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		
		Evento evento = this.getEvento(id);
		
		if(evento.isCancelado())
			throw new IllegalArgumentException("este evento ya esta cancelado");
		
		evento.setCancelado(true);
		repositorioEvento.save(evento);
		
	}

	@Override
	public List<EventoResumen> eventosDelMes(int mes, int año) {
		
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
