package br.com.aio.service;

import android.content.Context;

import br.com.aio.endpoint.ServicoProfissionalEndPoint;

/**
 * Created by elton on 11/10/2017.
 */

public class ServicoProfissionalService extends ValidadorCallBack {

    public static ServicoProfissionalEndPoint service;
    public static final String URL_GET_SERVICOS = ValidadorCallBack.API_URL + "/servico/listar";

    public ServicoProfissionalService(Context context) {
        super(context);
        service = restAdapter.create(ServicoProfissionalEndPoint.class);
    }

}
