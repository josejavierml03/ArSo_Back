package repositorioEventos;

import dominio.Evento;
import repositorio.RepositorioJPA;

public class RepositorioEventosJPA extends RepositorioJPA<Evento>{

	@Override
	public Class<Evento> getClase() {
		return Evento.class;
	}

}