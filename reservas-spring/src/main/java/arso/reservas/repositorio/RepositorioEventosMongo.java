package arso.reservas.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;

import arso.reservas.modelo.Evento;


public interface RepositorioEventosMongo 
	extends RepositorioEventos, MongoRepository<Evento, String> {
	
}