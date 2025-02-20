package web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebFilter("*.xhtml")
public class SessionFilter extends HttpFilter implements Filter {
	
	public SessionFilter() {
        super();
    }
	
	@Override
    public void destroy() {
    }

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	        throws IOException, ServletException {
	    HttpServletRequest httpRequest = (HttpServletRequest) request;
	    HttpServletResponse httpResponse = (HttpServletResponse) response;
	    String uri = httpRequest.getRequestURI();

	    if (uri.endsWith("index.xhtml") || uri.contains("javax.faces.resource")) {
	        chain.doFilter(request, response);
	        return;
	    }

	    sincronizarSesionDesdeCookies(httpRequest);

	    String usuario = (String) httpRequest.getSession().getAttribute("usuario");
	    String rol = (String) httpRequest.getSession().getAttribute("rol");
	    if (usuario == null) {
	        httpResponse.sendRedirect(httpRequest.getContextPath() + "/");
	        return;
	    }
	    if (uri.endsWith("crearEvento.xhtml") && !"organizador".equals(rol)) {
	        httpResponse.sendRedirect(httpRequest.getContextPath() + "/espacios.xhtml?propietario="+usuario);
	        return;
	    } else if (uri.endsWith("espacios.xhtml") && !"propietario".equals(rol)) {
	        httpResponse.sendRedirect(httpRequest.getContextPath() + "/crearEvento.xhtml");
	        return;
	    }

	    chain.doFilter(request, response);
	}

	private void sincronizarSesionDesdeCookies(HttpServletRequest httpRequest) {
	    Cookie[] cookies = httpRequest.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("usuario".equals(cookie.getName())) {
	                httpRequest.getSession().setAttribute("usuario", cookie.getValue());
	            }
	            if ("rol".equals(cookie.getName())) {
	                httpRequest.getSession().setAttribute("rol", cookie.getValue());
	            }
	        }
	    }
	}
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }


}
