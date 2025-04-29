package servicioEventos;

import java.util.HashMap;
import java.util.Map;

import auth.Usuario;

public class ServicioUsuarios implements IServicioUsuarios {
	 private static final Map<String, Usuario> usuarios = new HashMap<>();

	    static {
	        usuarios.put("juan", new Usuario("juan", "1234", "GESTOR_EVENTOS"));
	        usuarios.put("ana", new Usuario("ana", "abcd", "PROPIETARIO_ESPACIOS"));
	        usuarios.put("lucia", new Usuario("lucia", "pass", "USUARIO"));
	    }
	    

	    @Override
	    public Map<String, Object> verificarCredenciales(String username, String password) {
	        Usuario usuario = usuarios.get(username);
	        if (usuario != null && usuario.getPassword().equals(password)) {
	            HashMap<String, Object> claims = new HashMap<>();
	            claims.put("sub", usuario.getNombre());
	            claims.put("roles", usuario.getRol());
	            return claims;
	        }
	        return null;
	    }
}
