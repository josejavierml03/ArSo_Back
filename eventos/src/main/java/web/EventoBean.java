package web;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dto.EspacioFisicoDTO;
import servicioEventos.IServicioEspacios;
import servicioEventos.IServicioEventos;
import servicio.FactoriaServicios;
import dominio.Categoria;
import repositorio.RepositorioException;
import repositorio.EntidadNoEncontrada;

@SuppressWarnings("serial")
@Named("eventoBean")
@ViewScoped
public class EventoBean implements Serializable {

    private IServicioEspacios servicioEspacios;
    private IServicioEventos servicioEventos;
    private List<EspacioFisicoDTO> espaciosDisponibles;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Integer capacidadMinima;
    private EspacioFisicoDTO espacioSeleccionado;

    private String nombreEvento;
    private String descripcionEvento;
    private String organizador;
    private Categoria categoria;
    private Integer plazas;

    @Inject
    private FacesContext facesContext;

    public EventoBean() {
        servicioEspacios = FactoriaServicios.getServicio(IServicioEspacios.class);
        servicioEventos = FactoriaServicios.getServicio(IServicioEventos.class);
    }

    @PostConstruct
    public void init() {
        espaciosDisponibles = new ArrayList<>();
    }

    public void buscarEspaciosDisponibles() {
        if (fechaInicio != null && fechaFin != null && fechaInicio.isAfter(fechaFin)) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error en las fechas", "La fecha de inicio no puede ser posterior a la fecha de fin."));
            return;
        }

        try {
            espaciosDisponibles = servicioEspacios.buscarEspaciosFisicosLibres(fechaInicio, fechaFin, capacidadMinima);
            if (espaciosDisponibles == null || espaciosDisponibles.isEmpty()) {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Sin resultados", "No se encontraron espacios disponibles para los criterios dados."));
            }
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", "Ocurrió un error al buscar los espacios."));
        }
    }

    public void seleccionarEspacio(EspacioFisicoDTO espacio) {
        this.espacioSeleccionado = espacio;
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Espacio seleccionado", "Has seleccionado el espacio: " + espacio.getNombre()));
    }

    public void crearEvento() {
        if (espacioSeleccionado == null || nombreEvento == null || nombreEvento.isEmpty() || fechaInicio == null || fechaFin == null) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", "Todos los campos son requeridos para crear el evento."));
            return;
        }

        try {
            String idEvento = servicioEventos.altaEvento(nombreEvento, descripcionEvento, organizador, categoria, fechaInicio, fechaFin, plazas, espacioSeleccionado.getId());
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Evento creado", "El evento ha sido creado con éxito con ID: " + idEvento));
            resetFormulario();
        } catch (RepositorioException | EntidadNoEncontrada e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al crear el evento", "Ocurrió un error al crear el evento."));
            resetFormulario();
        }
    }
    
    private void resetFormulario() {
        fechaInicio = null;
        fechaFin = null;
        capacidadMinima = null;
        espaciosDisponibles = new ArrayList<>();
        espacioSeleccionado = null;
        nombreEvento = null;
        descripcionEvento = null;
        organizador = null;
        categoria = null;
        plazas = null;
    }
    
    public List<Categoria> getCategoriasDisponibles() {
        return Arrays.asList(Categoria.values());
    }


    public List<EspacioFisicoDTO> getEspaciosDisponibles() {
        return espaciosDisponibles;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getCapacidadMinima() {
        return capacidadMinima;
    }

    public void setCapacidadMinima(Integer capacidadMinima) {
        this.capacidadMinima = capacidadMinima;
    }

    public EspacioFisicoDTO getEspacioSeleccionado() {
        return espacioSeleccionado;
    }

    public void setEspacioSeleccionado(EspacioFisicoDTO espacioSeleccionado) {
        this.espacioSeleccionado = espacioSeleccionado;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getDescripcionEvento() {
        return descripcionEvento;
    }

    public void setDescripcionEvento(String descripcionEvento) {
        this.descripcionEvento = descripcionEvento;
    }

    public String getOrganizador() {
        return organizador;
    }

    public void setOrganizador(String organizador) {
        this.organizador = organizador;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Integer getPlazas() {
        return plazas;
    }

    public void setPlazas(Integer plazas) {
        this.plazas = plazas;
    }
}
