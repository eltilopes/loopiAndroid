package br.com.aio.activity;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.io.PrintWriter;
import java.io.StringWriter;

import br.com.aio.utils.UsuarioSharedUtils;

/**
 * Created by elton on 11/10/2017.
 */

public class AplicationEscopo extends Application {

    private Thread.UncaughtExceptionHandler defaultUEH;
    public static String EXTRAS_KEY_ERROR = "FATAL_ERROR";

    public AplicationEscopo() {
        defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(_unCaughtExceptionHandler);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    // handler listener
    private Thread.UncaughtExceptionHandler _unCaughtExceptionHandler =
            new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread thread, Throwable ex) {

                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    ex.printStackTrace(pw);


                    Intent intent = null;
                    if(UsuarioSharedUtils.isLogado(getApplicationContext())){
                        intent = new Intent(getApplicationContext(), ListagemActivity.class);
                    }else{
                        intent = new Intent(getApplicationContext(), CriarContaActivity.class);
                    }
                    intent.putExtra(EXTRAS_KEY_ERROR, sw.toString());

                    // here I do logging of exception to a db
                    PendingIntent myActivity = PendingIntent.getActivity(getApplicationContext(),
                            192837, intent,
                            PendingIntent.FLAG_ONE_SHOT);

                    AlarmManager alarmManager;
                    alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            15000, myActivity);

                    System.exit(2);

                    defaultUEH.uncaughtException(thread, ex);
                }
            };



}
