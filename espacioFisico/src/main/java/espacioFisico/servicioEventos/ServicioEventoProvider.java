package espacioFisico.servicioEventos;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicioEventoProvider {
	
	private static final IServicioEventos client;

    static {
        Retrofit retrofit = new Retrofit.Builder()
        		.baseUrl("http://eventos:8081/eventos/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();

        client = retrofit.create(IServicioEventos.class);
    }

    public static IServicioEventos getClient() {
        return client;
    }

}
