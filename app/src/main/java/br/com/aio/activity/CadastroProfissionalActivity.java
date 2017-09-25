package br.com.aio.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import br.com.aio.R;
import br.com.aio.adapter.SpinnerAdapter;
import br.com.aio.entity.Categoria;
import br.com.aio.entity.Profissional;
import br.com.aio.entity.SubCategoria;
import br.com.aio.fonts.RobotoTextView;
import br.com.aio.utils.SessionUtils;
import br.com.aio.utils.ToastUtils;

import static br.com.aio.utils.BundleUtils.PREFS_NAME;

/**
 * Created by elton on 17/07/2017.
 */

public class CadastroProfissionalActivity extends AppCompatActivity implements AdapterView.OnClickListener  {

    private RobotoTextView nomePagina ;
    private TextView continuar ;
    private Spinner spinnerCategoria;
    private Spinner spinnerSubCategoria;
    private SharedPreferences mPrefs;
    private Profissional profissional;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_profissional);
        ActionBar actionBar = getSupportActionBar();
        mPrefs = getSharedPreferences(PREFS_NAME, 0);
        getProfissional();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(R.drawable.arrow_back_white);
        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_title_bar, null);
        nomePagina = (RobotoTextView) v.findViewById(R.id.nome_pagina);
        nomePagina.setText("Cadastro");
        actionBar.setCustomView(v);

        continuar = (TextView) findViewById(R.id.continuar);
        continuar.setOnClickListener(this);

        spinnerCategoria = (Spinner) findViewById(R.id.categoria);
        final SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(),
                Categoria.getCategorias(), Categoria.class, false);
        spinnerCategoria.setAdapter(adapter);
        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    adapter.setItemChecked(view, position);
                    profissional.setCategoria((Categoria) adapter.getItemAtPosition(position));
                    ToastUtils.show(CadastroProfissionalActivity.this,
                            "Categoria Selecionada : " + profissional.getCategoria().getDescricao(),
                            ToastUtils.INFORMATION);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerSubCategoria = (Spinner) findViewById(R.id.sub_categoria);
        final SpinnerAdapter adapterSub = new SpinnerAdapter(getApplicationContext(),
                SubCategoria.getSubCategorias(), SubCategoria.class, false);
        spinnerSubCategoria.setAdapter(adapterSub);
        spinnerSubCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    adapterSub.setItemChecked(view, position);
                    profissional.setSubCategoria((SubCategoria) adapterSub.getItemAtPosition(position));
                    ToastUtils.show(CadastroProfissionalActivity.this,
                            "Selecionado : " + profissional.getSubCategoria().getDescricao(),
                            ToastUtils.INFORMATION);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
            case R.id.continuar:
                Intent newActivity0 = new Intent(CadastroProfissionalActivity.this, CadastroDocumentosActivity.class);
                startActivity(newActivity0);
                break;
        }

    }

    public void getProfissional() {
        profissional = SessionUtils.getProfissionalCadastro(mPrefs);
    }
}
