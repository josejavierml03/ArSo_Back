package arso.reservas.RabbitMQ;

public interface IConsumidorEventos {
	void procesar(EventoRabbitMQ evento);
}
