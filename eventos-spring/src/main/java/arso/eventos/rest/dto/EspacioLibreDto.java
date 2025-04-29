package arso.eventos.rest.dto;

public class EspacioLibreDto {
    private String id;
    private String nombre;

    public EspacioLibreDto(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() { 
    	return id; 
    }
    
    public String getNombre() { 
    	return nombre; 
    }
    
}
