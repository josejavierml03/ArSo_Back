package arso.reservas.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para crear la Reserca")
public class CrearReservaDto {
	
	@Schema(description = "Identificador del evento en el que se realiza la reserva")
    private String idEvento;
	
	@Schema(description = "Identificador del usuario que realiza la reserva")
    private String idUsuario;
    
	@Schema(description = "Numero de plazas a reservar")
    private int plazas;

    public CrearReservaDto() {
    	
    }
    
    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getPlazas() {
        return plazas;
    }

    public void setPlazas(int plazas) {
        this.plazas = plazas;
    }
}