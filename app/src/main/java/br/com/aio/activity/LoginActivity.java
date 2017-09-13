package br.com.aio.activity;

/**
 * Created by elton on 16/07/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import br.com.aio.R;
import br.com.aio.utils.CpfCnpjMaks;

public class LoginActivity extends Activity implements OnClickListener {

        private TextWatcher cpfCnpjMaks;
        private TextView validationCpfCnpj;
        private TextView validationSenha;
        private EditText cpfCnpj;
        private EditText senha;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getWindow().requestFeature(Window.FEATURE_NO_TITLE); // Removing
            setContentView(R.layout.activity_login);
            validationCpfCnpj = (TextView) findViewById(R.id.cpf_cnpj_validation);
            validationCpfCnpj.setVisibility(View.GONE);
            validationSenha = (TextView) findViewById(R.id.senha_validation);
            validationSenha.setVisibility(View.GONE);
            cpfCnpj = (EditText) findViewById(R.id.cpf_cnpj);
            senha = (EditText) findViewById(R.id.senha);
            cpfCnpjMaks = CpfCnpjMaks.insert(getApplicationContext(),cpfCnpj,validationCpfCnpj);
            cpfCnpj.addTextChangedListener(cpfCnpjMaks);
            senha.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    senhaDigitada(editable.toString());
                }
            });

            Typeface sRobotoThin = Typeface.createFromAsset(getAssets(),
                    "fonts/Roboto-Thin.ttf");
            ;
            cpfCnpj.setTypeface(sRobotoThin);
            senha.setTypeface(sRobotoThin);

            TextView login;
            login = (TextView) findViewById(R.id.login);

            login.setOnClickListener(this);
            senha.setText("senha");
            cpfCnpj.setText("123456");
        }

    private boolean senhaDigitada(String senha) {
        boolean digitouSenha = !senha.isEmpty();
        if (digitouSenha) {
            validationSenha.setVisibility(View.GONE);
        } else {
            validationSenha.setVisibility(View.VISIBLE);
            validationSenha.setText("Campo obrigatório");
        }
        return digitouSenha;
    }

    @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.login:
                    entrar();
                    break;
            }

        }

    private void entrar() {
        boolean cpfCnpjValido = CpfCnpjMaks.verificarCpfCnpj(getApplicationContext(),cpfCnpj.getText().toString(), cpfCnpj, validationCpfCnpj);
        boolean digitouSenha = senhaDigitada(senha.getText().toString());
        if(cpfCnpjValido && digitouSenha){
            Intent newActivity0 = new Intent(LoginActivity.this, ListagemActivity.class);
            startActivity(newActivity0);
        }
    }
}
