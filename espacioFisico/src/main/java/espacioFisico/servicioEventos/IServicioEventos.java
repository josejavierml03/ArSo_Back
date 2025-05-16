package espacioFisico.servicioEventos;

import java.util.List;

import espacioFisico.rest.dto.EspacioLibreDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface IServicioEventos {

    @GET("{id}/ocupacion/activa")
    Call<Boolean> tieneOcupacionesActivas(@Path("id") String id);

    @GET("espacio/libre")
    Call<List<EspacioLibreDto>> obtenerEspaciosLibres(
        @Query("fechaInicio") String fechaInicio,
        @Query("fechaFin") String fechaFin,
        @Query("capacidad") int capacidad
    );
}