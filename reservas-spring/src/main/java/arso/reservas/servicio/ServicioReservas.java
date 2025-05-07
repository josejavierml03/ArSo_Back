package arso.reservas.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import arso.reservas.modelo.Evento;
import arso.reservas.modelo.Reserva;
import arso.reservas.repositorio.RepositorioEventos;
import arso.reservas.repositorio.RepositorioReservas;
import repositorio.EntidadNoEncontrada;

@Service
@Transactional
public class ServicioReservas implements IServicioReservas{

	private final RepositorioReservas repositorioReservas;
	private final RepositorioEventos repositorioEventos;

    @Autowired
    public ServicioReservas(RepositorioReservas repositorioReservas, RepositorioEventos repositorioEventos) {
        this.repositorioReservas = repositorioReservas;
        this.repositorioEventos = repositorioEventos;
    }

    @Override
    public String Reservar(String idEvento, String idUsuario, int plazas) throws EntidadNoEncontrada {
    	
    	if (idEvento == null || idEvento.isEmpty())
			throw new IllegalArgumentException("idEvento: no debe ser nulo ni vacio");

		if (idEvento == null || idEvento.isEmpty())
			throw new IllegalArgumentException("idUsuario: no debe ser nulo mi vacio");
		
		if (plazas < 0)
			throw new IllegalArgumentException("plazas: no debe ser un numero negativo");
		
		Optional<Evento> resultado = repositorioEventos.findById(idEvento);
		if (resultado.isPresent() == false)
			throw new EntidadNoEncontrada("No existe evento con id: " + idEvento);
		
		Evento evento = resultado.get();
		if (evento.isCancelado()) 
	        throw new IllegalStateException("El evento está cancelado");
	    
		if (evento.getPlazasDisponibles() < plazas) 
		    throw new IllegalStateException("No hay suficientes plazas disponibles");
		
        Reserva reserva = new Reserva(idUsuario, plazas, false, evento);
        
        evento.setPlazasDisponibles(evento.getPlazasDisponibles() - plazas);
        evento.getReservas().add(reserva);
        repositorioEventos.save(evento);

        String id = repositorioReservas.save(reserva).getId();
        
        return id;
    }

    @Override
    public Reserva getReserva(String id) throws EntidadNoEncontrada {
    	
    	if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
    	
    	Optional<Reserva> resultado = repositorioReservas.findById(id);
		if (resultado.isPresent() == false)
			throw new EntidadNoEncontrada("No existe reserva con id: " + id);
		else
			return resultado.get();
	}

	@Override
	public List<Reserva> getReservas(String id) throws EntidadNoEncontrada {
		
		if (id == null || id.isEmpty())
			throw new IllegalArgumentException("id: no debe ser nulo ni vacio");
		
		List<Reserva> reservas = repositorioReservas.findByEvento_Id(id);
		if (reservas.isEmpty()) {
		    throw new EntidadNoEncontrada("No hay reservas para el evento con id: " + id);
		}
		return reservas;
	}
	
	public Page<Reserva> getListadoPaginado(Pageable pageable, String id) {
		 
		 return repositorioReservas.findByEvento_Id(id, pageable);
	}



}
