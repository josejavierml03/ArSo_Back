package arso.reservas.repositorio;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import arso.reservas.modelo.Reserva;

@NoRepositoryBean
public interface RepositorioReservas extends PagingAndSortingRepository<Reserva, String> {
	
	List<Reserva> findByEvento_Id(String eventoId);
	
	Page<Reserva> findByEvento_Id(String eventoId, Pageable pageable);
	
	List<Reserva> findByIdUsuario(String idUsuario);
	

}
