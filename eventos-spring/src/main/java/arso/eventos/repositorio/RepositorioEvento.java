package arso.eventos.repositorio;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import arso.eventos.modelo.Evento;

@NoRepositoryBean
public interface RepositorioEvento	
				extends PagingAndSortingRepository<Evento, String> {
	
	public List<Evento> getEventosDelMes(int mes, int año);
}
