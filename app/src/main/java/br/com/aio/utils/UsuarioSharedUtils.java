package br.com.aio.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import br.com.aio.entity.Role;
import br.com.aio.entity.UsuarioSession;

/**
 * Created by elton on 11/10/2017.
 */

public class UsuarioSharedUtils {

    public static boolean isLogado(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Preferences.PREFERENCES_LOCAL.preferencia, Context.MODE_PRIVATE);
        String senha = sharedPreferences.getString(Preferences.PREFERENCES_PASSWORD.preferencia, "");
        return !(senha == null || "".equals(senha));
    }

    public enum Preferences {
        PREFERENCES_LOCAL("br.gov.ce.fortaleza.sme.smemobile.user.profile"),
        PREFERENCES_NAME("userName"),
        PREFERENCES_CPF("userCpf"),
        PREFERENCES_ID_GLPI("idGlpi"),
        PREFERENCES_EMAIL("userEmail"),
        PREFERENCES_LOGIN("userLogin"),
        PREFERENCES_PASSWORD("userPassword"),
        PREFERENCES_NOME("userNome"),
        PREFERENCES_TOKEN("userToken"),
        PREFERENCES_REFRESH_TOKEN("userRefreshToken"),
        PREFERENCES_REG_ID("regId"),
        PREFERENCES_ID("id"),
        PREFERENCES_ROLE("userRoles");
        private String preferencia;

        Preferences(String preferencia) {
            this.preferencia = preferencia;
        }

        public String getPreferencia() {
            return preferencia;
        }
    }

    public static UsuarioSession getUsuarioShared(Context context){
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences(Preferences.PREFERENCES_LOCAL.preferencia, Context.MODE_PRIVATE);
        UsuarioSession usuario = new UsuarioSession();
        usuario.setLogin(sharedPreferences.getString(Preferences.PREFERENCES_LOGIN.preferencia, ""));
        usuario.setSenha(sharedPreferences.getString(Preferences.PREFERENCES_PASSWORD.preferencia, ""));
        usuario.setNome(sharedPreferences.getString(Preferences.PREFERENCES_NOME.preferencia, ""));
        usuario.setCpf(sharedPreferences.getString(Preferences.PREFERENCES_CPF.preferencia, ""));
        usuario.setToken(sharedPreferences.getString(Preferences.PREFERENCES_TOKEN.preferencia, ""));

        JsonParser parser = new JsonParser();
        String preferencias = sharedPreferences.getString(Preferences.PREFERENCES_ROLE.preferencia, "");
        if("".equals(preferencias))
            return new UsuarioSession();
        JsonArray jArray = parser.parse(preferencias).getAsJsonArray();
        List<Role> roles = new ArrayList<Role>();
        for (JsonElement obj : jArray)
            roles.add(gson.fromJson( obj , Role.class));

        usuario.setRoles(roles);
        return usuario;
    }

    public static String getElementoSalvo(Context context, Preferences preferencia){
        return getElementoSalvo(context, preferencia, "");
    }

    public static String getElementoSalvo(Context context, Preferences preferencia, String valorPadrao){
        if(context == null){
            return "";
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(Preferences.PREFERENCES_LOCAL.preferencia, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferencia.preferencia, valorPadrao);
    }

    public static void setUsuarioShared(UsuarioSession usuario, Context context){
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences(Preferences.PREFERENCES_LOCAL.preferencia, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Preferences.PREFERENCES_LOGIN.preferencia, usuario.getLogin());
        if(usuario.getId() != null)
            editor.putString(Preferences.PREFERENCES_ID.preferencia, usuario.getId().toString());


        editor.putString(Preferences.PREFERENCES_PASSWORD.preferencia, usuario.getSenha());
        editor.putString(Preferences.PREFERENCES_NOME.preferencia, usuario.getNome());
        editor.putString(Preferences.PREFERENCES_TOKEN.preferencia, usuario.getToken());
        editor.putString(Preferences.PREFERENCES_ROLE.preferencia, gson.toJson(usuario.getRoles()));
        editor.putString(Preferences.PREFERENCES_ID_GLPI.preferencia, usuario.getIdUsuarioGlpi());
        editor.putString(Preferences.PREFERENCES_CPF.preferencia, usuario.getCpf());
        editor.commit();
    }

    public static void set(Context context, Preferences key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Preferences.PREFERENCES_LOCAL.preferencia, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key.preferencia, value);
        editor.commit();
    }

    public static UsuarioSession clearToken(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Preferences.PREFERENCES_LOCAL.preferencia, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Preferences.PREFERENCES_TOKEN.preferencia, null);
        editor.putString(Preferences.PREFERENCES_PASSWORD.preferencia, null);
//        editor.putString(Preferences.PREFERENCES_REG_ID.preferencia, null);
        editor.commit();

        UsuarioSharedUtils.clearDatabase();

        return new UsuarioSession();
    }

    public static void clearDatabase(){
       /* SQLiteDatabase db = ActiveAndroid.getDatabase();
        List<String> tables = new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT * FROM sqlite_master WHERE type='table';", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String tableName = cursor.getString(1);
            if (!tableName.equals("android_metadata") &&
                    !tableName.equals("sqlite_sequence")) {
                tables.add(tableName);
            }
            cursor.moveToNext();
        }
        cursor.close();

        db.execSQL("PRAGMA foreign_keys=OFF;");
        for (String tableName : tables)
            db.execSQL("DELETE FROM " + tableName);*/
    }
}

