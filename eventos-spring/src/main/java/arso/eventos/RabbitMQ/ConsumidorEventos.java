package arso.eventos.RabbitMQ;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import arso.eventos.modelo.EspacioFisico;
import arso.eventos.modelo.Estado;
import arso.eventos.modelo.PuntoDeInteres;
import arso.eventos.repositorio.RepositorioEspacio;

@Component
public class ConsumidorEventos {

	private final RepositorioEspacio repositorio;

	public ConsumidorEventos(RepositorioEspacio repositorio) {
		this.repositorio = repositorio;
	}

	@RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
	public void handleEvent(EventoRabbitMQ evento) {
		try {
			String tipo = evento.getTipoEvento();
			String id = evento.getIdEntidad();
			Map<String, Object> datos = evento.getDatos();

			switch (tipo) {
			case "espacio-creado":
				EspacioFisico nuevo = new EspacioFisico((String) datos.get("nombre"), (String) datos.get("direccion"),
						(Integer) datos.get("capacidad"));
				nuevo.setId(id);
				nuevo.setEstado(Estado.valueOf((String) datos.get("estado")));
				repositorio.save(nuevo);
				break;

			case "espacio-modificado":
				EspacioFisico existente = repositorio.findById(id)
						.orElseThrow(() -> new RuntimeException("Espacio Fisico no encontrado"));
				existente.setNombre((String) datos.get("nombre"));
				existente.setCapacidad((Integer) datos.get("capacidad"));
				existente.setDireccion((String) datos.get("descripcion"));
				repositorio.save(existente);
				break;

			case "espacio-activado":
			case "espacio-desactivado":
				EspacioFisico espacio = repositorio.findById(id)
						.orElseThrow(() -> new RuntimeException("Espacio Fisico no encontrado"));
				String estadoStr = (String) datos.get("estado");
				espacio.setEstado(Estado.valueOf(estadoStr));
				repositorio.save(espacio);
				break;

			case "espacio-asignarPuntos":
				@SuppressWarnings("unchecked") List<Map<String, Object>> puntos = (List<Map<String, Object>>) datos.get("puntos");

				List<PuntoDeInteres> puntosDeInteres = new ArrayList<>();
				for (Map<String, Object> punto : puntos) {
					PuntoDeInteres p = new PuntoDeInteres();
					p.setNombre((String) punto.get("nombre"));
					p.setDescripcion((String) punto.get("descripcion"));
					p.setDistancia(((Number) punto.get("distancia")).doubleValue());
					p.setUrlAWikipedia((String) punto.get("urlAWikipedia"));
					puntosDeInteres.add(p);
				}

				EspacioFisico espacioFisico = repositorio.findById(id)
						.orElseThrow(() -> new RuntimeException("Espacio Fisico no encontrado"));

				espacioFisico.setPuntosDeInteres(puntosDeInteres);
				repositorio.save(espacioFisico);

				break;

			default:
				System.out.println("Tipo de evento no manejado:" + tipo);
			}

		} catch (Exception e) {
			System.err.println("Error procesando evento: " + e.getMessage());
			e.printStackTrace();
		}

	}
}