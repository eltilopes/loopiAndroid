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
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.aio.R;
import br.com.aio.entity.Categoria;
import br.com.aio.entity.Profissional;
import br.com.aio.fonts.RobotoTextView;
import br.com.aio.utils.SessionUtils;
import br.com.aio.utils.SpinnerUtils;
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
    private List<Categoria> categorias;

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
        categorias = getCategorias(Categoria.getCategorias());
        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_title_bar, null);
        nomePagina = (RobotoTextView) v.findViewById(R.id.nome_pagina);
        nomePagina.setText("Cadastro");
        actionBar.setCustomView(v);

        continuar = (TextView) findViewById(R.id.continuar);
        continuar.setOnClickListener(this);

        spinnerCategoria = (Spinner) findViewById(R.id.categoria);
        ArrayAdapter<Categoria> adapter = new ArrayAdapter<Categoria>(this, android.R.layout.simple_spinner_item, categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);
        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    profissional.setCategoria(Categoria.getCategoria(categorias, parent.getItemAtPosition(position).toString()));
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
        ArrayAdapter<CharSequence> adapterSub = ArrayAdapter.createFromResource(this,
                R.array.list_sub_categoria, android.R.layout.simple_spinner_item);
        adapterSub.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubCategoria.setAdapter(adapterSub);
        spinnerSubCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(),
                        "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private List<Categoria> getCategorias(List<Categoria> categorias) {
        List<Categoria> lista = new ArrayList<Categoria>();
        try {
            lista.add((Categoria) SpinnerUtils.getObjectDefaultSpinner(Categoria.class));
        }catch (Exception e){
            e.printStackTrace();
        }
        lista.addAll(categorias);
        return lista;
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
