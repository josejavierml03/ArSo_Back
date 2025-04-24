package arso.eventos.repositorio;

import org.springframework.data.repository.CrudRepository;

import arso.eventos.modelo.EspacioFisico;

public interface RepositorioEspacio	
				extends CrudRepository<EspacioFisico, String> {
}
