package br.com.aio.endpoint;

import br.com.aio.entity.Profissional;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by elton on 11/10/2017.
 */
public interface ProfissionalEndPoint {

    @POST("/profissional/novo")
    Response salvarProfissional( @Body Profissional profissional);


}
