package arso.eventos;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import arso.eventos.modelo.Categoria;
import arso.eventos.modelo.EspacioFisico;
import arso.eventos.modelo.Evento;
import arso.eventos.repositorio.RepositorioEspacio;
import arso.eventos.repositorio.RepositorioEvento;

public class PruebaRepositorio {
	
public static void main(String[] args) {
		
		ConfigurableApplicationContext contexto = SpringApplication.run(EventosSpringApplication.class, args);

		RepositorioEvento repositorioEvento = contexto.getBean(RepositorioEvento.class);
        RepositorioEspacio repositorioEspacio = contexto.getBean(RepositorioEspacio.class);

        EspacioFisico espacio = new EspacioFisico(
                "Auditorio Central",
                "Calle Principal 123",
                "Auditorio para conferencias",
                300
        );
        
        espacio = repositorioEspacio.save(espacio);

        Evento evento = new Evento(
                "Charla de Inteligencia Artificial",
                "Introducción a modelos generativos con IA",
                "Facultad de Ingeniería",
                150,
                Categoria.ACADEMICOS, 
                LocalDateTime.of(2025, 6, 5, 18, 0),
                LocalDateTime.of(2025, 6, 5, 20, 0),
                espacio
        );

        String idEvento = repositorioEvento.save(evento).getId();

        System.out.println("Evento creado con ID: " + idEvento);
        contexto.close();
	}

}
