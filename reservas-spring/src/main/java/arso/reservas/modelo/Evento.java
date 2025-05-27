package arso.reservas.modelo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Evento {
	@Id
	private String id;
	private int plazasDisponibles;
	private boolean cancelado;
	private List<Reserva> reservas;

	public Evento(int plazasDisponibles, boolean cancelado) {
		this.plazasDisponibles = plazasDisponibles;
		this.cancelado = cancelado;
		this.reservas = new ArrayList<>();
	}
	public Evento() {
		this.reservas = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getPlazasDisponibles() {
		return plazasDisponibles;
	}

	public void setPlazasDisponibles(int plazasDisponibles) {
		this.plazasDisponibles = plazasDisponibles;
	}

	public boolean isCancelado() {
		return cancelado;
	}

	public void setCancelado(boolean cancelado) {
		this.cancelado = cancelado;
	}

	public List<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}

	@Override
	public String toString() {
		return "Evento [id=" + id + ", plazasDisponibles=" + plazasDisponibles + ", cancelado=" + cancelado
				+ ", reservas=" + reservas + "]";
	}
}
