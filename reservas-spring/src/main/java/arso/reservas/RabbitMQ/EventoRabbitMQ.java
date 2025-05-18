package arso.reservas.RabbitMQ;

import java.util.Map;

public class EventoRabbitMQ {

	private String tipoEvento;
	private String idEntidad;
	private String fechaHora;
	private Map<String, Object> datos;

	public EventoRabbitMQ() {
	}

	public String getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public String getIdEntidad() {
		return idEntidad;
	}

	public void setIdEntidad(String idEntidad) {
		this.idEntidad = idEntidad;
	}

	public String getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(String fechaHora) {
		this.fechaHora = fechaHora;
	}

	public Map<String, Object> getDatos() {
		return datos;
	}

	public void setDatos(Map<String, Object> datos) {
		this.datos = datos;
	}

	@Override
	public String toString() {
		return "EventoDto{" +
				"tipoEvento='" + tipoEvento + '\'' +
				", idEntidad='" + idEntidad + '\'' +
				", fechaHora='" + fechaHora + '\'' +
				", datos=" + datos +
				'}';
	}
}
