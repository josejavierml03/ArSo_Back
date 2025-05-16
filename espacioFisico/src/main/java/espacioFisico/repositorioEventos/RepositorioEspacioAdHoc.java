package espacioFisico.repositorioEventos;

import java.util.List;

import espacioFisico.dominio.EspacioFisico;

public interface RepositorioEspacioAdHoc {
	
	public List<EspacioFisico> buscarPorPropietario(String propietario) ;
}
