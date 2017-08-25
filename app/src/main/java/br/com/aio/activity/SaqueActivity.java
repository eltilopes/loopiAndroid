package br.com.aio.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import br.com.aio.R;
import br.com.aio.fonts.RobotoTextView;
import br.com.aio.view.NumberTextWatcher;

/**
 * Created by elton on 17/07/2017.
 */

public class SaqueActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnClickListener {

    private RobotoTextView nomePagina ;
    private Spinner spinnerBancos;
    private Spinner spinnerFinalidades;
    private EditText editTextValorSaque;
    private TextView confirmarSaque;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saque);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(R.drawable.arrow_back_white);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_title_bar, null);
        nomePagina = (RobotoTextView) v.findViewById(R.id.nome_pagina);
        nomePagina.setText("Saque");
        actionBar.setCustomView(v);

        spinnerBancos = (Spinner) findViewById(R.id.bancos);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.list_bancos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBancos.setAdapter(adapter);
        spinnerBancos.setOnItemSelectedListener(this);

        spinnerFinalidades = (Spinner) findViewById(R.id.finalildades);
        ArrayAdapter<CharSequence> adapterFinalidades = ArrayAdapter.createFromResource(this,
                R.array.list_finalidades, android.R.layout.simple_spinner_item);
        adapterFinalidades.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFinalidades.setAdapter(adapterFinalidades);
        spinnerFinalidades.setOnItemSelectedListener(this);

        editTextValorSaque = (EditText) findViewById(R.id.valor_saque);
        editTextValorSaque.addTextChangedListener(new NumberTextWatcher(editTextValorSaque, "#,###"));

        confirmarSaque = (TextView) findViewById(R.id.solicitar_saque);
        confirmarSaque.setOnClickListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
        Toast.makeText(parent.getContext(),
                "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.solicitar_saque:
                Intent newActivity0 = new Intent(SaqueActivity.this, SaqueActivity.class);
                startActivity(newActivity0);
                break;
        }

    }
}
