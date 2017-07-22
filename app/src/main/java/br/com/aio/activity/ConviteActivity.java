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

public class ConviteActivity extends Activity implements View.OnClickListener {

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
        setContentView(R.layout.activity_convite);
        TextView continuar, naoTenhoConvite;
        continuar = (TextView) findViewById(R.id.continuar);
        naoTenhoConvite = (TextView) findViewById(R.id.naoTenhoConvite);

        continuar.setOnClickListener(this);
        naoTenhoConvite.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.continuar:
                Intent newActivity0 = new Intent(ConviteActivity.this, ConviteActivity.class);
                startActivity(newActivity0);
                break;
            case R.id.naoTenhoConvite:
                Intent newActivity = new Intent(ConviteActivity.this, NaoTenhoConviteActivity.class);
                startActivity(newActivity);
                break;
        }

    }
}
