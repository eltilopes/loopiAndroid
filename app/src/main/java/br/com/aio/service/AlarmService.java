package br.com.aio.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import br.com.aio.R;
import br.com.aio.activity.ServicoReceiverActivity;

/**
 * Created by elton on 03/10/2017.
 */

public class AlarmService extends WakeIntentService {

    private static final int PERIOD=1000;  // 10sec

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    void doReminderWork(Intent intent) {
        PowerManager pm = (PowerManager)this.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "Tag");
        wl.acquire();

        NotificationManager manager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(this, ServicoReceiverActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0,
                notificationIntent, PendingIntent.FLAG_ONE_SHOT);
        int id = 123456789;
        Notification.Builder builder = new Notification.Builder(this);

        builder.setContentIntent(pi)
                .setSmallIcon(R.drawable.ic_alarm_on)
                .setTicker("Local Notification Ticker")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle("Local Notification")
                .setContentText("This is content text.");
        Notification n = builder.getNotification();

        manager.notify(id, n);
        this.startActivity(notificationIntent);

    }

}
