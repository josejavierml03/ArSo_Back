package arso.reservas.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import arso.reservas.modelo.Reserva;
import arso.reservas.repositorio.RepositorioReservas;
import arso.reservas.repositorio.RepositorioReservasMongo;
import repositorio.EntidadNoEncontrada;

@Service
@Transactional
public class ServicioReservas implements IServicioReservas{

	private final RepositorioReservas repositorioReservas;

    @Autowired
    public ServicioReservas(RepositorioReservas repositorioReservas) {
        this.repositorioReservas = repositorioReservas;
    }

    @Override
    public String Reservar(String idEvento, String idUsuario, int plazas) {
    	
    	if (idEvento == null || idEvento.isEmpty())
			throw new IllegalArgumentException("idEvento: no debe ser nulo ni vacio");

		if (idEvento == null || idEvento.isEmpty())
			throw new IllegalArgumentException("idUsuario: no debe ser nulo mi vacio");
		
		if (plazas < 0)
			throw new IllegalArgumentException("plazas: no debe ser un numero negativo");
		
        Reserva reserva = new Reserva(idUsuario, plazas, false, null);

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
		
		return repositorioReservas.findByEvento_Id(id);
	}



}
