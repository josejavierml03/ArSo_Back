package arso.reservas.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;

import arso.reservas.modelo.Reserva;

public interface RepositorioReservasMongo 
	extends RepositorioReservas, MongoRepository<Reserva, String> {

}