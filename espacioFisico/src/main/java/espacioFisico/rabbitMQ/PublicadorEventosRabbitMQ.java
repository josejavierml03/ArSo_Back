package espacioFisico.rabbitMQ;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class PublicadorEventosRabbitMQ implements PublicadorEventos {

    private final String EXCHANGE = "bus";
    private final String ORIGEN = "espacios";
    private final Channel canal;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PublicadorEventosRabbitMQ() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            String url = "amqps://lcirmeef:4hti3qfThjZ9N5xb_3Jh_rTMYVB71Jpf@rat.rmq2.cloudamqp.com/lcirmeef";
            factory.setUri(url);
            Connection connection = factory.newConnection();
            this.canal = connection.createChannel();
            this.canal.exchangeDeclare(EXCHANGE, "topic", true);
        } catch (Exception e) {
            throw new RuntimeException("Error inicializando conexión RabbitMQ", e);
        }
    }

    @Override
    public void publicar(String tipoEvento, String idEntidad, Map<String, Object> datos) {
        try {
            Map<String, Object> evento = new HashMap<>();
            evento.put("idEntidad", idEntidad);
            evento.put("tipoEvento", tipoEvento);
            evento.put("fechaHora", LocalDateTime.now().toString());
            evento.put("datos", datos);

            String routingKey = "bus." + ORIGEN + "." + tipoEvento;
            String json = objectMapper.writeValueAsString(evento);

            try {
                canal.basicPublish(EXCHANGE, routingKey, null, json.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                System.err.println("Error publicando a RabbitMQ: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error publicando evento", e);
        }
    }
}