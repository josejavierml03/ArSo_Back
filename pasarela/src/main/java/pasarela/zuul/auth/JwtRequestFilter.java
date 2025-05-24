package pasarela.zuul.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.netflix.zuul.context.RequestContext;
import io.jsonwebtoken.Claims;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String path = request.getRequestURI();
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        if (path.equals("/auth/login")) {
            chain.doFilter(request, response);
            return;
        }
        String jwt = null;

        String auth = request.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            jwt = auth.substring(7);
        }

        if (jwt == null && request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    jwt = cookie.getValue();
                }
            }
        }

        if (jwt != null) {
            try {
                Claims claims = JwtUtils.validateToken(jwt);
                String username = claims.getSubject();
                String[] roles = claims.get("roles", String.class).split(",");

                List<GrantedAuthority> authorities = new ArrayList<>();
                for (String rol : roles) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + rol));
                }

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authToken);

                RequestContext ctx = RequestContext.getCurrentContext();
                ctx.addZuulRequestHeader("X-User", username);
                ctx.addZuulRequestHeader("X-Roles", String.join(",", roles));
                
                request.setAttribute("claims", claims);

            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token JWT inválido");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}