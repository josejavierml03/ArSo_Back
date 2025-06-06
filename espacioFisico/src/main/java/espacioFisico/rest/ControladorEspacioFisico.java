package espacioFisico.rest;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import espacioFisico.dominio.EspacioFisico;
import espacioFisico.dominio.PuntoDeInteres;
import espacioFisico.repositorio.EntidadNoEncontrada;
import espacioFisico.repositorio.RepositorioException;
import espacioFisico.rest.Listado.EspacioFisicoExtendido;
import espacioFisico.rest.dto.CrearEspacioFisicoDto;
import espacioFisico.rest.dto.EspacioFisicoDto;
import espacioFisico.rest.dto.PuntoDeInteresDto;
import espacioFisico.rest.dto.PuntosDeInteresDto;
import espacioFisico.rest.dto.UpdateEspacioFisicoDto;
import espacioFisico.servicio.FactoriaServicios;
import espacioFisico.servicioEventos.IServicioEspacios;

@Path("espacios")
public class ControladorEspacioFisico {
	
	private IServicioEspacios servicio =  FactoriaServicios.getServicio(IServicioEspacios.class); 
	
	@Context
	private UriInfo uriInfo;
	
	@Context
	private HttpServletRequest servletRequest;
	
	@POST
	@Consumes( MediaType.APPLICATION_JSON )
	@RolesAllowed("PROPIETARIO_ESPACIOS")
	public Response createEspacio(CrearEspacioFisicoDto es) throws RepositorioException {
		String id = servicio.altaDeUnEspacioFisico(es.getNombre(),
												   es.getPropietario(),
												   es.getCapacidad(),
												   es.getDireccion(),
												   es.getLongitud(),
												   es.getLatitud(),
												   es.getDescripcion());
		URI nuevaURL = this.uriInfo.getAbsolutePathBuilder().path(id).build();
		return Response.created(nuevaURL).build();
	}
	
	@PUT
	@Path("/{id}/puntosinteres")
	@RolesAllowed("PROPIETARIO_ESPACIOS")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updatePto(@PathParam("id") String id, PuntosDeInteresDto pto ) 
			throws RepositorioException, EntidadNoEncontrada {
		LinkedList<PuntoDeInteres> puntos =  new LinkedList<>();
		for (PuntoDeInteresDto pt : pto.getPto()) {
			PuntoDeInteres p = new PuntoDeInteres
					(pt.getNombre(),pt.getDescripcion(),pt.getDistancia(),pt.getUrlAWikipedia());
			puntos.add(p);
		}
		servicio.asignarPuntosDeInteres(id, puntos);


		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	@PATCH
	@Path("/{id}")
	@RolesAllowed("PROPIETARIO_ESPACIOS")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateEspacioFisico(@PathParam("id") String id, UpdateEspacioFisicoDto dto) 
			throws RepositorioException, EntidadNoEncontrada {
		
		servicio.modificarEspacioFisico(id, dto.getNombre(),  dto.getCapacidad(), dto.getDescripcion());
		
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	@PUT
	@Path("/{id}/estado")
	@RolesAllowed("PROPIETARIO_ESPACIOS")
	public Response updateEstado(@PathParam("id") String id, @FormParam("estado") String estado) 
			throws RepositorioException, EntidadNoEncontrada {
		if ("cerrado".equals(estado)) {
			servicio.darDeBajaEspacioFisico(id);
		} else if ("activo".equals(estado)){
			servicio.activarEspacioFisico(id);
		}

		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	@GET
	@Path("/libres")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"USUARIO", "PROPIETARIO_ESPACIOS"})
	public Response getListadoEspaciosLibres(@QueryParam("fechaInicio") String fechaInicio,
								  @QueryParam("fechaFin")    String fechaFin,
								  @QueryParam("capacidad") int capacidad) throws RepositorioException, EntidadNoEncontrada {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

	    LocalDateTime fechaInicioParseada = LocalDateTime.parse(fechaInicio.trim(), formatter);
	    LocalDateTime fechaFinalParseada = LocalDateTime.parse(fechaFin.trim(), formatter);
		 
		List<EspacioFisico> espaciosLibres = servicio.buscarEspaciosFisicosLibres(fechaInicioParseada, fechaFinalParseada, capacidad);
		
		LinkedList<EspacioFisicoExtendido> extendido = new LinkedList<>();
		for (EspacioFisico es : espaciosLibres) {
			EspacioFisicoExtendido espacioFisicoExtendido = new EspacioFisicoExtendido();
			espacioFisicoExtendido.setEs(es);

			String id = es.getId();
			URI nuevaURL = this.uriInfo.getAbsolutePathBuilder().path(id).build();
			espacioFisicoExtendido.setUrl(nuevaURL.toString());

			extendido.add(espacioFisicoExtendido);
		}
		Listado listado = new Listado();
		listado.setEspacio(extendido);
		return Response.ok(listado).build();
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"USUARIO", "PROPIETARIO_ESPACIOS"})
	public Response buscarEspaciosPorPropietario(@QueryParam("propietario") String propietario) 
	        throws RepositorioException {

	    List<EspacioFisico> espacios = servicio.obtenerEspaciosPorPropietario(propietario); 
	    
	    List<EspacioFisicoExtendido> extendido = new LinkedList<>();
	    for (EspacioFisico es : espacios) {
	        EspacioFisicoExtendido espacioExtendido = new EspacioFisicoExtendido();
	        espacioExtendido.setEs(es);
	        
	        URI urlRecurso = this.uriInfo.getAbsolutePathBuilder().path(es.getId()).build();
	        espacioExtendido.setUrl(urlRecurso.toString());
	        
	        extendido.add(espacioExtendido);
	    }

	    Listado listado = new Listado();
	    listado.setEspacio(extendido);

	    return Response.ok(listado).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"USUARIO", "PROPIETARIO_ESPACIOS"})
	public Response recuperarEspacioFisico(@PathParam("id") String id)
	        throws RepositorioException, EntidadNoEncontrada {

	    EspacioFisico espacio = servicio.recuperarEspacioFisico(id);

	    EspacioFisicoDto dto = new EspacioFisicoDto();
	    dto.setId(espacio.getId());
	    dto.setNombre(espacio.getNombre());
	    dto.setPropietario(espacio.getPropietario());
	    dto.setCapacidad(espacio.getCapacidad());
	    dto.setDireccion(espacio.getDireccion());
	    dto.setLatitud(espacio.getLatitud());
	    dto.setLongitud(espacio.getLongitud());
	    dto.setDescripcion(espacio.getDescripcion());
	    dto.setEstado(espacio.getEstado().name());

	    List<PuntoDeInteresDto> listaPuntosDto = new ArrayList<>();
	    for (PuntoDeInteres p : espacio.getPuntosDeInteres()) {
	        PuntoDeInteresDto pdto = new PuntoDeInteresDto();
	        pdto.setNombre(p.getNombre());
	        pdto.setDescripcion(p.getDescripcion());
	        pdto.setDistancia(p.getDistancia());
	        pdto.setUrlAWikipedia(p.getUrlAWikipedia());
	        listaPuntosDto.add(pdto);
	    }
	    dto.setPuntosDeInteres(listaPuntosDto);

	    return Response.status(Response.Status.OK).entity(dto).build();
	}

}
