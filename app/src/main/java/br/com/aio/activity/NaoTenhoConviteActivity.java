package br.com.aio.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import br.com.aio.R;
import br.com.aio.utils.CpfCnpjMaks;
import br.com.aio.view.FloatLabeledEditText;

/**
 * Created by elton on 17/07/2017.
 */

public class NaoTenhoConviteActivity extends Activity implements View.OnClickListener {

    private FloatLabeledEditText nomeCompleto;
    private FloatLabeledEditText cpfCnpj;
    private FloatLabeledEditText email;
    private TextWatcher cpfCnpjMaks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE); // Removing
        setContentView();
    }

    private void setContentView() {
        setContentView(R.layout.activity_nao_tenho_convite);
        cpfCnpj = (FloatLabeledEditText) findViewById(R.id.cpf_cnpj);
        email = (FloatLabeledEditText) findViewById(R.id.email);
        nomeCompleto = (FloatLabeledEditText) findViewById(R.id.nome_completo);
        cpfCnpjMaks = CpfCnpjMaks.insert(getApplicationContext(),cpfCnpj.getEditText(), null);
        cpfCnpj.getEditText().addTextChangedListener(cpfCnpjMaks);
        email.getEditText().setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        email.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!emailValido(editable.toString())){
                    email.getEditText().setError(getString(R.string.validation_email_invalido));
                }
            }
        });
        nomeCompleto.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!nomeValido(editable.toString())){

                }
            }
        });
        TextView pedirConvite;
        pedirConvite = (TextView) findViewById(R.id.pedirConvite);

        pedirConvite.setOnClickListener(this);
    }

    public boolean emailValido(String email) {
        boolean valido = true;
        if (email == null || email.isEmpty()){
            valido = false;
            this.email.getEditText().setError(getString(R.string.validation_campo_obrigatorio));
        }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            valido = false;
            this.email.getEditText().setError(getString(R.string.validation_email_invalido));
        }
        return valido;
    }

    public boolean nomeValido(String nome) {
        boolean valido = true;
        if (nome == null){
            valido = false;
            nomeCompleto.getEditText().setError(getString(R.string.validation_campo_obrigatorio));
        }else if(nome.split(" ").length < 2){
            valido = false;
            nomeCompleto.getEditText().setError(getString(R.string.validation_nome_invalido));
        }
        return valido;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.pedirConvite:
                encaminharConvite();
                break;
        }

    }

    private void encaminharConvite() {
        boolean nomeValido = nomeValido(nomeCompleto.getText().toString());
        boolean emailValido = emailValido(email.getText().toString());
        boolean cpfCnpjValido = CpfCnpjMaks.verificarCpfCnpj(getApplicationContext(),cpfCnpj.getText().toString(), cpfCnpj.getEditText(), null);
        if(nomeValido && emailValido && cpfCnpjValido){
            Toast.makeText(this, "Pedir Convite", Toast.LENGTH_SHORT).show();
        }
    }
}
