package arso.reservas.modelo;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Document
public class Reserva {
	
    private String id;
    private String idUsuario;
    private int plazasReservadas;
    private boolean cancelada;
    @JsonIgnore  
    @DBRef
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
