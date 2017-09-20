package br.com.aio.utils;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import br.com.aio.activity.ListagemActivity;
import br.com.aio.activity.SolicitarPedidoActivity;
import br.com.aio.adapter.ServicoCard;
import br.com.aio.entity.Localizacao;

import static br.com.aio.utils.BundleUtils.ACTIVITY_ANTERIOR;
import static br.com.aio.utils.BundleUtils.ACTIVITY_SOLICITAR_PEDIDO;
import static br.com.aio.utils.BundleUtils.LOCALIZACAO_MAPA;
import static br.com.aio.utils.BundleUtils.SERVICO_CARD;

/**
 * Created by elton on 19/09/2017.
 */

public class SessionUtils {

    public static ServicoCard getServicoCard(SharedPreferences mPreferences){
        Gson gson = new Gson();
        String json = mPreferences.getString(SERVICO_CARD, null);
        return gson.fromJson(json, ServicoCard.class);
    }

    public static void setServicoCard(SharedPreferences mPreferences, ServicoCard servicoCard){
        SharedPreferences.Editor prefsEditor = mPreferences.edit();
        Gson gson = new Gson();;
        prefsEditor.putString(SERVICO_CARD, servicoCard!=null ? gson.toJson(servicoCard) : null);
        prefsEditor.commit();
    }

    public static Localizacao getLocalizacaoMapa(SharedPreferences mPreferences){
        Gson gson = new Gson();
        String json = mPreferences.getString(LOCALIZACAO_MAPA, null);
        return gson.fromJson(json, Localizacao.class);
    }

    public static void setLocalizacaoMapa(SharedPreferences mPreferences, Localizacao localizacao){
        SharedPreferences.Editor prefsEditor = mPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(localizacao);
        prefsEditor.putString(LOCALIZACAO_MAPA, json);
        prefsEditor.commit();
    }

    public static String getNomeActivityAnterior(SharedPreferences mPreferences){
        return mPreferences.getString(ACTIVITY_ANTERIOR, "");
    }

    public static Class getActivityAnterior(SharedPreferences mPreferences){
        String activity = mPreferences.getString(ACTIVITY_ANTERIOR, null);
        Class activityAnterior = ListagemActivity.class;
        switch (activity){
            case ACTIVITY_SOLICITAR_PEDIDO:
                activityAnterior = SolicitarPedidoActivity.class;
        }
        return activityAnterior;
    }

    public static void setActivityAnterior(SharedPreferences mPreferences, String activityAnterior ){
        SharedPreferences.Editor prefsEditor = mPreferences.edit();
        prefsEditor.putString(ACTIVITY_ANTERIOR, activityAnterior);
        prefsEditor.commit();
    }

}
