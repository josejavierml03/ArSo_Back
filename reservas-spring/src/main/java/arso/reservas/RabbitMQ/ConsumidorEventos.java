package arso.reservas.RabbitMQ;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import arso.reservas.modelo.Evento;
import arso.reservas.repositorio.RepositorioEventos;

@Component
public class ConsumidorEventos {

	private final RepositorioEventos repositorio;

	public ConsumidorEventos(RepositorioEventos repositorio) {
		this.repositorio = repositorio;
	}

	@RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
	public void handleEvent(EventoRabbitMQ evento) {
		try {
			String tipo = evento.getTipoEvento();
			String id = evento.getIdEntidad();
			Map<String, Object> datos = evento.getDatos();
			Evento eventoAlta;

			switch (tipo) {
			case "evento-creado":
				eventoAlta = new Evento();
				eventoAlta.setId(id);
				eventoAlta.setPlazasDisponibles((Integer) datos.get("plazas"));
				eventoAlta.setCancelado((Boolean) datos.get("cancelado"));

				repositorio.save(eventoAlta);

			case "evento-modificado":
				eventoAlta = repositorio.findById(id)
						.orElseThrow(() -> new RuntimeException("Evento no encontrado con ID: " + id));

				if (datos.containsKey("plazas")) {
					eventoAlta.setPlazasDisponibles((Integer) datos.get("plazas"));
				}
				
				repositorio.save(eventoAlta);

			case "evento-cancelado":
				eventoAlta = repositorio.findById(id)
						.orElseThrow(() -> new RuntimeException("Evento no encontrado con ID: " + id));

				eventoAlta.setCancelado((Boolean) datos.get("cancelado"));

				repositorio.save(eventoAlta);

			default:
				System.out.println("Tipo de evento no manejado:" + tipo);
			}

		} catch (Exception e) {
			System.err.println("Error procesando evento: " + e.getMessage());
			e.printStackTrace();
		}

	}
}