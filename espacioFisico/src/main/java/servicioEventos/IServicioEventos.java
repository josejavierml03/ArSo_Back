package servicioEventos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import dto.EspaciosLibresRespuesta;


public interface IServicioEventos {

    @GET("{id}/ocupacion/activa")
    Call<Boolean> tieneOcupacionesActivas(@Path("id") String id);

    @GET("espacio/libre")
    Call<EspaciosLibresRespuesta> obtenerEspaciosLibres(
        @Query("fechaInicio") String fechaInicio,
        @Query("fechaFin") String fechaFin,
        @Query("capacidad") int capacidad
    );
}