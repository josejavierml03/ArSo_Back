package rest;

import java.util.List;

import dominio.EspacioFisico;

public class Listado {
	
	public static class EspacioFisicoExtendido {
		private String url;
		private EspacioFisico es;
		
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public EspacioFisico getEs() {
			return es;
		}
		public void setEs(EspacioFisico es) {
			this.es = es;
		}

		
	}
	
	private List<EspacioFisicoExtendido> espacio;

	public List<EspacioFisicoExtendido> getEspacio() {
		return espacio;
	}

	public void setEspacio(List<EspacioFisicoExtendido> espacio) {
		this.espacio = espacio;
	}



}
