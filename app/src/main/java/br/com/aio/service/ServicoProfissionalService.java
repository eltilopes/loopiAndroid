package br.com.aio.service;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import br.com.aio.R;
import br.com.aio.endpoint.ServicoProfissionalEndPoint;
import br.com.aio.entity.Filtro;
import br.com.aio.entity.GoogleDirectionsResponse;
import br.com.aio.entity.ServicoCard;
import br.com.aio.entity.ServicoProfissional;
import br.com.aio.utils.DirectionUtils;
import br.com.aio.utils.FiltroUtils;
import br.com.aio.utils.JsonUtils;
import retrofit.client.Response;

/**
 * Created by elton on 11/10/2017.
 */

public class ServicoProfissionalService extends ValidadorCallBack {

    public static ServicoProfissionalEndPoint service;

    private static boolean multiServicos = false;
    public ServicoProfissionalService(Context context) {
        super(context);
        service = restAdapter.create(ServicoProfissionalEndPoint.class);
    }

    public List<ServicoCard>  salvarServicoProfissional(ServicoProfissional servicoProfissional) {
        Response response = service.salvarServicoProfissional(servicoProfissional);
        JsonUtils<List<ServicoCard>> json = new JsonUtils<List<ServicoCard>>();
        List<ServicoCard> servicos = json.converteObject(response, new TypeToken<List<ServicoCard>>() {
        }.getType());
        return servicos;

    }

    public List<ServicoCard> getServicoCardPorFiltro(Filtro filtro) {/*
        String token = UsuarioSharedUtils.getElementoSalvo(ctx, UsuarioSharedUtils.Preferences.PREFERENCES_TOKEN);
        String login = UsuarioSharedUtils.getElementoSalvo(ctx, UsuarioSharedUtils.Preferences.PREFERENCES_LOGIN);*/
        Response response = service.getServicoCardPorFiltro( filtro);
        JsonUtils<List<ServicoCard>> json = new JsonUtils<List<ServicoCard>>();
        List<ServicoCard> servicos = json.converteObject(response, new TypeToken<List<ServicoCard>>() {
        }.getType());
        servicos = servicos == null ? new ArrayList<ServicoCard>() : servicos;
        /*Gson gson = new Gson();
        List<ServicoCard> servicos = gson.fromJson(SERVICOS, new TypeToken<List<ServicoCard>>() {}.getType());*/
        return prepareServicoCards(servicos,getCtx(), filtro );
    }

    private static List<ServicoCard> prepareServicoCards(List<ServicoCard> servicos, Context context, Filtro filtro) {

        if(!servicos.isEmpty()) {
            DirectionUtils directionUtils = new DirectionUtils(context);
            GoogleDirectionsResponse googleDirectionsResponse;
            for (ServicoCard sc : servicos) {
                googleDirectionsResponse = directionUtils.getGoogleDirectionsResponse(filtro.getMinhaLatLng(), new LatLng(sc.getLatitude(), sc.getLongitude()));
                sc.setDistancia(googleDirectionsResponse != null ? googleDirectionsResponse.getDistance() : context.getString(R.string.distancia_nao_calculada));
                sc.setDuracao(googleDirectionsResponse != null ? context.getString(R.string.em_ate) + " " + googleDirectionsResponse.getDuration() : context.getString(R.string.tempo_nao_calculado));
                sc.setDistanciaMetros(googleDirectionsResponse != null ? googleDirectionsResponse.getDistanceMeters() : 0);
                sc.setQuantidadeFavorito(sc.getId().intValue());
                multiServicos = !multiServicos;
            }
            servicos = FiltroUtils.ordenarServicosPorDistancia(servicos, filtro);
        }
        return servicos;
    }


}
