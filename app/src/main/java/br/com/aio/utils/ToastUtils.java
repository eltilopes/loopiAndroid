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

import com.google.gson.reflect.TypeToken;

import br.com.aio.R;
import br.com.aio.entity.FieldsErroRetrofit;
import br.com.aio.service.ValidadorCallBack;
import retrofit.client.Response;

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

    public static void showErro(final Activity activity, final Response resp) {
        JsonUtils<FieldsErroRetrofit> json = new JsonUtils();
        final FieldsErroRetrofit erroField = json.converteObject(resp, new TypeToken<FieldsErroRetrofit>() {
        }.getType());
        if (activity != null)
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    if (erroField != null && erroField.getErrors() != null && erroField.getErrors().size() > 0) {
                        StringBuilder erroString = new StringBuilder();
                        for (int i = 0; i < erroField.getErrors().size(); i++) {
                            erroString.append(erroField.getErrors().get(i));
                            if (i != erroField.getErrors().size() - 1)
                                erroString.append(" \n ");
                        }
                        ToastUtils.show(activity, erroString.toString(), ToastUtils.WARNING);
                    } else if (resp == null) {
                        ToastUtils.show(activity, activity.getResources().getString(R.string.error_conexao_servidor), ToastUtils.ERROR);
                    } else if (ValidadorCallBack.Status.contemStatus(resp.getStatus())) {
                        ToastUtils.show(activity, activity.getResources().getString(R.string.error_credenciais_invalidas), ToastUtils.WARNING);
                    } else if (resp.getStatus() == ValidadorCallBack.Status.ERROR_SERVIDOR.getStatus()) {
                        ToastUtils.show(activity, activity.getResources().getString(R.string.error_servidor), ToastUtils.ERROR);
                    } else if (resp.getStatus() == ValidadorCallBack.Status.NAO_ENCONTRADO.getStatus()) {
                        ToastUtils.show(activity, activity.getResources().getString(R.string.error_nao_encontrado), ToastUtils.WARNING);
                    } else if (resp.getStatus() == ValidadorCallBack.Status.DADOS_DUPLICADOS.getStatus()) {
                        ToastUtils.show(activity, activity.getResources().getString(R.string.error_dados_duplicados), ToastUtils.WARNING);
                    } else if (resp.getStatus() == ValidadorCallBack.Status.DADOS_INVALIDOS.getStatus()) {
                        ToastUtils.show(activity, activity.getResources().getString(R.string.error_dados_invalidos), ToastUtils.WARNING);
                    } else {
                        ToastUtils.show(activity, activity.getResources().getString(R.string.error_inesperado) + resp.getStatus(), ToastUtils.ERROR);
                    }
                }
            });
    }


}
