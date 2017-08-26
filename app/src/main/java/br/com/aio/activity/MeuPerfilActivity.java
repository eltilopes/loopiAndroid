package br.com.aio.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import br.com.aio.R;
import br.com.aio.fonts.RobotoTextView;

/**
 * Created by elton on 17/07/2017.
 */

public class MeuPerfilActivity extends AppCompatActivity implements AdapterView.OnClickListener {

    private RobotoTextView nomePagina ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meu_perfil);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(R.drawable.arrow_back_white);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_title_bar, null);
        nomePagina = (RobotoTextView) v.findViewById(R.id.nome_pagina);
        nomePagina.setText("Meu Perfil");
        actionBar.setCustomView(v);
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
