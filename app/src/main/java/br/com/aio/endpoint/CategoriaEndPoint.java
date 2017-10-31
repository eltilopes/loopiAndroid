package br.com.aio.endpoint;

import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by elton on 11/10/2017.
 */
public interface CategoriaEndPoint {

    @GET("/categoria/listar")
    Response getCategorias(@Header("Authorization") String authorization,
                           @Query("login") String login);

}
