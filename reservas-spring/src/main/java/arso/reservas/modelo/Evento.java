package arso.reservas.modelo;

import java.util.List;

public class Evento {
    private String id;
    private int plazasDisponibles;
    private boolean cancelado;
    private List<Reserva> reservas;

    public Evento(int plazasDisponibles, boolean cancelado, List<Reserva> reservas) {
        this.plazasDisponibles = plazasDisponibles;
        this.cancelado = cancelado;
        this.reservas = reservas;
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
}
