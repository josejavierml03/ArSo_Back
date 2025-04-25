package arso.eventos.rest;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


import arso.eventos.servicio.EventoResumen;

@Component
public class EventoResumenAssembler  implements RepresentationModelAssembler<EventoResumen, EntityModel<EventoResumen>>{
	
	@Override
    public EntityModel<EventoResumen> toModel(EventoResumen eventoResumen) {
    	try {
    		
	        EntityModel<EventoResumen> resultado = EntityModel.of(eventoResumen, 
	                linkTo(methodOn(ControladorEventos.class).getEvento(eventoResumen.getId())).withSelfRel());
	        return resultado;
	    } 
    	catch(Exception e) {
	    	return  EntityModel.of(eventoResumen);
	    }
    }

}
