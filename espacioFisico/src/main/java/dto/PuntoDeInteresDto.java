package dto;

public class PuntoDeInteresDto {
	
	private String nombre;
	private String descripcion;
	private double distancia;
    private String urlAWikipedia;
    
    public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public double getDistancia() {
		return distancia;
	}
	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}
	public String getUrlAWikipedia() {
		return urlAWikipedia;
	}
	public void setUrlAWikipedia(String urlAWikipedia) {
		this.urlAWikipedia = urlAWikipedia;
	}

}
