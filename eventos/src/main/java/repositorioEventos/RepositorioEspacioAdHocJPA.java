package repositorioEventos;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.mysql.cj.Query;

import dominio.EspacioFisico;
import dominio.Estado;
import dominio.Ocupacion;
import utils.EntityManagerHelper;

public class RepositorioEspacioAdHocJPA extends RepositorioEspacioJPA implements RepositorioEspacioAdHoc{

	@Override
	public List<EspacioFisico> buscarEspaciosLibres(LocalDateTime fechaInicio, LocalDateTime fechaFin, int capacidadMinima) {
	    EntityManager em = EntityManagerHelper.getEntityManager();
	    
	    String queryString = "SELECT e " +
	                         "FROM EspacioFisico e " +
	                         "WHERE e.capacidad >= :capacidadMinima " +
	                         "AND e.estado = :estadoActivo " +
	                         "AND NOT EXISTS ( " +
	                         "    SELECT 1 " +
	                         "    FROM Evento ev " +
	                         "    WHERE ev.ocupacion.espacioFisico = e " +
	                         "    AND ev.ocupacion.fechaInicio < :fechaFin " +
	                         "    AND ev.ocupacion.fechaFin > :fechaInicio " +
	                         ")";
	    
	    TypedQuery<EspacioFisico> query = em.createQuery(queryString, EspacioFisico.class);
	    query.setParameter("capacidadMinima", capacidadMinima);
	    query.setParameter("fechaInicio", fechaInicio);
	    query.setParameter("fechaFin", fechaFin);
	    query.setParameter("estadoActivo", Estado.ACTIVO);
	    
	    return query.getResultList();
	}


	@Override
	public List<Ocupacion> espacioConOcupacionesActivas(String id) {
		EntityManager em = EntityManagerHelper.getEntityManager();
	    
	    String queryString = "SELECT e.ocupacion " +
	                         "FROM Evento e " +
	                         "WHERE e.ocupacion.espacioFisico.id = :id";
	    
	    TypedQuery<Ocupacion> query = em.createQuery(queryString, Ocupacion.class);
	    query.setParameter("id", id);
	    
	    List<Ocupacion> ocupacionesActivas = query.getResultList();
	    
	    return ocupacionesActivas;
	}
	
	@Override
	public List<EspacioFisico> buscarPorPropietario(String propietario) {
	    if (propietario == null || propietario.isEmpty()) {
	        throw new IllegalArgumentException("propietario: no debe ser nulo ni vacío");
	    }

	    EntityManager em = EntityManagerHelper.getEntityManager();
	    
	    String queryString = "SELECT e " +
	                         "FROM EspacioFisico e " +
	                         "WHERE e.propietario = :propietario";
	    
	    TypedQuery<EspacioFisico> query = em.createQuery(queryString, EspacioFisico.class);
	    query.setParameter("propietario", propietario);
	    
	    List<EspacioFisico> espacios = query.getResultList();
	    
	    return espacios;
	}
	

}
