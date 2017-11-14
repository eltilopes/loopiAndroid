package br.com.aio.endpoint;

import br.com.aio.entity.Filtro;
import br.com.aio.entity.ServicoProfissional;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by elton on 11/10/2017.
 */
public interface ServicoProfissionalEndPoint {

    @POST("/servico/listar")
    Response getServicoCardPorFiltro(@Body Filtro filtro);

    @POST("/servico/novo")
    Response salvarServicoProfissional(@Body ServicoProfissional servicoProfissional);
}
