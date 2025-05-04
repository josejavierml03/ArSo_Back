package arso.reservas.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import arso.reservas.modelo.Evento;


@NoRepositoryBean
public interface RepositorioEventos extends CrudRepository<Evento, String> {
	
	

}
