package espacioFisico.repositorioEventos;

import espacioFisico.dominio.EspacioFisico;
import espacioFisico.repositorio.RepositorioJPA;

public class RepositorioEspacioJPA extends RepositorioJPA<EspacioFisico> {

	@Override
	public Class<EspacioFisico> getClase() {
		return EspacioFisico.class;
	}

}
