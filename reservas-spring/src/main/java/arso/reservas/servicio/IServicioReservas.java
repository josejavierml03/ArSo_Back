package arso.reservas.servicio;

import java.util.List;
import arso.reservas.modelo.Reserva;
import repositorio.EntidadNoEncontrada;

public interface IServicioReservas {
	
	String Reservar (String id, String idUsuario, int plazas) throws EntidadNoEncontrada;
	
	Reserva getReserva (String id) throws EntidadNoEncontrada;
	
	List<Reserva> getReservas (String id) throws EntidadNoEncontrada;
	
}
