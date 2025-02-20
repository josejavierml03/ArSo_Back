package web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import servicioEventos.IServicioEspacios;
import dto.EspacioFisicoDTO;
import servicio.FactoriaServicios;

@SuppressWarnings("serial")
@Named("espaciosWeb")
@ViewScoped
public class EspaciosWeb implements Serializable {
	
	private IServicioEspacios servicioEspacios;
    private List<EspacioFisicoDTO> espacios;
    private String propietario;
    private boolean mostrarModalError;
    
    public EspaciosWeb() {
    	servicioEspacios = FactoriaServicios.getServicio(IServicioEspacios.class);
    }
    
    @PostConstruct
    public void init() {
        propietario = FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap().get("propietario");


        if (propietario != null && !propietario.isEmpty()) {
            cargarEspacios();
        } else {
            espacios = new ArrayList<>();
        }
    }

    public void cargarEspacios() {
        try {
            espacios = servicioEspacios.obtenerEspaciosPorPropietario(propietario);
            if (espacios == null) {
                espacios = new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar espacios: " + e.getMessage());
        }
    }
    
    public void darDeBaja(EspacioFisicoDTO espacioDTO) {
        try {
        	setMostrarModalError(false);
            servicioEspacios.darDeBajaEspacioFisico(espacioDTO.getId());
            cargarEspacios();	
        } catch (Exception e) {
            e.printStackTrace();
            setMostrarModalError(true); 
        }
    }


    public void activar(EspacioFisicoDTO espacioDTO) {
        try {
        	setMostrarModalError(false);
            servicioEspacios.activarEspacioFisico(espacioDTO.getId());
            cargarEspacios();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al activar el espacio: " + e.getMessage());
        }
    }


    public List<EspacioFisicoDTO> getEspacios() {
        return espacios;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getPropietario() {
        return propietario;
    }

	public boolean isMostrarModalError() {
		return mostrarModalError;
	}

	public void setMostrarModalError(boolean mostrarModalError) {
		this.mostrarModalError = mostrarModalError;
	}

}
