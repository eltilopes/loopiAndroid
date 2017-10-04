package br.com.aio.activity;

import android.annotation.TargetApi;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import br.com.aio.R;
import br.com.aio.view.CronometroView;

/**
 * Created by elton on 03/10/2017.
 */

public class ServicoReceiverActivity extends AppCompatActivity {
    /** Called when the activity is first created. */

    TimePickerDialog timePickerDialog;
    CounterClass timer;

    private CronometroView mCountDown = null;
    private TextView mTxtHeadline = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servico_receiver);
        mTxtHeadline = (TextView) findViewById(R.id.txt_headline);
        mTxtHeadline.setText("Aceitar Serviço");
        mCountDown = (CronometroView) findViewById(R.id.view_ticktock_countdown);
        if (mCountDown != null) {
            mCountDown.setOnTickListener(new CronometroView.OnTickListener() {
                @Override
                public String getText(long timeRemaining) {
                    int seconds = (int) (timeRemaining / 1000) % 60;
                    int minutes = (int) ((timeRemaining / (1000 * 60)) % 60);
                    int hours = (int) ((timeRemaining / (1000 * 60 * 60)) % 24);
                    int days = (int) (timeRemaining / (1000 * 60 * 60 * 24));
                    boolean hasDays = days > 0;
                    return String.format("%1$02d%4$s %2$02d%5$s %3$02d%6$s",
                            hasDays ? days : hours,
                            hasDays ? hours : minutes,
                            hasDays ? minutes : seconds,
                            hasDays ? "d" : "h",
                            hasDays ? "h" : "m",
                            hasDays ? "m" : "s");
                }
            });
        }

    /*    timer = new CounterClass(180000, 1000);
        openTimePickerDialog(false);
        timer.start();
*/

    }

    @Override
    protected void onStart() {
        super.onStart();
        Calendar end = Calendar.getInstance();
        end.add(Calendar.MINUTE, 2);
        end.set(Calendar.SECOND, 0);

        Calendar start = Calendar.getInstance();
        start.add(Calendar.MINUTE, -1);
        if (mCountDown != null) {
            mCountDown.start(start,end);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        mCountDown.stop();
    }

    public class CounterClass extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */

        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            timePickerDialog.setTitle(hms);

        }

        @Override
        public void onFinish() {

        }
    }



    private void openTimePickerDialog(boolean is24r){
        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(
                ServicoReceiverActivity.this,
                onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                is24r);
        timePickerDialog.setTitle("Atender Chamado");
        timePickerDialog.show();
    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            timer = new CounterClass((minute * 60 * 1000) + (hourOfDay * 60* 60 * 1000), 1000);
            long millis = (minute * 60 * 1000) + (hourOfDay * 60* 60 * 1000);
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            timePickerDialog.setTitle(hms);

        }};


}