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
import android.widget.TextView;

import java.util.ArrayList;

import br.com.aio.R;
import br.com.aio.entity.Categoria;
import br.com.aio.entity.Especialidade;
import br.com.aio.entity.Profissional;
import br.com.aio.entity.ServicoProfissional;
import br.com.aio.entity.SubCategoria;
import br.com.aio.entity.UsuarioSession;
import br.com.aio.fonts.RobotoTextView;
import br.com.aio.utils.SessionUtils;

import static br.com.aio.utils.BundleUtils.PREFS_NAME;

/**
 * Created by elton on 17/07/2017.
 */

public class CadastroServicoActivity extends AppCompatActivity implements AdapterView.OnClickListener  {

    private RobotoTextView nomePagina ;
    private TextView historico ;
    private TextView novaSolicitacao ;
    private TextView cadastre ;
    private SharedPreferences mPrefs;
    private UsuarioSession usuarioSession;
    private Profissional profissional;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_servico);
        mPrefs = getSharedPreferences(PREFS_NAME, 0);
        getUsuarioLogado();
        getProfissional();
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

    private void getProfissional() {
        profissional = new Profissional(1,usuarioSession,
                new Categoria(1,"Saúde"), new SubCategoria(1,"Médico"),
                new Especialidade(1,"Dermatologista"),new ArrayList<ServicoProfissional>());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        SessionUtils.setProfissionalCadastro(mPrefs,profissional);
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

    public void getUsuarioLogado() {
        usuarioSession = SessionUtils.getUsuarioSession(mPrefs);
    }

}
