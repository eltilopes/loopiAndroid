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

public class TermosActivity extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE); // Removing
        setContentView();
    }

    private void setContentView() {
        setContentView(R.layout.activity_termos);
        TextView enviar;
        enviar = (TextView) findViewById(R.id.enviar);

        enviar.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.enviar:
                Intent newActivity0 = new Intent(TermosActivity.this, ListagemActivity.class);
                startActivity(newActivity0);
                break;
        }

    }
}
