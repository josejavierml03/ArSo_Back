package arso.reservas.repositorio;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import arso.reservas.modelo.Reserva;

@NoRepositoryBean
public interface RepositorioReservas extends PagingAndSortingRepository<Reserva, String> {
	
	List<Reserva> findByEvento_Id(String eventoId);
	

}
