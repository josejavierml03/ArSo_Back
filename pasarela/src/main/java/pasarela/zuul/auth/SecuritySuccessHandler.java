package pasarela.zuul.auth;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


@Component
public class SecuritySuccessHandler implements AuthenticationSuccessHandler {
	private static final int JWT_TIEMPO_VALIDEZ = 3600;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {

		DefaultOAuth2User usuario = (DefaultOAuth2User) authentication.getPrincipal();
		Map<String, Object> claims = fetchUserInfo(usuario);

		if (claims != null) {
			String token = JwtUtils.generateToken(claims);

			Cookie cookie = new Cookie("jwt", token);
			cookie.setHttpOnly(true);
			cookie.setMaxAge(JWT_TIEMPO_VALIDEZ);
			cookie.setPath("/");
			response.addCookie(cookie);

			Map<String, Object> respuesta = new HashMap<>();
			respuesta.put("token", token);
			respuesta.put("identificador", claims.get("sub"));
			respuesta.put("nombreCompleto", claims.get("nombreCompleto"));
			respuesta.put("roles", claims.get("roles"));

			String redirectUrl = String.format(
		            "http://localhost:3001/oauth-success?token=%s&nombre=%s&roles=%s",
		            token,
		            claims.get("nombreCompleto"),
		            claims.get("roles")
		        );
		        response.sendRedirect(redirectUrl);
		} else {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Usuario de GitHub no autorizado");
		}
	}

	private Map<String, Object> fetchUserInfo(DefaultOAuth2User usuario) {
		HashMap<String, Object> claims = new HashMap<String, Object>();
		String login = (String) usuario.getAttributes().get("login");
		String name = (String) usuario.getAttributes().get("name");
        claims.put("sub", usuario.getAttributes().get("login"));
        claims.put("roles", "USUARIO");
        claims.put("nombreCompleto", name != null ? name : login);

        return claims;
	}
}
