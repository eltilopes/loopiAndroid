package br.com.aio.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import br.com.aio.R;
import br.com.aio.exception.EditTextValidation;

/**
 * Created by elton on 25/09/2017.
 */

public class ViewUtils {
    private static final String VIRGULA = ", ";
    private static final String ESPACO = " ";
    private static final String EXCLAMACAO = "!";

    public static Drawable drawableSetTint(Drawable d, int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(d);
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }

    public static void escondeTeclado(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static Drawable scaleDrawable(Drawable drawable, int width, int height) {

        int wi = drawable.getIntrinsicWidth();
        int hi = drawable.getIntrinsicHeight();
        int dimDiff = Math.abs(wi - width) - Math.abs(hi - height);
        float scale = (dimDiff > 0) ? width / (float)wi : height /
                (float)hi;
        Rect bounds = new Rect(0, 0, (int)(scale * wi), (int)(scale * hi));
        drawable.setBounds(bounds);
        return drawable;
    }

    public static String getTextSpam(Drawable drawable, String texto, int[] state, int alinhamento, int spanned) {
        spanned = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
        //alinhamento = ImageSpan.ALIGN_BOTTOM;
        SpannableStringBuilder textspan = new SpannableStringBuilder(texto);
        drawable.setState(state);  // match the background state
        textspan.setSpan(new ImageSpan(drawable, alinhamento), 0, 1, spanned);
        return textspan.toString();
    }

    public static boolean editableEmpty( final Editable s ) {
        return s == null || s.toString().trim().isEmpty();
    }

    public static EditTextValidation validarSenha(String senha, Context context, int forcaSenha)
    {
        String upperCaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseChars = "abcdefghijklmnopqrstuvwxyz";
        String numberChars = "0123456789";
        String specialChars = "!@#$%^&*()_-+=<>?/{}~|";
        String validacao = context.getString(R.string.validation_senha_invalida);
        boolean upperCase = false,lowerCase = false, numbers = false, specialCharacters = false;
        for(String letra : senha.split("")){
            if(!letra.equals("")){
                if(!upperCase && upperCaseChars.contains(letra)){
                    upperCase = true;
                }if(!lowerCase && lowerCaseChars.contains(letra)){
                    lowerCase = true;
                }if(!numbers && numberChars.contains(letra)){
                    numbers = true;
                }if(!specialCharacters && specialChars.contains(letra)){
                    specialCharacters = true;
                }
            }
        }
        if(!upperCase) {
            validacao = validacao + ESPACO + context.getString(R.string.validation_letras_maiusculas) + VIRGULA;
            forcaSenha = forcaSenha -1;
        }
        if(!lowerCase) {
            validacao = validacao + ESPACO + context.getString(R.string.validation_letras_minusculas) + VIRGULA;
            forcaSenha = forcaSenha -1;
        }
        if(!numbers) {
            validacao = validacao + ESPACO + context.getString(R.string.validation_numeros) + VIRGULA;
            forcaSenha = forcaSenha -1;
        }
        if(!specialCharacters) {
            validacao = validacao + ESPACO + context.getString(R.string.validation_caracteres_especiais) + VIRGULA;
            forcaSenha = forcaSenha -1;
        }

        if(validacao.equals(context.getString(R.string.validation_senha_invalida))){
            validacao = null;
        }else{
            validacao = validacao.substring(0,validacao.length()-2) + EXCLAMACAO;
        }

        return validacao!=null ? new EditTextValidation(validacao,forcaSenha, context) : null;
    }

}
