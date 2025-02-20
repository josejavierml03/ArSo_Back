package servicioEventos;

import java.util.List;
import dominio.PuntoDeInteres;


public interface IPuntosDeInteres {
	
	List<PuntoDeInteres> obtenerPuntosDeInteres(double latitud, double longitud);
		
	
}
