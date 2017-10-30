package br.com.aio.service;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import br.com.aio.endpoint.ProfissionalEndPoint;
import br.com.aio.entity.Profissional;
import br.com.aio.utils.JsonUtils;
import br.com.aio.utils.UsuarioSharedUtils;
import retrofit.client.Response;

/**
 * Created by elton on 11/10/2017.
 */

public class ProfissionalService extends ValidadorCallBack {

    public static ProfissionalEndPoint service;

    public ProfissionalService(Context context) {
        super(context);
        service = restAdapter.create(ProfissionalEndPoint.class);
    }

    public Profissional salvarProfissional(Profissional profissional) {
        String token = UsuarioSharedUtils.getElementoSalvo(ctx, UsuarioSharedUtils.Preferences.PREFERENCES_TOKEN);
        Response response = service.salvarProfissional(profissional);
        JsonUtils<Profissional> json = new JsonUtils();
        Profissional profissionalResponse = json.converteObject(response, new TypeToken<Profissional>() {
        }.getType());
        return profissionalResponse;

    }

}
