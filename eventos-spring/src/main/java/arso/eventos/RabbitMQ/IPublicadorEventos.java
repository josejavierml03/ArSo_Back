package arso.eventos.RabbitMQ;

import java.util.Map;

public interface IPublicadorEventos {
	void publicar(String tipoEvento, String idEntidad, Map<String, Object> datos);

}
