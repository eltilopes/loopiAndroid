package br.com.aio.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import br.com.aio.R;
import br.com.aio.entity.Localizacao;
import br.com.aio.utils.SessionUtils;

import static br.com.aio.utils.BundleUtils.PREFS_NAME;

/**
 * Created by elton on 15/11/2017.
 */

public class GoogleService extends Service implements LocationListener{

    private boolean isGPSEnable = false;
    private boolean isNetworkEnable = false;
    private LocationManager locationManager;
    private Location location;
    private Handler mHandler = new Handler();
    private Timer mTimer = null;
    private long interval = 1000;
    private long delay = 100;
    private float distance = 0;
    public static String receiver = "br.com.aio.service.receiver";
    private Intent intent;
    private SharedPreferences mPrefs;
    private Localizacao localizacao;
    private long minuto = 60 * 1000;

    public GoogleService() { }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPrefs = getSharedPreferences(PREFS_NAME, 0);
        mTimer = new Timer();
        mTimer.schedule(new TimerTaskToGetLocation(),delay, interval);
        intent = new Intent(receiver);
        localizacao = new Localizacao();
        localizacao.setDataLocalizacao(new Date(System.currentTimeMillis() - minuto*2));
//        fn_getlocation();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void getlocation(){
        locationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnable && !isNetworkEnable ){

        }else {
            if (isGPSEnable && tempoNovaLocalizacao()){
                location = null;
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,interval,distance,this);
                if (locationManager!=null){
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location!=null){
                        updateLocation(location);
                    }
                }
            } else if (isNetworkEnable && tempoNovaLocalizacao()){
                location = null;
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,interval,distance,this);
                if (locationManager!=null){
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location!=null){
                        updateLocation(location);
                    }
                }

            }
        }

    }

    private boolean tempoNovaLocalizacao() {
        if(localizacao!= null && localizacao.getDataLocalizacao()!= null ){
            Calendar agora = Calendar.getInstance();
            Calendar dataLocalizacao = toCalendar(localizacao.getDataLocalizacao());
            long tempo = agora.getTimeInMillis() - dataLocalizacao.getTimeInMillis();
            if(tempo > minuto){
                return true;
            }
        }
        return false;
    }

    private Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    private class TimerTaskToGetLocation extends TimerTask{
        @Override
        public void run() {

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    getlocation();
                }
            });

        }
    }

    private void updateLocation(Location location){
        localizacao = null;
        localizacao = new Localizacao(location.getLatitude(), location.getLongitude(), getNomeLocalizacao(location));
        SessionUtils.setLocalizacaoMapa(mPrefs, localizacao);
        sendBroadcast(intent);
    }

    private String getNomeLocalizacao(Location location) {
        String nomeLocalizacao = getString(R.string.sem_endereco_localizacao);
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> listAddresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if(null!=listAddresses&&listAddresses.size()>0){
                nomeLocalizacao = listAddresses.get(0).getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nomeLocalizacao;
    }


}
