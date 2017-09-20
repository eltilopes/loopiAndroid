package br.com.aio.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.aio.R;

/**
 * Created by elton on 20/09/2017.
 */

public class ToastUtils {
    public static final int INFORMATION = 0;
    public static final int WARNING     = 1;
    public static final int ERROR       = 2;
    public static Toast toast;

    public static void show(final Activity context, String text, int toastType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.toast_layout, null);

        TextView tv = (TextView) layout.findViewById(R.id.text_toast);
        tv.setText(text);

        ImageView icon = (ImageView) layout.findViewById(R.id.icon_toast);

        CardView cardToast = (CardView) layout.findViewById(R.id.card_toast);
        Drawable img;
        int bg;
        switch (toastType) {
            case WARNING:
                img = context.getResources().getDrawable(R.drawable.ic_warning);
                bg = R.color.textColorInfoAmarelo;
                break;
            case ERROR:
                img = context.getResources().getDrawable(R.drawable.ic_error);
                bg = R.color.textColorInfoVermelho;
                break;
            default:
                img = context.getResources().getDrawable(R.drawable.ic_info);
                bg = R.color.textColorInfoAzul;
                break;
        }

        icon.setImageDrawable(img);
        cardToast.setBackgroundTintList(context.getResources().getColorStateList(bg));
        if (context != null){
            context.runOnUiThread(new Runnable() {
                public void run() {
                    if (toast != null)
                        toast.cancel();
                    toast = new Toast(context);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            });
        }
    }

}
