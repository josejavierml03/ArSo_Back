package arso.reservas.rest;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import arso.reservas.modelo.Reserva;
import arso.reservas.rest.dto.CrearReservaDto;
import arso.reservas.rest.dto.ReservaDto;
import arso.reservas.servicio.IServicioReservas;
import arso.reservas.servicio.ServicioReservas;

@RestController
@RequestMapping("/reservas")
public class ControladorReservas implements ReservasApi {
	
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
	
	@PreAuthorize("hasRole('USUARIO')")
	@GetMapping("/{id}")
	public EntityModel<ReservaDto> getReservaById(@PathVariable String id)throws Exception {

		Reserva reserva = this.servicio.getReserva(id);
		ReservaDto reservaDto = ReservaDto.fromEntity(reserva);
		EntityModel<ReservaDto> model = EntityModel.of(reservaDto);
		model.add(
				WebMvcLinkBuilder.linkTo(
						WebMvcLinkBuilder
						.methodOn(ControladorReservas.class)
						.getReservaById(id))
				.withSelfRel());
		return model;
	}
	
	@PreAuthorize("hasRole('USUARIO')")
	@PostMapping
	public ResponseEntity<Void> crearReserva(@Valid @RequestBody CrearReservaDto dto) throws Exception {
	    String id = this.servicio.Reservar(dto.getIdEvento(), dto.getIdUsuario(), dto.getPlazas());
	    URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest()
	 			.path("/{id}").buildAndExpand(id).toUri();        
        return ResponseEntity.created(nuevaURL).build();
	}
	
	@PreAuthorize("hasRole('GESTOR_EVENTOS')")
	@GetMapping("/evento/{idEvento}")
	public PagedModel<EntityModel<Reserva>> getReservasPaginado(Pageable paginacion, @PathVariable String idEvento) throws Exception {
		 
		 Page<Reserva> resultado = this.servicio.getListadoPaginado(paginacion, idEvento);
		 
		 return this.pagedResourcesAssembler.toModel(resultado, ReservaAssembler);
	}
	
	
}