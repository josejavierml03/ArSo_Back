package arso.eventos;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import arso.eventos.modelo.Categoria;
import arso.eventos.servicio.EventoResumen;
import arso.eventos.servicio.IServicioEventos;

public class PruebaServicio {

	public static void main(String[] args) throws Exception {

		 ConfigurableApplicationContext contexto = SpringApplication.run(EventosSpringApplication.class, args);

	        IServicioEventos servicio = contexto.getBean(IServicioEventos.class);

	        String nombre = "Seminario de Innovación";
	        String descripcion = "Charla sobre innovación tecnológica";
	        String organizador = "Juan Pérez";
	        Categoria categoria = Categoria.ACADEMICOS;
	        LocalDateTime fechaInicio = LocalDateTime.now().plusDays(2);
	        LocalDateTime fechaFin = fechaInicio.plusHours(3);
	        int plazas = 100;
	        String idEspacio = "297ee716966576cc01966576cf880000"; 

	        String idEvento = servicio.altaEvento(nombre, descripcion, organizador, categoria, fechaInicio, fechaFin, plazas, idEspacio);
	        System.out.println("Evento creado con ID: " + idEvento);

	        LocalDateTime nuevaFechaInicio = fechaInicio.plusDays(1);
	        LocalDateTime nuevaFechaFin = nuevaFechaInicio.plusHours(2);
	        int nuevasPlazas = 120;

	        servicio.modificarEvento(String.valueOf(idEvento), nuevaFechaInicio, nuevaFechaFin, nuevasPlazas, null, "Descripción actualizada");
	        System.out.println("Evento modificado.");

	        int mes = LocalDateTime.now().getMonthValue();
	        int año = LocalDateTime.now().getYear();
	        List<EventoResumen> resumenes = servicio.eventosDelMes(mes, año);

	        for (EventoResumen resumen : resumenes) {
	            System.out.println(resumen);
	        }

	        servicio.cancelarEvento(String.valueOf(idEvento));
	        System.out.println("Evento cancelado.");

	        contexto.close();

	        System.out.println("fin.");

	}
}
