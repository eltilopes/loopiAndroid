package br.com.aio.service;

import android.content.Context;

import br.com.aio.endpoint.GcmEndPoint;
import br.com.aio.utils.UsuarioSharedUtils;
import retrofit.client.Response;

/**
 * Created by elton on 11/10/2017.
 */

public class GcmService extends ValidadorCallBack {

    public static GcmEndPoint service;

    public GcmService(Context context) {
        super(context);
        service = restAdapter.create(GcmEndPoint.class);
    }

    public void registrarApi(String regId) {
        String token = UsuarioSharedUtils.getElementoSalvo(ctx, UsuarioSharedUtils.Preferences.PREFERENCES_TOKEN);
        String idUsuarioGlpi = UsuarioSharedUtils.getElementoSalvo(ctx, UsuarioSharedUtils.Preferences.PREFERENCES_LOGIN);
        if (!"".equals(idUsuarioGlpi)) {
            Response response = service.criarChamado("Bearer " + token, regId, idUsuarioGlpi);
        }
    }

}
