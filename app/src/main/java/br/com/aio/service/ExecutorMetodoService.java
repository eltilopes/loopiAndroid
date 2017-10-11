package br.com.aio.service;

import android.app.Activity;
import android.content.Intent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import br.com.aio.activity.LoginActivity;
import br.com.aio.exception.AcessoNegadoException;
import br.com.aio.exception.TokenException;
import br.com.aio.utils.ToastUtils;
import br.com.aio.utils.UsuarioSharedUtils;
import retrofit.RetrofitError;

/**
 * Created by elton on 11/10/2017.
 */

public class ExecutorMetodoService {
    public static <T> T invoke(ValidadorCallBack obj, String method, Object... params) throws RetrofitError {
        try {

            if (obj == null) {
                return null;
            }

            Class<?>[] classes = new Class[params.length];
            for (int i = 0; i < params.length; i++) {
                classes[i] = params[i].getClass();
            }

            Method method2 = getMetodo(obj, method, classes);
            T result = null;

            int tentativas = 0;
            boolean redirectLogin = true;
            TokenException token = null;

            while (tentativas++ <= 2) {
                try {
                    result = (T) method2.invoke(obj, params);
                    redirectLogin = false;
                    break;
                } catch (InvocationTargetException e) {
                    if(e.getCause() instanceof RetrofitError) {
                        throw (RetrofitError) e.getCause();
                    } else  if(e.getCause() instanceof AcessoNegadoException){
                        throw (AcessoNegadoException) e.getCause();
                    }else if (e.getCause() instanceof TokenException) {
                        try {
                            new LoginService(obj.ctx).reLogin();
                        }catch (TokenException erro){
                            break;
                        }
                    }else{
                        throw e;
                    }
                }
            }
            if(token != null){
                throw new AcessoNegadoException();
            }

            if (redirectLogin) {
                redirectLogin(obj);
            }

            return result;
        } catch (RetrofitError re){
            throw re;
        } catch (AcessoNegadoException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void redirectLogin(ValidadorCallBack obj) {
        UsuarioSharedUtils.clearToken(obj.ctx);
        Intent intent = new Intent(obj.ctx, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        ToastUtils.show((Activity) obj.ctx, "Não foi possível acessar ao sistema. Por favor, faça o login novamente.", ToastUtils.WARNING);
        obj.ctx.startActivity(intent);
    }

    private static Method getMetodo(Object obj, String metodo, Class<?>[] types) {
        try {

            return obj.getClass().getDeclaredMethod(metodo, types);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
