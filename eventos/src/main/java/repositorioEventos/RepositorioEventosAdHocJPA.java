package repositorioEventos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import dominio.Evento;
import utils.EntityManagerHelper;

public class RepositorioEventosAdHocJPA extends RepositorioEventosJPA implements RepositorioEventosAdHoc {

	@Override
	public List<Evento> getEventosDelMes(int mes, int año) {
		 EntityManager em = EntityManagerHelper.getEntityManager();

		 String queryString = "SELECT e " +
                 "FROM Evento e " +
                 "JOIN e.ocupacion o " +
                 "WHERE FUNCTION('MONTH', o.fechaInicio) = :mes " +
                 "AND FUNCTION('YEAR', o.fechaInicio) = :año";

		 TypedQuery<Evento> query = em.createQuery(queryString, Evento.class);
		 query.setParameter("mes", mes);
		 query.setParameter("año", año);

		 return query.getResultList();
	}
}
