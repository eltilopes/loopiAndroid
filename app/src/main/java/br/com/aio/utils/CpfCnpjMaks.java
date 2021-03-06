package br.com.aio.utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import br.com.aio.R;

/**
 * Created by elton on 11/09/2017.
 */

public class CpfCnpjMaks {
    private static final String cpfMask = "###.###.###-##";
    private static final String cnpjMask = "##.###.###/####-##";
    private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    private Context context;

    public static String unmask(String s){

        return s.replaceAll("[^0-9]*", "");

    }

    public static TextWatcher insert(final Context contexto,final EditText editText, final View validationCpf, final TextView validationCpfCnpj) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = CpfCnpjMaks.unmask(s.toString());
                String mask;
                String defaultMask = getDefaultMask(str);
                switch (str.length()) {
                    case 11:
                        mask = cpfMask;
                        break;
                    case 14:
                        mask = cnpjMask;
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

            @Override
            public void afterTextChanged(Editable s) {
                String numero = CpfCnpjMaks.unmask(s.toString());
                verificarCpfCnpj(contexto,numero,editText, validationCpfCnpj,validationCpf);
            }
        };

    }

    public static TextWatcher insert(final Context contexto,final EditText editText, final TextView validationCpfCnpj) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = CpfCnpjMaks.unmask(s.toString());
                String mask;
                String defaultMask = getDefaultMask(str);
                switch (str.length()) {
                    case 11:
                        mask = cpfMask;
                        break;
                    case 14:
                        mask = cnpjMask;
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

            @Override
            public void afterTextChanged(Editable s) {
                String numero = CpfCnpjMaks.unmask(s.toString());
                verificarCpfCnpj(contexto,numero,editText, validationCpfCnpj, null);
            }
        };

    }

    public static boolean verificarCpfCnpj(Context context, String numero,EditText editText, TextView validationCpfCnpj, View validationCpf) {
        boolean validado = true;
        try {

            if (numero.isEmpty()) {
                validado = addValidation(R.string.validation_campo_obrigatorio, context, editText, validationCpfCnpj, validationCpf);
            } else if (numero.length() == 11 && !isValidCPF(numero)) {
                validado = addValidation(R.string.validation_cpf_invalido, context, editText, validationCpfCnpj, validationCpf);
            } else if (numero.length() == 14 && !isValidCNPJ(numero)) {
                validado = addValidation(R.string.validation_cnpj_invalido, context, editText, validationCpfCnpj, validationCpf);
            } else if (numero.length() != 14 && numero.length() != 11) {
                validado = addValidation(R.string.validation_cpf_cnpj_invalido, context, editText, validationCpfCnpj, validationCpf);
            } else if (validationCpfCnpj != null) {
                validationCpfCnpj.setVisibility(View.GONE);
                if (validationCpf != null) {
                    validationCpf.setBackgroundColor(context.getResources().getColor(R.color.textColorInfoVerde));
                }
            } else if (validationCpf != null) {
                validationCpf.setBackgroundColor(context.getResources().getColor(R.color.textColorInfoVerde));
            }
        }catch (Exception e){
            validado = false;
        }
        return validado;
    }

    private static boolean addValidation(int idString, Context context, EditText editText, TextView validationCpfCnpj, View validationCpf) {
        boolean validado;
        if(validationCpfCnpj == null){
            editText.setError(context.getString(idString));
        }else{
            validationCpfCnpj.setVisibility(View.VISIBLE);
            validationCpfCnpj.setText(context.getString(idString));
        }
        if(validationCpf!=null){
            validationCpf.setBackgroundColor(context.getResources().getColor(R.color.textColorInfoVermelho));
        }
        validado = false;
        return validado;
    }

    private static String getDefaultMask(String str){

        String defaultMaks = cpfMask;
        if (str.length()>11){

            defaultMaks = cnpjMask;

        }

        return defaultMaks;
    }

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice=str.length()-1, digito; indice >= 0; indice-- ) {
            digito = Integer.parseInt(str.substring(indice,indice+1));
            soma += digito*peso[peso.length-str.length()+indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    public static boolean isValidCPF(String cpf) {
        if ((cpf==null) || (cpf.length()!=11)) return false;

        Integer digito1 = calcularDigito(cpf.substring(0,9), pesoCPF);
        Integer digito2 = calcularDigito(cpf.substring(0,9) + digito1, pesoCPF);
        return cpf.equals(cpf.substring(0,9) + digito1.toString() + digito2.toString());
    }

    public static boolean isValidCNPJ(String cnpj) {
        if ((cnpj==null)||(cnpj.length()!=14)) return false;

        Integer digito1 = calcularDigito(cnpj.substring(0,12), pesoCNPJ);
        Integer digito2 = calcularDigito(cnpj.substring(0,12) + digito1, pesoCNPJ);
        return cnpj.equals(cnpj.substring(0,12) + digito1.toString() + digito2.toString());
    }

}