package br.com.aio.endpoint;

import br.com.aio.model.Convite;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by elton on 11/10/2017.
 */
public interface ServicoProfissionalEndPoint {

    @POST("/servico/novo")
    Response solicitarConvite(@Body Convite convite);

}
