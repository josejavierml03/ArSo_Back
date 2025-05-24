package pasarela.zuul.auth;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import pasarela.zuul.servicio.usuarios.ServicioUsuarios;

@RestController
@RequestMapping("/auth")
public class ControllerAuth {

	@Autowired
	private ServicioUsuarios servicioUsuarios;

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestParam String username, @RequestParam String password,
			HttpServletResponse response) {
		Map<String, Object> claims = verificarCredenciales(username, password);
		if (claims != null) {
			String token = JwtUtils.generateToken(claims);

			Cookie cookie = new Cookie("jwt", token);
			cookie.setHttpOnly(true);
			cookie.setPath("/");
			cookie.setMaxAge(3600);
			response.addCookie(cookie);

			Map<String, Object> respuesta = new HashMap<>();
			respuesta.put("token", token);
			respuesta.put("identificador", claims.get("sub"));
			respuesta.put("nombreCompleto", claims.get("nombreCompleto"));
			respuesta.put("roles", claims.get("roles"));

			return ResponseEntity.ok(respuesta);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	@PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", "");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }
	
	@GetMapping("/me")
	public ResponseEntity<?> me(HttpServletRequest request) {
	    Claims claims = (Claims) request.getAttribute("claims");

	    if (claims == null) {
	        String jwt = null;
	        Cookie[] cookies = request.getCookies();
	        if (cookies != null) {
	            for (Cookie c : cookies) {
	                if ("jwt".equals(c.getName())) {
	                    jwt = c.getValue();
	                    break;
	                }
	            }
	        }
	        if (jwt != null) {
	            try {
	                claims = JwtUtils.validateToken(jwt);
	            } catch (Exception e) {
	                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	            }
	        }
	    }

	    if (claims == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }

	    Map<String, Object> response = new HashMap<>();
	    response.put("nombreCompleto", claims.get("nombreCompleto"));
	    response.put("roles", claims.get("roles"));
	    return ResponseEntity.ok(response);
	}
	
	private Map<String, Object> verificarCredenciales(String username, String password) {

		return servicioUsuarios.verificarCredenciales(username, password);

	}
}
