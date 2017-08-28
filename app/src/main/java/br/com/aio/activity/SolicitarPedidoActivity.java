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
import android.widget.EditText;
import android.widget.ImageView;

import br.com.aio.R;
import br.com.aio.fonts.RobotoTextView;

/**
 * Created by elton on 17/07/2017.
 */

public class SolicitarPedidoActivity extends AppCompatActivity implements AdapterView.OnClickListener {

    private RobotoTextView nomePagina ;
    private ImageView imagemProfissional;
    private EditText mapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_pedido);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(R.drawable.arrow_back_white);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_title_bar, null);
        nomePagina = (RobotoTextView) v.findViewById(R.id.nome_pagina);
        nomePagina.setText("Solicitar Pedido");
        actionBar.setCustomView(v);

        mapa = (EditText) findViewById(R.id.mapa);
        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newActivity = new Intent(SolicitarPedidoActivity.this, MapsActivity.class);
                startActivity(newActivity);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {

    }
}
