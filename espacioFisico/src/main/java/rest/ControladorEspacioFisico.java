package rest;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

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

import dominio.PuntoDeInteres;
import dto.CrearEspacioFisicoDto;
import dto.PuntoDeInteresDto;
import dto.PuntosDeInteresDto;
import dto.UpdateEspacioFisicoDto;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;
import servicioEventos.IServicioEspacios;
import servicioEventos.PuntosDeInteres;

@Path("espacios")
public class ControladorEspacioFisico {
	
	private IServicioEspacios servicio =  FactoriaServicios.getServicio(IServicioEspacios.class); 
	
	@Context
	private UriInfo uriInfo;
	
	@Context
	private HttpServletRequest servletRequest;
	
	@POST
	@Consumes( MediaType.APPLICATION_JSON )
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
	@Consumes( MediaType.APPLICATION_JSON)
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
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateEspacioFisico(@PathParam("id") String id, UpdateEspacioFisicoDto dto) 
			throws RepositorioException, EntidadNoEncontrada {
		
		servicio.modificarEspacioFisico(dto.getId(), dto.getNombre(),  dto.getCapacidad(), dto.getDescripcion());
		
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	@PUT
	@Path("/{id}/estado")
	@Consumes( MediaType.APPLICATION_JSON)
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
	public Response getActividad(@QueryParam("fechaInicio") String fechaInicio,
								  @QueryParam("fechaFin")    String fechaFin,
								  @QueryParam("capacidadMin") int capacidadMin) throws Exception {
		
		//Hacer listado
		
		return null;


	}

}
