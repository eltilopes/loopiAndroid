package br.com.aio.service;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import br.com.aio.endpoint.CategoriaEndPoint;
import br.com.aio.entity.Categoria;
import br.com.aio.utils.JsonUtils;
import br.com.aio.utils.UsuarioSharedUtils;
import retrofit.client.Response;

/**
 * Created by elton on 11/10/2017.
 */

public class CategoriaService extends ValidadorCallBack {

    public static CategoriaEndPoint service;

    public CategoriaService(Context context) {
        super(context);
        service = restAdapter.create(CategoriaEndPoint.class);
    }

    public List<Categoria> getCategorias() {
        String token = UsuarioSharedUtils.getElementoSalvo(ctx, UsuarioSharedUtils.Preferences.PREFERENCES_TOKEN);
        String login = UsuarioSharedUtils.getElementoSalvo(ctx, UsuarioSharedUtils.Preferences.PREFERENCES_LOGIN);
        Response response = service.getCategorias("Bearer " + token, login);
        JsonUtils<List<Categoria>> json = new JsonUtils<List<Categoria>>();
        List<Categoria> categorias = json.converteObject(response, new TypeToken<List<Categoria>>() {
        }.getType());
        return Categoria.prepareCategorias(categorias);
    }

}
