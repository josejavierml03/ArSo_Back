package espacioFisico.auth;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Priority;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtTokenFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    @Context
    private HttpServletRequest servletRequest;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String path = requestContext.getUriInfo().getPath();

        if (resourceInfo.getResourceMethod().isAnnotationPresent(PermitAll.class)) {
            return;
        }

        if (path.equals("auth/login")) {
            return;
        }

        String user = servletRequest.getHeader("X-User");
        String rolesHeader = servletRequest.getHeader("X-Roles");

        if (user == null || rolesHeader == null) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .entity("Faltan cabeceras de autenticación (X-User o X-Roles)").build());
            return;
        }

        servletRequest.setAttribute("user", user);
        servletRequest.setAttribute("roles", rolesHeader);


        if (resourceInfo.getResourceMethod().isAnnotationPresent(RolesAllowed.class)) {
            Set<String> userRoles = new HashSet<>(Arrays.asList(rolesHeader.split(",")));
            List<String> allowedRoles = Arrays.asList(
                resourceInfo.getResourceMethod().getAnnotation(RolesAllowed.class).value()
            );

            boolean autorizado = userRoles.stream().anyMatch(allowedRoles::contains);
            if (!autorizado) {
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity("No tiene rol de acceso").build());
            }
        }
    }
}