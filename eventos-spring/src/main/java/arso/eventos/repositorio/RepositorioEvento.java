package arso.eventos.repositorio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import arso.eventos.modelo.Evento;

@NoRepositoryBean
public interface RepositorioEvento	
				extends CrudRepository<Evento, String> {
	
	public List<Evento> getEventosDelMes(int mes, int año);
}
