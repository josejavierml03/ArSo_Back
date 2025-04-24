package auth;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import servicio.FactoriaServicios;
import servicioEventos.IServicioUsuarios;

@Path("auth")
public class ControladorAuth {

	// curl -X POST -H "Content-Type: application/x-www-form-urlencoded"  -d "username=ana&password=abcd" http://localhost:8080/api/auth/login
	
	// eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmEiLCJyb2xlcyI6IlBST1BJRVRBUklPX0VTUEFDSU9TIiwiZXhwIjoxNzQ1NDY0OTgwfQ.mKOVEtCHYY6PF4RvW471fRkrjCERCOZ94qgNFv4ob1c
	
	private IServicioUsuarios servicioUsuarios;

    public ControladorAuth() {
        this.servicioUsuarios = FactoriaServicios.getServicio(IServicioUsuarios.class);
    }
    
	@POST
	@Path("/login")
	public Response login(@FormParam("username") String username, @FormParam("password") String password) {

		Map<String, Object> claims = servicioUsuarios.verificarCredenciales(username, password);
		if (claims != null) {
			String token = JwtUtils.generateToken(claims);
			return Response.ok(token).build();
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Credenciales inválidas").build();
		}

	}

}
