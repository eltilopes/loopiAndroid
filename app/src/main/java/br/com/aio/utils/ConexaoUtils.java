package br.com.aio.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by elton on 11/10/2017.
 */

public class ConexaoUtils {
    public static boolean isConexao(final Context cont){
        if(cont == null) return false;
        boolean conectado = false;
        ConnectivityManager conmag;
        conmag = (ConnectivityManager)cont.getSystemService(Context.CONNECTIVITY_SERVICE);
        conmag.getActiveNetworkInfo();
        NetworkInfo activeNetwork = conmag.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        NetworkInfo ni = conmag.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        //Verifica o WIFI
        if(conmag.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()){
            conectado = true;
        }
        //Verifica o 3G
        else if(ni != null  && ni.isConnected()){
            conectado = true;
        }
        else{
            conectado = false;
        }
        return conectado;
    }
}
