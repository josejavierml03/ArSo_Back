package programa;

import dominio.Categoria;
import dto.EspacioFisicoDTO;
import dominio.PuntoDeInteres;
import servicioEventos.ServicioEspacios;
import servicioEventos.ServicioEventos;
import servicioEventos.PuntosDeInteres;
import servicioEventos.EventoResumen;
import servicioEventos.IServicioEspacios;
import servicioEventos.IPuntosDeInteres;
import servicioEventos.IServicioEventos;
import repositorio.RepositorioException;
import repositorio.EntidadNoEncontrada;
import java.time.LocalDateTime;
import java.util.List;

public class Pruebas {
    public static void main(String[] args) throws Exception {
    	
        IServicioEspacios servicioEspacios = new ServicioEspacios();
        IServicioEventos servicioEventos = new ServicioEventos();
        IPuntosDeInteres servicioPuntosDeInteres = new PuntosDeInteres();

        try {
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
            LocalDateTime fechaInicio = LocalDateTime.of(2024, 11, 26, 9, 0);
            LocalDateTime fechaFin = LocalDateTime.of(2024, 11, 26, 20, 0);
            String eventoId = servicioEventos.altaEvento(
                    "Foro de Innovación", "Un evento sobre innovación tecnológica", "Carlos Ruiz",
                    Categoria.ACADEMICOS, fechaInicio, fechaFin, 200, "1");
            servicioEspacios.darDeBajaEspacioFisico(espacioId);
            System.out.println("Espacios de 'a'"+servicioEspacios.obtenerEspaciosPorPropietario("a"));
           
            System.out.println("Espacio físico dado de baja.");

            LocalDateTime fechaInicio2 = LocalDateTime.of(2024, 11, 10, 18, 0);
            LocalDateTime fechaFin2 = LocalDateTime.of(2024, 11, 11, 12, 0);
            System.out.println("Evento registrado con ID: " + eventoId);
            List<EspacioFisicoDTO> espaciosDisponibles = servicioEspacios.buscarEspaciosFisicosLibres(fechaInicio, fechaFin, 10);
            List<EspacioFisicoDTO> espaciosDisponibles2 = servicioEspacios.buscarEspaciosFisicosLibres(fechaInicio2, fechaFin2, 10);
            System.out.println(espaciosDisponibles.toString());
            System.out.println(espaciosDisponibles2.toString());

            servicioEventos.modificarEvento(eventoId, fechaInicio.plusHours(1), fechaFin.plusHours(1), 250, null, "Foro actualizado");
            System.out.println("Evento modificado.");

            servicioEventos.cancelarEvento(eventoId);
            System.out.println("Evento cancelado.");

            List<EventoResumen> eventosDelMes = servicioEventos.eventosDelMes(11, 2024);
            System.out.println("Eventos del mes:");
            for (EventoResumen resumen : eventosDelMes) {
                System.out.println(resumen.toString());
            }

            servicioEspacios.activarEspacioFisico(espacioId);
            System.out.println("Espacio fisico dado de alta.");
            List<EspacioFisicoDTO> espaciosDisponibles3 = servicioEspacios.buscarEspaciosFisicosLibres(fechaInicio, fechaFin, 10);
            List<EspacioFisicoDTO> espaciosDisponibles4 = servicioEspacios.buscarEspaciosFisicosLibres(fechaInicio2, fechaFin2, 10);
            System.out.println(espaciosDisponibles3.toString());
            System.out.println(espaciosDisponibles4.toString());

        } catch (RepositorioException | EntidadNoEncontrada e) {
            System.err.println("Error de repositorio: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
