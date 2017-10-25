package br.com.aio.exception;

import android.content.Context;
import android.content.Intent;

import br.com.aio.activity.LogarActivity;
import br.com.aio.service.ValidadorCallBack;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by elton on 11/10/2017.
 */

public class ErrorHandler implements retrofit.ErrorHandler {

    private final Context ctx;

    public ErrorHandler(Context ctx) {
        this.ctx = ctx;
    }

    @Override public Throwable handleError(RetrofitError cause) {
        Response r = cause.getResponse();
        if (r != null && r.getStatus() == ValidadorCallBack.Status.TOKEN_INVALIDO.getStatus() && ctx != null && !cause.getUrl().contains("oauth/token")) {
            throw new TokenException();
        }else if(r != null && r.getStatus() == ValidadorCallBack.Status.ACESSO_NEGADO.getStatus()){
            Intent intent = new Intent(ctx, LogarActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
            // throw new AcessoNegadoException(r);
        }
        return cause;
    }
}
