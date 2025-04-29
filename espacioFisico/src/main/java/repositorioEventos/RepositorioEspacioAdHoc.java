package repositorioEventos;

import java.util.List;

import dominio.EspacioFisico;

public interface RepositorioEspacioAdHoc {
	
	public List<EspacioFisico> buscarPorPropietario(String propietario) ;
}
