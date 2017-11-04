package br.com.aio.utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import br.com.aio.R;

/**
 * Created by elton on 01/10/2017.
 */

public class TelefoneMaskUtil {

    private static final String mask10 = "(##) ####-####";
    private static final String mask11 = "(##) #####-####";
    private static final String mask8 = "####-####";
    private static final String mask9 = "#####-####";

    public static String unmask(String s) {
        return s.replaceAll("[^0-9]*", "");
    }

    public static TextWatcher insert(final Context contexto, final EditText editText, final View validation) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = TelefoneMaskUtil.unmask(s.toString());
                String mask;
                String defaultMask = getDefaultMask(str);
                switch (str.length()) {
                    case 11:
                        mask = mask11;
                        break;
                    case 10:
                        mask = mask10;
                        break;
                    case 9:
                        mask = mask9;
                        break;
                    default:
                        mask = defaultMask;
                        break;
                }

                String mascara = "";
                if (isUpdating) {
                    old = str;
                    isUpdating = false;
                    return;
                }
                int i = 0;
                for (char m : mask.toCharArray()) {
                    if ((m != '#' && str.length() > old.length()) || (m != '#' && str.length() < old.length() && str.length() != i)) {
                        mascara += m;
                        continue;
                    }

                    try {
                        mascara += str.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }
                isUpdating = true;
                editText.setText(mascara);
                editText.setSelection(mascara.length());
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
                if(contexto!=null){
                    String numero = CpfCnpjMaks.unmask(s.toString());
                    verificarTelefone(contexto,numero,editText, validation);
                }
            }
        };
    }

    public static boolean verificarTelefone(Context context, String numero, EditText editText, View validation) {
        boolean validado = true;
        try {

            if (numero.isEmpty()) {
                validado = addValidation(R.string.validation_campo_obrigatorio, context, editText, validation);
            } else if(!Patterns.PHONE.matcher(numero).matches() || numero.length()!= 11){
                validado = addValidation(R.string.validation_telefone_invalido, context, editText, validation);
            } else if (validation != null) {
                validation.setBackgroundColor(context.getResources().getColor(R.color.textColorInfoVerde));
            }
        }catch (Exception e){
            validado = false;
        }
        return validado;
    }

    private static boolean addValidation(int idString, Context context, EditText editText, View validation) {
        boolean validado;
        if(validation!=null){
            validation.setBackgroundColor(context.getResources().getColor(R.color.textColorInfoVermelho));
            editText.setError(context.getString(idString));
        }
        validado = false;
        return validado;
    }
    private static String getDefaultMask(String str) {
        String defaultMask = mask8;
        if (str.length() > 11){
            defaultMask = mask11;
        }
        return defaultMask;
    }
}
