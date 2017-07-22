package br.com.aio.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import br.com.aio.R;

/**
 * Created by elton on 17/07/2017.
 */

public class CriarContaActivity extends Activity implements View.OnClickListener {

    public static final String MEDIA = "Media";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE); // Removing
        // ActionBar
        String category = MEDIA;
        setContentView(category);
    }

    private void setContentView(String category) {
        setContentView(R.layout.activity_criar_conta);
        TextView criarConta, entrar;
        criarConta = (TextView) findViewById(R.id.criarConta);
        entrar = (TextView) findViewById(R.id.entrar);

        criarConta.setOnClickListener(this);
        entrar.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.criarConta:
                Intent newActivity0 = new Intent(CriarContaActivity.this, ConviteActivity.class);
                startActivity(newActivity0);
                break;
            case R.id.entrar:
                Intent newActivity = new Intent(CriarContaActivity.this, LoginActivity.class);
                startActivity(newActivity);
                break;
        }

    }
}
