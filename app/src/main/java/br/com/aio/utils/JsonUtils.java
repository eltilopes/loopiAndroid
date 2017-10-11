package br.com.aio.utils;

import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by bezerra.junior on 22/07/2015.
 */
public class JsonUtils<T> {

    ExclusionStrategy exclusionStrategy = new ExclusionStrategy() {

        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            return false;
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return clazz == Field.class || clazz == Method.class;
        }
    };

    public T converteObject(Response response, Type type){
        if(response == null || type == null || response.getBody() == null)
            return null;
        T toReturn = null;
        try{
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                    .serializeNulls()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            InputStream stream = response.getBody().in();
            int size = stream.available();
            byte [] data = new byte [size];
            stream.read(data, 0, size);
            String value = new String(data);
            toReturn = (T) gson.fromJson(value, type);
        }catch (Exception e){
            Log.e("Erro Json","Ocorreu um erro ao converter json", e);
        }
        return toReturn;
    }

    public static String getErroString(Response respon){
        if(respon == null)
            return "";
        try {
            String test =  new String(((TypedByteArray)respon.getBody()).getBytes());
            JSONObject jsonObject = new JSONObject(test);
            return jsonObject.getString("error_description");
        } catch (JSONException e) {
            Log.e("Erro Json","Ocorreu um erro ao converter json");
            e.printStackTrace();
        }
        return "";
    }
    public static String getElementoString(Response respon, String elemento){
        if(respon == null)
            return "";
        try {
            String test =  new String(((TypedByteArray)respon.getBody()).getBytes());
            JSONObject jsonObject = new JSONObject(test);
            return jsonObject.getString(elemento);
        } catch (JSONException e) {
            Log.e("Erro Json", "Ocorreu um erro ao converter json");
            e.printStackTrace();
        }
        return "";
    }

    public static String getResponseString(Response respon){
        if(respon == null)
            return "";
        try {
            String test =  new String(((TypedByteArray)respon.getBody()).getBytes());
            JSONObject jsonObject = new JSONObject(test);
            return jsonObject.getString("idUsuarioGlpi");
        } catch (JSONException e) {
            Log.e("Erro Json", "Ocorreu um erro ao converter json");
            e.printStackTrace();
        }
        return "";
    }
}
