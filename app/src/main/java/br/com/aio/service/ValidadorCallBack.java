package br.com.aio.service;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import br.com.aio.exception.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by elton on 11/10/2017.
 */

public class ValidadorCallBack {
    //PROD
    //public static final String API_URL = "http://api.sme.fortaleza.ce.gov.br/apisme";
    //HOM
//    public static final String API_URL = "http://172.23.7.125:8080/apisme";
//    LOCAL ip do compuadopr que esta o servidor localhost quando conectado com o celular
    public static final String API_URL = "http://192.168.2.9:8080/allinoneserver";

    protected RestAdapter restAdapter;

    public Context getCtx() {
        return ctx;
    }

    protected Context ctx;

    public ValidadorCallBack(Context context) {
        this.ctx = context;
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                Log.i("REQUEST", request.toString());
            }
        };

        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss").excludeFieldsWithoutExposeAnnotation().create();
        restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API_URL)
                .setErrorHandler(new ErrorHandler(ctx))
                .setConverter(new GsonConverter(gson))
                .setClient(new OkClient(okHttpClient))
                .setRequestInterceptor(requestInterceptor)

                .build();
    }

    public enum Status {
        SUCESSO(200),
        CREATED(201),
        MUDAR(202),
        DADOS_INVALIDOS(400),
        TOKEN_INVALIDO(401),
        ACESSO_NEGADO(403),
        NAO_ENCONTRADO(404),
        DADOS_DUPLICADOS(409),
        ERROR_SERVIDOR(500);

        private int status;

        Status(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }
        public static boolean contemStatus(int code){
            boolean toReturn = false;
            for(Status status : values()){
                if(status.ordinal() == code){
                    toReturn = true;
                    break;
                }
            }
            return toReturn;
        }
    }
}
