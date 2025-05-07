package arso.reservas.rest.dto;

import arso.reservas.modelo.Reserva;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de la entidad Reserva")
public class ReservaDto {
	
		@Schema(description = "Identificador de la reserva")
	 	private String id;
		
		@Schema(description = "Identificador del usuario que realiza la reserva")
	    private String idUsuario;
		
		@Schema(description = "Numero de plazas reservadas")
	    private int plazasReservadas;
	    
		@Schema(description = "Indica si la reserva esta cancaleada")
	    private boolean cancelada;
	    
		@Schema(description = "Evento sobre el que se realiza la reserva")
	    private String evento;
	    
	    public ReservaDto() {
	    	
	    }
	    
		public ReservaDto(String id, String idUsuario, int plazasReservadas, boolean cancelada, String evento) {
			super();
			this.id = id;
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
		public String getEvento() {
			return evento;
		}
		public void setEvento(String evento) {
			this.evento = evento;
		}
		
		public static ReservaDto fromEntity (Reserva reserva) {
			return new ReservaDto(reserva.getId(), reserva.getIdUsuario(), reserva.getPlazasReservadas(),
					reserva.isCancelada(), reserva.getEvento().toString());
		}
		    
}
