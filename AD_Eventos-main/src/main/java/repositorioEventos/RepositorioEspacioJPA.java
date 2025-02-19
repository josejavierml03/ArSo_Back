package repositorioEventos;

import dominio.EspacioFisico;
import repositorio.RepositorioJPA;

public class RepositorioEspacioJPA extends RepositorioJPA<EspacioFisico> {

	@Override
	public Class<EspacioFisico> getClase() {
		return EspacioFisico.class;
	}

}
