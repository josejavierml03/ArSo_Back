package espacioFisico.auth;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class PruebasAuth {
	public static void main(String[] args) {
		
		// eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVc3VhcmlvIiwiZXhwIjoxNzQ1MjU4Mjg3fQ.JBBtWfirpfkuz2UhiifRwVxcbneaJmmdf_TYbeCLqIs
		
		Map<String, Object> claims = new HashMap<String, Object>(); 
	
		claims.put("sub", "Usuario");
	
		Date caducidad = Date.from(Instant.now().plusSeconds(3600));
		
		String token = Jwts.builder()
				.setClaims(claims).signWith(SignatureAlgorithm.HS256, "secreto")
				.setExpiration(caducidad).compact();
		
		System.out.println(token);
		
		 Claims claims2 = Jwts.parser()
				 .setSigningKey("secreto")
				 .parseClaimsJws(token)
				 .getBody();
		 
		 System.out.println(claims2.getSubject());
	}
}
