package br.com.aio.view;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import br.com.aio.R;
import br.com.aio.activity.CadastroProfissionalActivity;
import br.com.aio.adapter.SpinnerAdapter;
import br.com.aio.entity.Banco;
import br.com.aio.entity.Finalidade;
import br.com.aio.utils.ToastUtils;

/**
 * Created by elton on 01/10/2017.
 */

public class DadosBancarios {

    private Activity activity;

    private EditText editTextAgencia;
    private EditText editTextDvAgencia;
    private EditText editTextConta;
    private EditText editTextDvConta;
    private EditText editTextValor;
    private Spinner spinnerFinalidade;
    private Spinner spinnerBanco;
    private Finalidade finalidade;
    private Banco banco;

    public DadosBancarios(final Activity tela) {
        setActivity(tela);
        editTextAgencia = (EditText) activity.findViewById(R.id.agencia);
        editTextDvAgencia = (EditText) activity.findViewById(R.id.dv_agencia);
        editTextConta = (EditText) activity.findViewById(R.id.conta);
        editTextDvConta = (EditText) activity.findViewById(R.id.dv_conta);
        editTextValor = (EditText) activity.findViewById(R.id.valor_saque);
        prepareEditText(editTextAgencia);
        prepareEditText(editTextDvAgencia);
        prepareEditText(editTextConta);
        prepareEditText(editTextDvConta);
        prepareEditText(editTextValor);

        banco = new Banco();
        finalidade = new Finalidade();

        spinnerFinalidade = (Spinner) activity.findViewById(R.id.finalildades);
        final SpinnerAdapter adapter = new SpinnerAdapter(activity.getApplicationContext(),
                Finalidade.getCategorias(), Finalidade.class, R.id.finalildades);
        spinnerFinalidade.setAdapter(adapter);
        spinnerFinalidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    adapter.setItemChecked(view, position);
                    finalidade =((Finalidade) adapter.getItemAtPosition(position));
                    ToastUtils.show(activity,
                            "Finalidade: " + finalidade.getDescricao(),
                            ToastUtils.INFORMATION);
                    chamarActivity(tela);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerBanco = (Spinner) activity.findViewById(R.id.bancos);
        final SpinnerAdapter adapterBanco = new SpinnerAdapter(activity.getApplicationContext(),
                Banco.getCategorias(), Banco.class, R.id.bancos);
        spinnerBanco.setAdapter(adapterBanco);
        spinnerBanco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    adapterBanco.setItemChecked(view, position);
                    banco =((Banco) adapterBanco.getItemAtPosition(position));
                    ToastUtils.show(activity,
                            "Banco: " + banco.getDescricao(),
                            ToastUtils.INFORMATION);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void chamarActivity(Activity tela) {
        if ( tela instanceof CadastroProfissionalActivity) {
            CadastroProfissionalActivity parent = (CadastroProfissionalActivity) tela;
            parent.verDadosBancarios();
        }


    }

    private void prepareEditText(EditText editText) {
        editText.setFocusableInTouchMode(false);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                habilitarEditText((EditText) v);
            }
        });
    }

    private void habilitarEditText(EditText v) {
        EditText editText = v;
        editText.setFocusableInTouchMode(true);
        editText.setFocusable(true);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    public EditText getEditTextAgencia() {
        return editTextAgencia;
    }

    public void setEditTextAgencia(EditText editTextAgencia) {
        this.editTextAgencia = editTextAgencia;
    }

    public EditText getEditTextDvAgencia() {
        return editTextDvAgencia;
    }

    public void setEditTextDvAgencia(EditText editTextDvAgencia) {
        this.editTextDvAgencia = editTextDvAgencia;
    }

    public EditText getEditTextConta() {
        return editTextConta;
    }

    public void setEditTextConta(EditText editTextConta) {
        this.editTextConta = editTextConta;
    }

    public EditText getEditTextDvConta() {
        return editTextDvConta;
    }

    public void setEditTextDvConta(EditText editTextDvConta) {
        this.editTextDvConta = editTextDvConta;
    }

    public EditText getEditTextValor() {
        return editTextValor;
    }

    public void setEditTextValor(EditText editTextValor) {
        this.editTextValor = editTextValor;
    }

    public Finalidade getFinalidade() {
        return finalidade;
    }

    public void setFinalidade(Finalidade finalidade) {
        this.finalidade = finalidade;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public void setActivity(Activity tela) {
        if ( tela instanceof CadastroProfissionalActivity) {
            activity = (CadastroProfissionalActivity) tela;
        }
        activity = tela;
    }
}
