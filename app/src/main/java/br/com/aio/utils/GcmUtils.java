package br.com.aio.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

/**
 * Created by elton on 11/10/2017.
 */

public class GcmUtils {
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static String SERVER_ID = "AIzaSyBcU9bk64c7cOig4us1WMlO_o_k7Ococ6o";
    private static GoogleCloudMessaging gcm;

    public static boolean checkePlayService(Activity act) {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int resultCode = api.isGooglePlayServicesAvailable(act);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, act, PLAY_SERVICES_RESOLUTION_REQUEST);
            } else {
                Toast.makeText(act, "Sem suporte ao gcm", Toast.LENGTH_LONG).show();
            }
            return false;
        }
        return true;

    }


    public static String registerId(final Context act) {
        String msg = "";
        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(act);
            }
            //Registra qual id serve pode enviar mensagem
            String regId = gcm.register(SERVER_ID);
            Log.i("ID GCM", regId);
            SharedPreferences sharedPreferences = act.getSharedPreferences(UsuarioSharedUtils.Preferences.PREFERENCES_LOCAL.getPreferencia(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(UsuarioSharedUtils.Preferences.PREFERENCES_REG_ID.getPreferencia(), regId);
            editor.commit();
            msg = regId;
        } catch (IOException e) {
            Log.e("Script GCM", e.getMessage());
        }

        return msg;
    }
}
