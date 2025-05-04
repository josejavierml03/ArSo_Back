package arso.reservas.rest.dto;

import arso.reservas.modelo.Reserva;

public class ReservaDto {
	 	private String id;
	    private String idUsuario;
	    private int plazasReservadas;
	    private boolean cancelada;
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
