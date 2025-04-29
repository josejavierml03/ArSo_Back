package arso.eventos.servicio;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import arso.eventos.modelo.*;
import arso.eventos.rest.dto.EspacioLibreDto;
import repositorio.*;

public interface IServicioEventos {
	
	String altaEvento (String nombre, String descripcion, String organizador, Categoria categoria, LocalDateTime fechaInicio, LocalDateTime fechaFin, int plazas, String idEspacio) throws EntidadNoEncontrada;
	
	void modificarEvento(String id,LocalDateTime fechaInicio, LocalDateTime fechaFin, Integer plazas, String espacioFisicoId,String descripcion) throws EntidadNoEncontrada;
	
	void cancelarEvento(String id) throws  EntidadNoEncontrada;
	
	List<EventoResumen> eventosDelMes(int mes, int año);
	
	public Evento getEvento(String id) throws EntidadNoEncontrada;
	
	public <T> Page<T> convertirAPaginable(List<T> lista, Pageable pageable);
	
	Boolean obtenerOcupacionesActivasPorEspacio(String idEspacio) throws EntidadNoEncontrada ;

	List<EspacioLibreDto> obtenerIdsEspaciosLibres(LocalDateTime fechaInicio, LocalDateTime fechaFin,int capacidad);

}
