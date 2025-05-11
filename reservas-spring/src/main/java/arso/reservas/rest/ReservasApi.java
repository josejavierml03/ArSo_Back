package arso.reservas.rest;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import arso.reservas.modelo.Reserva;
import arso.reservas.rest.dto.CrearReservaDto;
import arso.reservas.rest.dto.ReservaDto;
import io.swagger.v3.oas.annotations.Operation;

public interface ReservasApi {

	 @Operation(summary = "Obtener reserva", description = "Obtiene una reserva por su id")
	 @GetMapping("/{id}")
	 public ReservaDto getReservaById(@PathVariable String id) throws Exception;
	 
	 @Operation(summary = "Crear reserva", description = "Crea una reserva con los datos proporcionados")
	 @PostMapping
	 public String crearReserva(@Valid @RequestBody CrearReservaDto dto) throws Exception;
	 
	 @Operation(summary = "Obtener reservas", description = "Obtiene reservas para un cierto evento por su id")
	 @GetMapping("/evento/{idEvento}")
	 public PagedModel<EntityModel<Reserva>> getReservasPaginado(Pageable paginacion, @PathVariable String idEvento) throws Exception;
}