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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.aio.R;
import br.com.aio.adapter.LazyAdapter;
import br.com.aio.fonts.RobotoTextView;

/**
 * Created by elton on 17/07/2017.
 */

public class ExtratoSaqueActivity extends AppCompatActivity implements View.OnClickListener {

    private RobotoTextView nomePagina ;
    private ListView listaMovimentacoes;
    private LayoutInflater inflator;

    public static final String KEY_DIA = "dia";
    public static final String KEY_HISTORICO = "historico";
    public static final String KEY_VALOR = "valor";
    public static final String KEY_TIPO_MOVIMENTACAO = "tipoMovimentaqcao";
    public static final String DEBITO = "DEBITO";
    public static final String CREDITO = "CREDITO";

    private ArrayList<HashMap<String, String>> movimentacoesMap = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extrato_saque);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(R.drawable.arrow_back_white);

        inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_title_bar, null);
        nomePagina = (RobotoTextView) v.findViewById(R.id.nome_pagina);
        nomePagina.setText("Extrato CashBack");
        actionBar.setCustomView(v);
        setListMovimentacoes();

        TextView saque;
        saque = (TextView) findViewById(R.id.saque);
        saque.setOnClickListener(this);
    }

    private void setListMovimentacoes() {
        getMovimentacoes();
        mostrarListaMovimentacoes();
    }

    private void getMovimentacoes() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(KEY_DIA, "20/07/2017");
        map.put(KEY_HISTORICO, "Médico");
        map.put(KEY_VALOR, "100,00");
        map.put(KEY_TIPO_MOVIMENTACAO,DEBITO);
        movimentacoesMap.add(map);

        map = new HashMap<String, String>();
        map.put(KEY_DIA, "15/07/2017");
        map.put(KEY_HISTORICO, "Manicure");
        map.put(KEY_VALOR, "50,00");
        map.put(KEY_TIPO_MOVIMENTACAO,DEBITO);
        movimentacoesMap.add(map);

        map = new HashMap<String, String>();
        map.put(KEY_DIA, "10/07/2017");
        map.put(KEY_HISTORICO, "CashBack Friend");
        map.put(KEY_VALOR, "8,50");
        map.put(KEY_TIPO_MOVIMENTACAO,CREDITO);
        movimentacoesMap.add(map);

    }

    private void mostrarListaMovimentacoes() {
        listaMovimentacoes = (ListView) findViewById(R.id.lista_movimentacoes);
        View header = (View) inflator.inflate(R.layout.header_movimentacoes, null);
        listaMovimentacoes.addHeaderView(header);
        listaMovimentacoes.setHeaderDividersEnabled(true);
        listaMovimentacoes.setHorizontalScrollBarEnabled(true);
        listaMovimentacoes.setAdapter(new LazyAdapter(this, movimentacoesMap));
        listaMovimentacoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(), parent.getItemAtPosition(position).toString() + " selecionado!", Toast.LENGTH_SHORT).show();

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
        switch(v.getId()) {
            case R.id.saque:
                Intent newActivity0 = new Intent(ExtratoSaqueActivity.this, ConviteActivity.class);
                startActivity(newActivity0);
                break;
        }

    }
}
