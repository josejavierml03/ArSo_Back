package repositorioEventos;

import java.util.List;
import dominio.Evento;

public interface RepositorioEventosAdHoc {
	
	public List<Evento> getEventosDelMes(int mes, int año);
}
