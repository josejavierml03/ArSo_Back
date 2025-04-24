package arso.eventos.servicio;

import java.time.LocalDateTime;
import java.util.List;

import arso.eventos.modelo.*;
import repositorio.*;

public interface IServicioEventos {
	
	String altaEvento (String nombre, String descripcion, String organizador, Categoria categoria, LocalDateTime fechaInicio, LocalDateTime fechaFin, int plazas, String idEspacio) throws EntidadNoEncontrada;
	
	void modificarEvento(String id,LocalDateTime fechaInicio, LocalDateTime fechaFin, Integer plazas, EspacioFisico espacioFisico,String descripcion) throws EntidadNoEncontrada;
	
	void cancelarEvento(String id) throws  EntidadNoEncontrada;
	
	List<EventoResumen> eventosDelMes(int mes, int año);
	
	public Evento getEvento(String id) throws EntidadNoEncontrada;

}
