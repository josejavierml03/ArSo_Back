package arso.eventos.RabbitMQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class PublicadorEventos {

    private static final String EXCHANGE = RabbitMQConfig.EXCHANGE_NAME;
    private static final String ORIGEN = "eventos";

    private final RabbitTemplate rabbitTemplate;

    public PublicadorEventos(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void emitirEvento(String tipoEvento, String idEntidad, Map<String, Object> datos) {
        Map<String, Object> evento = new HashMap<>();
        evento.put("fechaHora", LocalDateTime.now().toString());
        evento.put("idEntidad", idEntidad);
        evento.put("tipoEvento", tipoEvento);
        evento.put("datos", datos);

        String routingKey = "bus." + ORIGEN + "." + tipoEvento;

        rabbitTemplate.convertAndSend(EXCHANGE, routingKey, evento);
        System.out.println(routingKey);
    }
}