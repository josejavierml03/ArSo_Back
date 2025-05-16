package espacioFisico.repositorioEventos;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import espacioFisico.dominio.EspacioFisico;
import espacioFisico.dominio.Estado;
import espacioFisico.utils.EntityManagerHelper;

public class RepositorioEspacioAdHocJPA extends RepositorioEspacioJPA implements RepositorioEspacioAdHoc{

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
