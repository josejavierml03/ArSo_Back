package arso.eventos.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import arso.eventos.modelo.Evento;

@Repository
public interface RepositorioEventoJPA
			extends RepositorioEvento, JpaRepository<Evento, String> {
	@Override
    @Query("SELECT e FROM Evento e " +
           "JOIN e.ocupacion o " +
           "WHERE FUNCTION('MONTH', o.fechaInicio) = :mes " +
           "AND FUNCTION('YEAR', o.fechaInicio) = :año")
    List<Evento> getEventosDelMes(@Param("mes") int mes, @Param("año") int año);
}

