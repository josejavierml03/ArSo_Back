package arso.eventos.RabbitMQ;

public interface IConsumidorEventos {
	void procesar(EventoRabbitMQ evento);
}
