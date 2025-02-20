package web;

import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private String rol;
    private String usuario;
    

    @PostConstruct
    public void init() {
        Cookie[] cookies = ((HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest()).getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("usuario".equals(cookie.getName())) {
                    this.usuario = cookie.getValue();
                }
                if ("rol".equals(cookie.getName())) {
                    this.rol = cookie.getValue();
                }
            }
        }
    }

    public void verificarSesion() {
        if (usuario != null && rol != null) {
            try {
                if ("propietario".equals(rol)) {
                    FacesContext.getCurrentInstance()
                            .getExternalContext()
                            .redirect("espacios.xhtml?propietario=" + usuario);
                } else if ("organizador".equals(rol)) {
                    FacesContext.getCurrentInstance()
                            .getExternalContext()
                            .redirect("crearEvento.xhtml");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String redirigir() {
        try {
            guardarSesionEnCookies();
            if ("propietario".equals(rol)) {
                FacesContext.getCurrentInstance()
                        .getExternalContext()
                        .redirect("espacios.xhtml?propietario=" + usuario);
            } else if ("organizador".equals(rol)) {
                FacesContext.getCurrentInstance()
                        .getExternalContext()
                        .redirect("crearEvento.xhtml");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void guardarSesionEnCookies() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();

        Cookie usuarioCookie = new Cookie("usuario", usuario);
        usuarioCookie.setMaxAge(60 * 60 * 24 * 7);
        usuarioCookie.setPath("/");

        Cookie rolCookie = new Cookie("rol", rol);
        rolCookie.setMaxAge(60 * 60 * 24 * 7);
        rolCookie.setPath("/");

        response.addCookie(usuarioCookie);
        response.addCookie(rolCookie);

        request.getSession().setAttribute("usuario", usuario);
        request.getSession().setAttribute("rol", rol);
    }


    public void cerrarSesion() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();

        Cookie usuarioCookie = new Cookie("usuario", null);
        usuarioCookie.setMaxAge(0);
        usuarioCookie.setPath("/");

        Cookie rolCookie = new Cookie("rol", null);
        rolCookie.setMaxAge(0);
        rolCookie.setPath("/");

        response.addCookie(usuarioCookie);
        response.addCookie(rolCookie);

        request.getSession().invalidate();

        try {
            facesContext.getExternalContext().redirect("index.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

