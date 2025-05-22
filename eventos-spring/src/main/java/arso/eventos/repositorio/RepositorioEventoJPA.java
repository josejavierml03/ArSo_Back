package arso.eventos.repositorio;

import java.time.LocalDateTime;
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
		       "WHERE MONTH(o.fechaInicio) = :mes " +
		       "AND YEAR(o.fechaInicio) = :año")
	List<Evento> getEventosDelMes(@Param("mes") int mes, @Param("año") int año);
	
	@Query(value = "SELECT ef.id, ef.nombre " +
		    "FROM espacio_fisico ef " +
		    "WHERE ef.id NOT IN (" +
		    "  SELECT e.espacio_fisico_id " +
		    "  FROM evento e " +
		    "  WHERE e.fecha_inicio < :fechaFin AND e.fecha_fin > :fechaInicio" +
		    ") " +
		    "AND ef.capacidad >= :capacidadMin", nativeQuery = true)
	List<Object[]> findIdsEspaciosLibresEntre(@Param("fechaInicio") LocalDateTime fechaInicio,
		                                      @Param("fechaFin") LocalDateTime fechaFin,
		                                      @Param("capacidadMin") int capacidadMin);

}

