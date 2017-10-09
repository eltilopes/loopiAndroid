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
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import br.com.aio.R;
import br.com.aio.entity.ServicoCard;
import br.com.aio.fonts.RobotoTextView;
import br.com.aio.utils.SessionUtils;
import br.com.aio.utils.ToastUtils;
import br.com.aio.view.MySwitch;

import static br.com.aio.utils.BundleUtils.ACTIVITY_INICIAR_ATENDIMENTO;
import static br.com.aio.utils.BundleUtils.PREFS_NAME;


/**
 * Created by elton on 04/10/2017.
 */

public class IniciarAtendimentoActivity extends AppCompatActivity implements View.OnClickListener ,MySwitch.OnChangeAttemptListener, CompoundButton.OnCheckedChangeListener {

    private MySwitch slideToUnLock;
    private RobotoTextView nomePagina ;
    private ImageView foto;
    private SharedPreferences mPrefs;
    private ServicoCard servicoCard;
    private RobotoTextView cancelar;
    private RobotoTextView nomeCliente;
    private RobotoTextView txtHeadline;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_atendimento);
        nomeCliente = (RobotoTextView) findViewById(R.id.nomeCliente);
        txtHeadline = (RobotoTextView) findViewById(R.id.txt_headline);
        txtHeadline.setText("Aguardando Atendimento");
        cancelar = (RobotoTextView) findViewById(R.id.cancelar);
        cancelar.setOnClickListener(this);
        mPrefs = getSharedPreferences(PREFS_NAME, 0);
        servicoCard = SessionUtils.getServicoCard(mPrefs);
        nomeCliente.setText(servicoCard.getTitle());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(R.drawable.arrow_back_white);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_title_bar, null);
        nomePagina = (RobotoTextView) v.findViewById(R.id.nome_pagina);
        nomePagina.setText("Iniciar Atendimento");
        actionBar.setCustomView(v);
        slideToUnLock = (MySwitch)findViewById(R.id.slideToUnLock);
        slideToUnLock.toggle();
        slideToUnLock.setOnCheckedChangeListener(this);
        slideToUnLock.setTag(getString(R.string.dirigir_cliente));
        foto = (ImageView) findViewById(R.id.foto);
        Picasso.with(this).load(servicoCard.getThumbnail())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(foto);
    }



    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!isChecked){
            if(slideToUnLock.getTag().equals(getString(R.string.dirigir_cliente))){
                slideToUnLock.setmThumbDrawable(getDrawable(R.drawable.switch_thumb_arrow_verde));
                slideToUnLock.setTextOn(getString(R.string.concluir_atendimento));
                slideToUnLock.toggle();
                slideToUnLock.setTag(getString(R.string.concluir_atendimento));
                txtHeadline.setText("Aguardando Finalização");
                ToastUtils.show(IniciarAtendimentoActivity.this, getString(R.string.dirigir_cliente), ToastUtils.INFORMATION);
            }else if(slideToUnLock.getTag().equals(getString(R.string.concluir_atendimento))){
                ToastUtils.show(IniciarAtendimentoActivity.this, getString(R.string.concluir_atendimento), ToastUtils.INFORMATION);
                onBackPressed();
            }

        }
        else {
            ToastUtils.show(IniciarAtendimentoActivity.this, "Recusado", ToastUtils.WARNING);
        }
    }

    @Override
    public void onChangeAttempted(boolean isChecked) {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        SessionUtils.setActivityAnterior(mPrefs, ACTIVITY_INICIAR_ATENDIMENTO);
        Intent newActivity = new Intent(IniciarAtendimentoActivity.this, ListagemActivity.class);
        startActivity(newActivity);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.cancelar:
                moveTaskToBack(true);
                break;
        }

    }
}