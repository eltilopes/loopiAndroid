package br.com.aio.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;

import br.com.aio.R;
import br.com.aio.entity.ServicoCard;
import br.com.aio.entity.Localizacao;
import br.com.aio.fonts.MaterialDesignIconsTextView;
import br.com.aio.fonts.RobotoTextView;
import br.com.aio.utils.SessionUtils;

import static br.com.aio.utils.BundleUtils.ACTIVITY_SOLICITAR_PEDIDO;
import static br.com.aio.utils.BundleUtils.PREFS_NAME;

/**
 * Created by elton on 17/07/2017.
 */

public class SolicitarPedidoActivity extends AppCompatActivity implements AdapterView.OnClickListener {

    private RobotoTextView nomePagina ;
    private TextView mapa;
    private ImageView thumbnail;
    private ImageView favorito;
    private TextView title;
    private TextView categoria;
    private TextView subCategoria;
    private TextView especialidade;
    private TextView tempo;
    private TextView localizacao;
    private TextView preco;
    private MaterialDesignIconsTextView estrela1;
    private MaterialDesignIconsTextView estrela2;
    private MaterialDesignIconsTextView estrela3;
    private MaterialDesignIconsTextView estrela4;
    private MaterialDesignIconsTextView estrela5;
    private ServicoCard servicoCard;
    private Localizacao localizacaoMapa;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_pedido);
        Intent intent = getIntent();
        mPrefs = getSharedPreferences(PREFS_NAME, 0);
        servicoCard = SessionUtils.getServicoCard(mPrefs);
        localizacaoMapa = SessionUtils.getLocalizacaoMapa(mPrefs);
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

        mapa = (TextView) findViewById(R.id.mapa);
        mapa.setText(localizacaoMapa!=null ? localizacaoMapa.getNome() : getResources().getString(R.string.informar_localizacao));
        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionUtils.setActivityAnterior(mPrefs,ACTIVITY_SOLICITAR_PEDIDO );
                Intent newActivity = new Intent(SolicitarPedidoActivity.this, MapsActivity.class);
                startActivity(newActivity);
            }
        });

        thumbnail = (ImageView) findViewById(R.id.thumbnail);
        title = (TextView) findViewById(R.id.title);
        categoria = (TextView) findViewById(R.id.card_categoria);
        tempo = (TextView) findViewById(R.id.card_tempo);
        localizacao = (TextView) findViewById(R.id.card_localizacao);
        preco = (TextView) findViewById(R.id.card_preco);
        subCategoria = (TextView) findViewById(R.id.card_sub_categoria);
        especialidade = (TextView) findViewById(R.id.card_especialidade);
        favorito = (ImageView) findViewById(R.id.card_favorito);
        estrela1 = (MaterialDesignIconsTextView) findViewById(R.id.card_estrela_1);
        estrela2 = (MaterialDesignIconsTextView) findViewById(R.id.card_estrela_2);
        estrela3 = (MaterialDesignIconsTextView) findViewById(R.id.card_estrela_3);
        estrela4 = (MaterialDesignIconsTextView) findViewById(R.id.card_estrela_4);
        estrela5 = (MaterialDesignIconsTextView) findViewById(R.id.card_estrela_5);

        if (!TextUtils.isEmpty(servicoCard.getThumbnail())) {
            Picasso.with(getApplicationContext()).load(servicoCard.getThumbnail())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(thumbnail);
        }

        //Setting text view title
        title.setText(Html.fromHtml(servicoCard.getTitle()));
        categoria.setText(Html.fromHtml(servicoCard.getCategoria().getDescricao()));
        preco.setText(Html.fromHtml(NumberFormat.getCurrencyInstance().format((servicoCard.getPreco()/100))));
        tempo.setText(Html.fromHtml("Em até " +servicoCard.getDuracao()));
        localizacao.setText(Html.fromHtml(servicoCard.getDistancia()));
        subCategoria.setText(Html.fromHtml(servicoCard.getSubCategoria().getDescricao()));
        especialidade.setText(Html.fromHtml(servicoCard.getEspecialidade().getDescricao()));
        favorito.setImageResource(servicoCard.getFavorito()? R.drawable.ic_favorite_full : R.drawable.ic_favorite_empty);
        estrela1.setText(servicoCard.getEstrelas()>0? R.string.material_icon_star : R.string.material_icon_star_border);
        estrela2.setText(servicoCard.getEstrelas()>1? R.string.material_icon_star : R.string.material_icon_star_border);
        estrela3.setText(servicoCard.getEstrelas()>2? R.string.material_icon_star : R.string.material_icon_star_border);
        estrela4.setText(servicoCard.getEstrelas()>3? R.string.material_icon_star : R.string.material_icon_star_border);
        estrela5.setText(servicoCard.getEstrelas()>4? R.string.material_icon_star : R.string.material_icon_star_border);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        SessionUtils.setServicoCard(mPrefs,null);
        SessionUtils.setActivityAnterior(mPrefs,ACTIVITY_SOLICITAR_PEDIDO);
        Intent newActivity = new Intent(SolicitarPedidoActivity.this, ListagemActivity.class);
        startActivity(newActivity);
    }
}
