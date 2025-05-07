package arso.reservas.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import arso.reservas.modelo.Reserva;
import arso.reservas.rest.dto.CrearReservaDto;
import arso.reservas.rest.dto.ReservaDto;
import arso.reservas.servicio.IServicioReservas;
import arso.reservas.servicio.ServicioReservas;

@RestController
@RequestMapping("/reservas")
public class ControladorReservas {
	
	@Autowired
	private IServicioReservas servicio;
	
	@Autowired
	private PagedResourcesAssembler<Reserva> pagedResourcesAssembler;
	
	@Autowired
	private ReservaAssembler ReservaAssembler;
	
	@Autowired
	public ControladorReservas(ServicioReservas servicio) {
		this.servicio = servicio;
	}
	
	@GetMapping("/{id}")
	public ReservaDto getReservaById(@PathVariable String id)throws Exception {
		
	Reserva reserva = this.servicio.getReserva(id);
	
	return ReservaDto.fromEntity(reserva);
	}
	
	@PostMapping
	public String crearReserva(@Valid @RequestBody CrearReservaDto dto) throws Exception {
	    return this.servicio.Reservar(dto.getIdEvento(), dto.getIdUsuario(), dto.getPlazas());
	}
	
	@GetMapping("/evento/{idEvento}")
	public PagedModel<EntityModel<Reserva>> getReservasPaginado(Pageable paginacion, @PathVariable String idEvento) throws Exception {
		 
		 Page<Reserva> resultado = this.servicio.getListadoPaginado(paginacion, idEvento);
		 
		 return this.pagedResourcesAssembler.toModel(resultado, ReservaAssembler);
	}
	
	
}
