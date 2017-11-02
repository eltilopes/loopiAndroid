package br.com.aio.utils;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import br.com.aio.activity.AceitarServicoFirebaseActivity;
import br.com.aio.activity.ListagemActivity;
import br.com.aio.activity.SolicitarPedidoActivity;
import br.com.aio.entity.Categoria;
import br.com.aio.entity.Especialidade;
import br.com.aio.entity.Localizacao;
import br.com.aio.entity.Profissional;
import br.com.aio.entity.ServicoCard;
import br.com.aio.entity.SubCategoria;
import br.com.aio.entity.UsuarioSession;

import static br.com.aio.utils.BundleUtils.ACTIVITY_ACEITAR_SERVICO;
import static br.com.aio.utils.BundleUtils.ACTIVITY_ANTERIOR;
import static br.com.aio.utils.BundleUtils.ACTIVITY_SOLICITAR_PEDIDO;
import static br.com.aio.utils.BundleUtils.CADASTRO_PROFISSIONAL;
import static br.com.aio.utils.BundleUtils.CATEGORIAS;
import static br.com.aio.utils.BundleUtils.DEEP_LINK_FIREBASE;
import static br.com.aio.utils.BundleUtils.EM_ATENDIMENTO;
import static br.com.aio.utils.BundleUtils.ESPECIALIDADES;
import static br.com.aio.utils.BundleUtils.LOCALIZACAO_MAPA;
import static br.com.aio.utils.BundleUtils.PROFISSIONAL_CADASTRO;
import static br.com.aio.utils.BundleUtils.SERVICO_CARD;
import static br.com.aio.utils.BundleUtils.SUB_CATEGORIAS;
import static br.com.aio.utils.BundleUtils.USUARIO_SESSION;

/**
 * Created by elton on 19/09/2017.
 */

public class SessionUtils {

    private static Type listTypeCategoria = new TypeToken<ArrayList<Categoria>>(){}.getType();
    private static Type listTypeSubCategoria = new TypeToken<ArrayList<SubCategoria>>(){}.getType();
    private static Type listTypeEspecialidade = new TypeToken<ArrayList<Especialidade>>(){}.getType();

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
            case ACTIVITY_ACEITAR_SERVICO:
                activityAnterior = AceitarServicoFirebaseActivity.class;

        }
        return activityAnterior;
    }

    public static void setActivityAnterior(SharedPreferences mPreferences, String activityAnterior ){
        SharedPreferences.Editor prefsEditor = mPreferences.edit();
        prefsEditor.putString(ACTIVITY_ANTERIOR, activityAnterior);
        prefsEditor.commit();
    }

    public static void setDeepLinkFireBase(SharedPreferences mPreferences, String deepLink ){
        SharedPreferences.Editor prefsEditor = mPreferences.edit();
        prefsEditor.putString(DEEP_LINK_FIREBASE, deepLink);
        prefsEditor.commit();
    }

    public static String getDeepLinkFireBase(SharedPreferences mPreferences){
        return mPreferences.getString(DEEP_LINK_FIREBASE, "");
    }

    public static void setCadastroProfissional(SharedPreferences mPreferences ){
        SharedPreferences.Editor prefsEditor = mPreferences.edit();
        prefsEditor.putBoolean(CADASTRO_PROFISSIONAL, true);
        prefsEditor.commit();
    }

    public static Boolean isEmAtendimento(SharedPreferences mPreferences){
        return mPreferences.getBoolean(CADASTRO_PROFISSIONAL, false);
    }

    public static void setEmAtendimento(SharedPreferences mPreferences ){
        SharedPreferences.Editor prefsEditor = mPreferences.edit();
        prefsEditor.putBoolean(EM_ATENDIMENTO, true);
        prefsEditor.commit();
    }

    public static Boolean getCadastroProfissional(SharedPreferences mPreferences){
        return mPreferences.getBoolean(CADASTRO_PROFISSIONAL, false);
    }

    public static void setUsuarioSession(SharedPreferences mPreferences, UsuarioSession usuarioSession) {
        SharedPreferences.Editor prefsEditor = mPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(usuarioSession);
        prefsEditor.putString(USUARIO_SESSION, json);
        prefsEditor.commit();
    }

    public static UsuarioSession getUsuarioSession(SharedPreferences mPreferences){
        Gson gson = new Gson();
        String json = mPreferences.getString(USUARIO_SESSION, null);
        return gson.fromJson(json, UsuarioSession.class);
    }

    public static void setProfissionalCadastro(SharedPreferences mPreferences, Profissional profissional) {
        SharedPreferences.Editor prefsEditor = mPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(profissional);
        prefsEditor.putString(PROFISSIONAL_CADASTRO, json);
        prefsEditor.commit();
    }

    public static Profissional getProfissionalCadastro(SharedPreferences mPreferences){
        Gson gson = new Gson();
        String json = mPreferences.getString(PROFISSIONAL_CADASTRO, null);
        return gson.fromJson(json, Profissional.class);
    }

    public static void setCategorias(SharedPreferences mPrefs, List<Categoria> categorias) {
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();;
        prefsEditor.putString(CATEGORIAS, gson.toJson(categorias, listTypeCategoria) );
        prefsEditor.commit();
    }

    public static List<Categoria> getCategorias(SharedPreferences mPrefs) {
        Gson gson = new Gson();
        String json = mPrefs.getString(CATEGORIAS, null);
        List<Categoria> lista = gson.fromJson(json, listTypeCategoria);
        return lista == null ? new ArrayList<Categoria>() : lista;
    }

    public static void setSubCategorias(SharedPreferences mPrefs, List<SubCategoria> subCategorias) {
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();;
        prefsEditor.putString(SUB_CATEGORIAS, gson.toJson(subCategorias, listTypeSubCategoria) );
        prefsEditor.commit();
    }

    public static List<SubCategoria> getSubCategorias(SharedPreferences mPrefs, final Categoria categoria) {
        Gson gson = new Gson();
        String json = mPrefs.getString(SUB_CATEGORIAS, null);
        List<SubCategoria> lista = gson.fromJson(json, listTypeSubCategoria);
        lista = lista == null ? new ArrayList<SubCategoria>() : lista;
        if(categoria!=null) {
            List<SubCategoria> listaFiltrada = new ArrayList<SubCategoria>();
            for (SubCategoria s : lista) {
                if (s.getCategoria().equals(categoria)) {
                    listaFiltrada.add(s);
                }
            }
            lista = listaFiltrada;
        }
        return lista;
    }

    public static void setEspecialidades(SharedPreferences mPrefs, List<Especialidade> especialidades) {
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();;
        prefsEditor.putString(ESPECIALIDADES, gson.toJson(especialidades, listTypeEspecialidade) );
        prefsEditor.commit();
    }

    public static List<Especialidade> getEspecialidades(SharedPreferences mPrefs, SubCategoria subCategoria) {
        Gson gson = new Gson();
        String json = mPrefs.getString(ESPECIALIDADES, null);
        List<Especialidade> lista = gson.fromJson(json, listTypeEspecialidade);
        lista = lista == null ? new ArrayList<Especialidade>() : lista;
        if(subCategoria!=null) {
            List<Especialidade> listaFiltrada = new ArrayList<Especialidade>();
            for (Especialidade e : lista) {
                if (e.getSubCategoria().equals(subCategoria)) {
                    listaFiltrada.add(e);
                }
            }
            lista = listaFiltrada;
        }
        return lista;
    }

}
