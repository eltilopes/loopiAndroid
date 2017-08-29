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
import android.widget.TextView;

import br.com.aio.R;
import br.com.aio.fonts.RobotoTextView;

/**
 * Created by elton on 17/07/2017.
 */

public class CadastroServicoActivity extends AppCompatActivity implements AdapterView.OnClickListener  {

    private RobotoTextView nomePagina ;
    private TextView historico ;
    private TextView novaSolicitacao ;
    private TextView cadastre ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_servico);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(R.drawable.arrow_back_white);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_title_bar, null);
        nomePagina = (RobotoTextView) v.findViewById(R.id.nome_pagina);
        nomePagina.setText("Profissional");
        actionBar.setCustomView(v);

        historico = (TextView) findViewById(R.id.historico);
        historico.setOnClickListener(this);

        novaSolicitacao = (TextView) findViewById(R.id.nova_solicitacao);
        novaSolicitacao.setOnClickListener(this);

        cadastre = (TextView) findViewById(R.id.cadastre);
        cadastre.setOnClickListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.historico:
                Intent newActivity0 = new Intent(CadastroServicoActivity.this, ConfirmarSaqueActivity.class);
                startActivity(newActivity0);
                break;
            case R.id.cadastre:
                Intent newActivity1 = new Intent(CadastroServicoActivity.this, CadastroProfissionalActivity.class);
                startActivity(newActivity1);
                break;
            case R.id.nova_solicitacao:
                Intent newActivity2 = new Intent(CadastroServicoActivity.this, ConfirmarSaqueActivity.class);
                startActivity(newActivity2);
                break;
        }

    }
}
