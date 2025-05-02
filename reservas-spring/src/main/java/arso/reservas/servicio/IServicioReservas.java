package arso.reservas.servicio;

import java.util.List;
import arso.reservas.modelo.Reserva;

public interface IServicioReservas {
	
	String Reservar (String id, String idUsuario, int plazas);
	
	Reserva getReserva (String id);
	
	List<Reserva> getReservas (String id);
	
}
