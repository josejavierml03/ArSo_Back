package espacioFisico.rest.dto;

import java.util.LinkedList;

public class PuntosDeInteresDto {
	
	LinkedList<PuntoDeInteresDto> pto = new LinkedList<>();

	public LinkedList<PuntoDeInteresDto> getPto() {
		return pto;
	}

	public void setPto(LinkedList<PuntoDeInteresDto> pto) {
		this.pto = pto;
	}
	
}
