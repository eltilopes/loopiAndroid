package br.com.aio.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import br.com.aio.R;
import br.com.aio.utils.ToastUtils;

/**
 * Created by elton on 17/07/2017.
 */

public class TermosActivity extends Activity implements View.OnClickListener {

    private Spinner spinnerLanguagesDialog;
    private String[] languages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE); // Removing
        setContentView();
        spinnerLanguagesDialog = (Spinner) findViewById(R.id.spinner_languages_dialog);

        languages = getResources().getStringArray(R.array.list_sub_categoria);
        spinnerLanguagesDialog.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, languages));
        spinnerLanguagesDialog.setSelection(2);

        spinnerLanguagesDialog.setOnItemSelectedListener(new SpinnerItemSelectedListener());
    }

    private class SpinnerItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            ToastUtils.show(TermosActivity.this,
                    "Item selected is " + languages[position] ,ToastUtils.INFORMATION);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            ToastUtils.show(TermosActivity.this,
                    "onNothingSelected : " ,ToastUtils.ERROR);
        }
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
