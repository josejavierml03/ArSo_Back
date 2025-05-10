package RabbitMQ;


import java.util.Map;

public interface PublicadorEventos {
    void publicar(String tipoEvento, String idEntidad, Map<String, Object> datos);
}