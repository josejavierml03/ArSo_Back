package arso.eventos.rest;

import arso.eventos.modelo.Evento;
import arso.eventos.rest.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import arso.eventos.servicio.*;
import javax.validation.Valid;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/eventos")
public class ControladorEventos {

    @Autowired
    private IServicioEventos servicio;
    
    @Autowired
	private PagedResourcesAssembler<EventoResumen> pagedResourcesAssembler;
    
	@Autowired
	private EventoResumenAssembler eventoResumenAssembler;
	
	@Autowired
	private PagedResourcesAssembler<EspacioLibreDto> pagedResourcesAssemblerEspacioFisico;

    @PostMapping
    public ResponseEntity<Void> altaEvento(@Valid @RequestBody CrearEventoDto dto) throws Exception {
        String id = servicio.altaEvento(dto.getNombre(),
        								dto.getDescripcion(),
        								dto.getOrganizador(),
        								dto.getCategoria(),
        								LocalDateTime.parse(dto.getFechaInicio()),
        								LocalDateTime.parse(dto.getFechaFin()),
        								dto.getPlazas(),
        								dto.getIdEspacio());
        URI nuevaURL = ServletUriComponentsBuilder.fromCurrentRequest()
	 			.path("/{id}").buildAndExpand(id).toUri();        
        return ResponseEntity.created(nuevaURL).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> modificarEvento(@PathVariable String id,
                                                @Valid @RequestBody ModificarEventoDto dto) throws Exception {
        servicio.modificarEvento(id,
        						 LocalDateTime.parse( dto.getFechaInicio()),
        						 LocalDateTime.parse(dto.getFechaFin()),
        						 dto.getPlazas(),
        						 dto.getIdEspacio(),
        						 dto.getDescripcion());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/ocupacion")
    public ResponseEntity<Void> cancelarEvento(@PathVariable String id)
    		throws Exception { 
        servicio.cancelarEvento(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public PagedModel<EntityModel<EventoResumen>>eventosDelMes(
            @RequestParam("mes") int mes,
            @RequestParam("año") int año,
            Pageable paginacion) {

        List<EventoResumen> eventosR = servicio.eventosDelMes(mes, año);
        Page<EventoResumen> resultado = servicio.convertirAPaginable(eventosR,paginacion);
        return this.pagedResourcesAssembler.toModel(resultado,eventoResumenAssembler);
    }

    @GetMapping("/{id}")
    public EntityModel<EventoDto> getEvento(@PathVariable String id) throws Exception {
        Evento evento = servicio.getEvento(id);
        EventoDto encuestaDto = EventoDto.fromEntity(evento);
        EntityModel<EventoDto> model = EntityModel.of(encuestaDto);
		model.add(
			 WebMvcLinkBuilder.linkTo(
					 WebMvcLinkBuilder
					 	.methodOn(ControladorEventos.class)
					 	.getEvento(id))
				 .withSelfRel());
		return model;
    }
    
    @GetMapping("/{id}/ocupacion/activa")
    public ResponseEntity<Boolean> tieneOcupacionesActivas(@PathVariable String id) throws Exception {
        boolean resultado = servicio.obtenerOcupacionesActivasPorEspacio(id);
        return ResponseEntity.ok(resultado);   
    } 
    
    @GetMapping("/espacio/libre")
    public PagedModel<EntityModel<EspacioLibreDto>> obtenerEspaciosLibres(
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin,
            @RequestParam("capacidad") int capacidad,
            Pageable paginacion) {

	
        List<EspacioLibreDto> espaciosLibres = servicio.obtenerIdsEspaciosLibres(fechaInicio, fechaFin,capacidad);
        Page<EspacioLibreDto> resultado = servicio.convertirAPaginable(espaciosLibres, paginacion);
        return this.pagedResourcesAssemblerEspacioFisico.toModel(resultado);
    }

}
