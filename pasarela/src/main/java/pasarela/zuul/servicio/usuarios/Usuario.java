package pasarela.zuul.servicio.usuarios;

public class Usuario {
	
    private String identificador;
    private String nombreCompleto;
    private String password;
    private String rol;

    public Usuario(String identificador, String nombreCompleto, String password, String rol) {
        this.identificador = identificador;
        this.nombreCompleto = nombreCompleto;
        this.password = password;
        this.rol = rol;
    }

    public String getIdentificador() {
        return identificador;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getPassword() {
        return password;
    }

    public String getRol() {
        return rol;
    }
}
