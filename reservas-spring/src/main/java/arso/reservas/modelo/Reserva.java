package arso.reservas.modelo;

public class Reserva {
    private String id;
    private String idUsuario;
    private int plazasReservadas;
    private boolean cancelada;
    private Evento evento;

    public Reserva(String idUsuario, int plazasReservadas, boolean cancelada, Evento evento) {
        this.idUsuario = idUsuario;
        this.plazasReservadas = plazasReservadas;
        this.cancelada = cancelada;
        this.evento = evento;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getPlazasReservadas() {
        return plazasReservadas;
    }

    public void setPlazasReservadas(int plazasReservadas) {
        this.plazasReservadas = plazasReservadas;
    }

    public boolean isCancelada() {
        return cancelada;
    }

    public void setCancelada(boolean cancelada) {
        this.cancelada = cancelada;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }
}
