package br.com.aio.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import br.com.aio.R;

/**
 * Created by elton on 17/07/2017.
 */

public class NaoTenhoConviteActivity extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE); // Removing
        setContentView();
    }

    private void setContentView() {
        setContentView(R.layout.activity_nao_tenho_convite);
        TextView pedirConvite;
        pedirConvite = (TextView) findViewById(R.id.pedirConvite);

        pedirConvite.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.pedirConvite:
                Toast.makeText(this, "Pedir Convite", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
