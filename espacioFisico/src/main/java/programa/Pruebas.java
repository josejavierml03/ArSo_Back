package programa;

import dominio.PuntoDeInteres;
import servicioEventos.ServicioEspacios;

import servicioEventos.PuntosDeInteres;
import servicioEventos.IServicioEspacios;
import servicioEventos.IPuntosDeInteres;
import repositorio.RepositorioException;
import repositorio.EntidadNoEncontrada;
import java.time.LocalDateTime;
import java.util.List;

public class Pruebas {
    public static void main(String[] args) throws Exception {
        IServicioEspacios servicioEspacios = new ServicioEspacios();
        IPuntosDeInteres servicioPuntosDeInteres = new PuntosDeInteres();

        
        String espacioId = servicioEspacios.altaDeUnEspacioFisico(
                "Auditorio Principal", "a", 300, "Calle Principal 123", 37.98412315304831, 19.4326, "Auditorio para conferencias");
        System.out.println("Espacio físico creado con ID: " + espacioId);

        double latitud = 37.98412315304831;  
        double longitud = -1.1291804565199284;

        List<PuntoDeInteres> puntosDeInteres = servicioPuntosDeInteres.obtenerPuntosDeInteres(latitud, longitud);

        if (!puntosDeInteres.isEmpty()) {
           servicioEspacios.asignarPuntosDeInteres(espacioId, puntosDeInteres);
           System.out.println("Puntos de interés asignados al espacio físico.");
        } else {
            System.out.println("No se encontraron puntos de interés cercanos para las coordenadas proporcionadas.");
        }

        servicioEspacios.modificarEspacioFisico(espacioId, "Auditorio Renovado", 350, "Auditorio actualizado");
        System.out.println("Espacio físico modificado.");



        
    }
}