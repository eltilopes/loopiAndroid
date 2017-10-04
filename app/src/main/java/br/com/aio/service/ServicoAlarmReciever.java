package br.com.aio.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import br.com.aio.activity.ServicoReceiverActivity;
import br.com.aio.utils.SessionUtils;

import static br.com.aio.utils.BundleUtils.PREFS_NAME;

/**
 * Created by elton on 03/10/2017.
 */

public class ServicoAlarmReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences  mPrefs = context.getSharedPreferences(PREFS_NAME, 0);
        Class activityAnterior = SessionUtils.getActivityAnterior(mPrefs);
        ServicoReceiverActivity servicoReceiverActivity= new ServicoReceiverActivity();
        Toast.makeText(context, "OnReceive alarm test", Toast.LENGTH_SHORT).show();
    }
}