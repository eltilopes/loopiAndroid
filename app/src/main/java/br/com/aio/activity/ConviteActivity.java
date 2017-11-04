package br.com.aio.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import br.com.aio.R;
import br.com.aio.utils.CpfCnpjMaks;

/**
 * Created by elton on 17/07/2017.
 */

public class ConviteActivity extends Activity implements View.OnClickListener {


    private TextWatcher cpfCnpjMaks;
    private TextView validationCpfCnpj;
    private TextView validationConvite;
    private EditText cpfCnpj;
    private EditText convite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE); // Removing
        setContentView();
    }

    private void setContentView() {
        setContentView(R.layout.activity_convite);
        validationCpfCnpj = (TextView) findViewById(R.id.cpf_cnpj_validation);
        validationCpfCnpj.setVisibility(View.GONE);
        validationConvite = (TextView) findViewById(R.id.codigo_convite_validation);
        validationConvite.setVisibility(View.GONE);
        cpfCnpj = (EditText) findViewById(R.id.edit_text_cpf_cnpj);
        convite = (EditText) findViewById(R.id.codigo_convite);
        cpfCnpjMaks = CpfCnpjMaks.insert(getApplicationContext(),cpfCnpj,validationCpfCnpj);
        cpfCnpj.addTextChangedListener(cpfCnpjMaks);
        convite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                conviteCorreto(editable.toString());
            }
        });

        Typeface sRobotoThin = Typeface.createFromAsset(getAssets(),
                "fonts/Roboto-Thin.ttf");
        ;
        cpfCnpj.setTypeface(sRobotoThin);
        convite.setTypeface(sRobotoThin);

        TextView continuar, naoTenhoConvite;
        continuar = (TextView) findViewById(R.id.continuar);
        naoTenhoConvite = (TextView) findViewById(R.id.naoTenhoConvite);

        continuar.setOnClickListener(this);
        naoTenhoConvite.setOnClickListener(this);
    }

    private boolean conviteCorreto(String convite) {
        boolean digitouConvite = !convite.isEmpty();
        if (digitouConvite) {
            validationConvite.setVisibility(View.GONE);
        } else {
            validationConvite.setVisibility(View.VISIBLE);
            validationConvite.setText("Campo obrigatório");
        }
        return digitouConvite;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.continuar:
                entrar();
                break;
            case R.id.naoTenhoConvite:
                Intent newActivity = new Intent(ConviteActivity.this, NaoTenhoConviteActivity.class);
                startActivity(newActivity);
                break;
        }

    }
    private void entrar() {
        boolean cpfCnpjValidado = CpfCnpjMaks.verificarCpfCnpj(getApplicationContext(),cpfCnpj.getText().toString(),null, validationCpfCnpj, null);
        boolean entrar = conviteCorreto(convite.getText().toString())
                && cpfCnpjValidado;
        if(entrar){
            Intent newActivity0 = new Intent(ConviteActivity.this, ListagemActivity.class);
            startActivity(newActivity0);
        }
    }
}
