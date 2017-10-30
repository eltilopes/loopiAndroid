package br.com.aio.service;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import br.com.aio.endpoint.ConviteEndPoint;
import br.com.aio.model.Convite;
import br.com.aio.utils.JsonUtils;
import retrofit.client.Response;

/**
 * Created by elton on 11/10/2017.
 */

public class ServicoProfissionalService extends ValidadorCallBack {

    public static ConviteEndPoint service;

    public ServicoProfissionalService(Context context) {
        super(context);
        service = restAdapter.create(ConviteEndPoint.class);
    }

    public Convite solicitarConvite(Convite convite) {
        Response response = service.solicitarConvite(convite);
        JsonUtils<Convite> json = new JsonUtils();
        Convite conviteResponse = json.converteObject(response, new TypeToken<Convite>() {
        }.getType());
        return conviteResponse;

    }

}
