package pasarela.zuul.servicio.usuarios;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
@Service
public class ServicioUsuarios implements IServicioUsuarios {
	private static final Map<String, Usuario> usuarios = new HashMap<>();

	static {
		usuarios.put("juan", new Usuario("juan", "Juan Pérez", "1234", "GESTOR_EVENTOS"));
		usuarios.put("ana", new Usuario("ana", "Ana Gómez", "abcd", "PROPIETARIO_ESPACIOS"));
		usuarios.put("lucia", new Usuario("lucia", "Lucía Martínez", "pass", "USUARIO"));
		usuarios.put("admin", new Usuario("admin", "Administrador Total", "admin123", "GESTOR_EVENTOS,PROPIETARIO_ESPACIOS")); 
	}

	@Override
	public Map<String, Object> verificarCredenciales(String username, String password) {
		Usuario usuario = usuarios.get(username);
		if (usuario != null && usuario.getPassword().equals(password)) {
			Map<String, Object> claims = new HashMap<>();
			claims.put("sub", usuario.getIdentificador());
			claims.put("nombreCompleto", usuario.getNombreCompleto());
			claims.put("roles", usuario.getRol());
			return claims;
		}
		return null;
	}
	
}
