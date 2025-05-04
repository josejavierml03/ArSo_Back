package arso.reservas.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import arso.reservas.modelo.Reserva;
import arso.reservas.rest.dto.CrearReservaDto;
import arso.reservas.rest.dto.ReservaDto;
import arso.reservas.servicio.IServicioReservas;
import arso.reservas.servicio.ServicioReservas;

@RestController
@RequestMapping("/reservas")
public class ControladorReservas {
	
	private IServicioReservas servicio;
	
	@Autowired
	public ControladorReservas(ServicioReservas servicio) {
		this.servicio = servicio;
	}
	
	@GetMapping("/{id}")
	public ReservaDto getReservaById( @PathVariable String id)throws Exception {
		
	Reserva reserva = this.servicio.getReserva(id);
	
	return ReservaDto.fromEntity(reserva);
	}
	
	@PostMapping
	public String crearReserva(@Valid @RequestBody CrearReservaDto dto) throws Exception {
	    return this.servicio.Reservar(dto.getIdEvento(), dto.getIdUsuario(), dto.getPlazas());
	}
	
	

}
