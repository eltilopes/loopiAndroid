package br.com.aio.service;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import br.com.aio.endpoint.EspecialidadeEndPoint;
import br.com.aio.entity.Especialidade;
import br.com.aio.utils.JsonUtils;
import br.com.aio.utils.UsuarioSharedUtils;
import retrofit.client.Response;

/**
 * Created by elton on 11/10/2017.
 */

public class EspecialidadeService extends ValidadorCallBack {

    public static EspecialidadeEndPoint service;

    public EspecialidadeService(Context context) {
        super(context);
        service = restAdapter.create(EspecialidadeEndPoint.class);
    }

    public List<Especialidade> getEspecialidades() {
        String token = UsuarioSharedUtils.getElementoSalvo(ctx, UsuarioSharedUtils.Preferences.PREFERENCES_TOKEN);
        String login = UsuarioSharedUtils.getElementoSalvo(ctx, UsuarioSharedUtils.Preferences.PREFERENCES_LOGIN);
        Response response = service.getEspecialidades("Bearer " + token, login);
        JsonUtils<List<Especialidade>> json = new JsonUtils<List<Especialidade>>();
        List<Especialidade> especialidades = json.converteObject(response, new TypeToken<List<Especialidade>>() {
        }.getType());
        return Especialidade.prepareEspecialidades(especialidades) ;
    }

}
