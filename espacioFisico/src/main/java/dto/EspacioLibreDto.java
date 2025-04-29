package dto;

public class EspacioLibreDto {
    private String id;
    private String nombre;
	private int capacidad;

    public EspacioLibreDto(String id, String nombre,int capacidad) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
    }
    
    public EspacioLibreDto() {
    }

    public String getId() { 
    	return id; 
    }
    
    public String getNombre() { 
    	return nombre; 
    }
    
    public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
}
