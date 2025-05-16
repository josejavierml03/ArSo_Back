package espacioFisico.servicioEventos;

import java.util.List;

import espacioFisico.dominio.PuntoDeInteres;


public interface IPuntosDeInteres {
	
	List<PuntoDeInteres> obtenerPuntosDeInteres(double latitud, double longitud);
		
	
}
