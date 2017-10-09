package br.com.aio.activity;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.aio.R;
import br.com.aio.entity.ServicoCard;
import br.com.aio.fonts.RobotoTextView;
import br.com.aio.service.AdminReceiver;
import br.com.aio.utils.SessionUtils;
import br.com.aio.view.CronometroView;

import static br.com.aio.utils.BundleUtils.ACTIVITY_ACEITAR_SERVICO;
import static br.com.aio.utils.BundleUtils.PREFS_NAME;


/**
 * Created by elton on 04/10/2017.
 */

public class AceitarServicoFirebaseActivity extends AppCompatActivity implements View.OnClickListener {

    private Ringtone ringtone;
    private CronometroView mCountDown = null;
    private RobotoTextView nomePagina ;
    private PowerManager mPowerManager;
    private PowerManager.WakeLock mWakeLock;
    private SharedPreferences mPrefs;
    private RobotoTextView aceitar;
    private RobotoTextView recusar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aceitar_servico);
        aceitar = (RobotoTextView) findViewById(R.id.aceitar);
        recusar = (RobotoTextView) findViewById(R.id.recusar);
        aceitar.setOnClickListener(this);
        recusar.setOnClickListener(this);
        mPrefs = getSharedPreferences(PREFS_NAME, 0);
        DevicePolicyManager deviceManger = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
        ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName compName = new ComponentName(this, AdminReceiver.class);
        mPowerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = mPowerManager.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK
                | PowerManager.FULL_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
        if(mWakeLock.isHeld()) {
            mWakeLock.release();
        }
        mWakeLock.acquire();
        mWakeLock.release();
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock =  keyguardManager.newKeyguardLock("Tag");
        keyguardLock.disableKeyguard();
        final Window win= getWindow();
        win.setFlags(
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        //checkIntent(getIntent());
        String s = this.getComponentName().getClassName();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(R.drawable.arrow_back_white);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_title_bar, null);
        nomePagina = (RobotoTextView) v.findViewById(R.id.nome_pagina);
        nomePagina.setText("Servico Solicitado");
        actionBar.setCustomView(v);
        startCronometro();
       /* slideToUnLock = (MySwitch)findViewById(R.id.slideToUnLock);
        slideToUnLock.toggle();
        slideToUnLock.setOnCheckedChangeListener(this);*/
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);
        ringtone = RingtoneManager.getRingtone(getApplicationContext(),
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        if (ringtone != null) {
            ringtone.play();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        startCronometro();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startCronometro();
    }

    private void abrirCronometroView() {
        if (mCountDown != null) {
            mCountDown.setOnTickListener(getOnTickListener());
        }
    }

    private CronometroView.OnTickListener getOnTickListener(){

        return new CronometroView.OnTickListener() {
            SimpleDateFormat formatMinutos = new SimpleDateFormat("mm");
            SimpleDateFormat formatSegundos = new SimpleDateFormat("ss");
            Date date = new Date();
            @Override
            public String getText(long timeRemaining) {
                date.setTime(timeRemaining);
                String tempo = formatMinutos.format(date)+"m : "+formatSegundos.format(date)+"s";
                if(timeRemaining==0L){
                    moveTaskToBack(true);
                }
                return tempo;
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        startCronometro();
    }

    private void startCronometro() {
        mCountDown = null;
        mCountDown = (CronometroView) findViewById(R.id.cronometro_countdown);
        Calendar end = Calendar.getInstance();
        end.add(Calendar.MINUTE, 1);
        end.add(Calendar.SECOND, 0);

        Calendar start = Calendar.getInstance();
        start.add(Calendar.MINUTE, -1);
        mCountDown.setmStartTime(start);
        mCountDown.setmEndTime(end);
        mCountDown.start(start,end);

        abrirCronometroView();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mCountDown.stop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        SessionUtils.setActivityAnterior(mPrefs, ACTIVITY_ACEITAR_SERVICO);
        Intent newActivity = new Intent(AceitarServicoFirebaseActivity.this, ListagemActivity.class);
        startActivity(newActivity);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.aceitar:
                SessionUtils.setServicoCard(mPrefs,ServicoCard.getServicos().get(2));
                Intent newActivity0 = new Intent(AceitarServicoFirebaseActivity.this, IniciarAtendimentoActivity.class);
                startActivity(newActivity0);
                break;
            case R.id.recusar:
                moveTaskToBack(true);
                break;
        }

    }
}