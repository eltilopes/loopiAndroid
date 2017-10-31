package br.com.aio.service;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import br.com.aio.endpoint.SubCategoriaEndPoint;
import br.com.aio.entity.SubCategoria;
import br.com.aio.utils.JsonUtils;
import br.com.aio.utils.UsuarioSharedUtils;
import retrofit.client.Response;

/**
 * Created by elton on 11/10/2017.
 */

public class SubCategoriaService extends ValidadorCallBack {

    public static SubCategoriaEndPoint service;

    public SubCategoriaService(Context context) {
        super(context);
        service = restAdapter.create(SubCategoriaEndPoint.class);
    }

    public List<SubCategoria> getSubCategorias() {
        String token = UsuarioSharedUtils.getElementoSalvo(ctx, UsuarioSharedUtils.Preferences.PREFERENCES_TOKEN);
        String login = UsuarioSharedUtils.getElementoSalvo(ctx, UsuarioSharedUtils.Preferences.PREFERENCES_LOGIN);
        Response response = service.getSubCategorias("Bearer " + token, login);
        JsonUtils<List<SubCategoria>> json = new JsonUtils<List<SubCategoria>>();
        List<SubCategoria> subCategorias = json.converteObject(response, new TypeToken<List<SubCategoria>>() {
        }.getType());
        return SubCategoria.prepareSubCategorias(subCategorias) ;
    }

}
