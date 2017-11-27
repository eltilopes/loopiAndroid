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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.aio.R;
import br.com.aio.adapter.ServicoProfissionalAdapter;
import br.com.aio.entity.Localizacao;
import br.com.aio.entity.ServicoCard;
import br.com.aio.entity.ServicoProfissional;
import br.com.aio.fonts.MaterialDesignIconsTextView;
import br.com.aio.fonts.RobotoTextView;
import br.com.aio.utils.SessionUtils;
import br.com.aio.utils.ViewUtils;
import br.com.aio.view.ExpandedListView;

import static br.com.aio.utils.BundleUtils.ACTIVITY_SOLICITAR_PEDIDO;
import static br.com.aio.utils.BundleUtils.PREFS_NAME;

/**
 * Created by elton on 17/07/2017.
 */

public class SolicitarPedidoActivity extends AppCompatActivity implements View.OnClickListener,ServicoProfissionalAdapter.OnItemClickListener {

    private RobotoTextView nomePagina ;
    private TextView mapa;
    private ImageView thumbnail;
    private ImageView favorito;
    private TextView title;
    private TextView descricao;
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
    private ExpandedListView listaServicos;
    private List<ServicoProfissional> servicoSelecionados;
    private ServicoProfissionalAdapter servicoProfissionalAdapter;
    private LinearLayout layoutListaServicos;
    private RobotoTextView totalListaServicos;
    private String infoTotalListaServicos;
    private ServicoCard servicoCard;
    private Localizacao localizacaoMapa;
    private SharedPreferences mPrefs;
    private boolean multiServicos;

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
        multiServicos = servicoCard.getServicos() != null ?
                (servicoCard.getServicos().isEmpty() ? false : (servicoCard.getServicos().size() > 1 ? true : false)) : false;

        thumbnail = (ImageView) findViewById(R.id.thumbnail);
        descricao = (TextView) findViewById(R.id.card_descricao);
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
        layoutListaServicos = (LinearLayout) findViewById(R.id.layout_lista_servicos);
        layoutListaServicos.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(servicoCard.getThumbnail())) {
            Picasso.with(getApplicationContext()).load(servicoCard.getThumbnail())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(thumbnail);
        }

        //Setting text view title
        title.setText(Html.fromHtml(servicoCard.getTitle()));
        descricao.setText(multiServicos ? "" :Html.fromHtml(servicoCard.getDescricao()));
        categoria.setText(Html.fromHtml(servicoCard.getCategoria().getDescricao()));
        preco.setText(multiServicos ? "" :Html.fromHtml(NumberFormat.getCurrencyInstance().format((servicoCard.getPreco()/100))));
        tempo.setText(Html.fromHtml(servicoCard.getDuracao()));
        localizacao.setText(Html.fromHtml(servicoCard.getDistancia()));
        subCategoria.setText(Html.fromHtml(servicoCard.getSubCategoria().getDescricao()));
        especialidade.setText(Html.fromHtml(servicoCard.getEspecialidade().getDescricao()));
        favorito.setImageResource(servicoCard.getFavorito()? R.drawable.ic_thumb_up_full : R.drawable.ic_thumb_up_empty);
        estrela1.setText(servicoCard.getEstrelas()>0? R.string.material_icon_star : R.string.material_icon_star_border);
        estrela2.setText(servicoCard.getEstrelas()>1? R.string.material_icon_star : R.string.material_icon_star_border);
        estrela3.setText(servicoCard.getEstrelas()>2? R.string.material_icon_star : R.string.material_icon_star_border);
        estrela4.setText(servicoCard.getEstrelas()>3? R.string.material_icon_star : R.string.material_icon_star_border);
        estrela5.setText(servicoCard.getEstrelas()>4? R.string.material_icon_star : R.string.material_icon_star_border);
        if(multiServicos){
            layoutListaServicos.setVisibility(View.VISIBLE);
            servicoSelecionados = new ArrayList<>();
            listaServicos = (ExpandedListView) findViewById(R.id.card_lista_servicos);
            listaServicos.setHeaderDividersEnabled(true);
            totalListaServicos = (RobotoTextView) findViewById(R.id.total_lista_servicos);
            servicoProfissionalAdapter  = new ServicoProfissionalAdapter(this, servicoCard.getServicos());
            listaServicos.setAdapter(servicoProfissionalAdapter);
            servicoProfissionalAdapter.setOnItemClickListener(SolicitarPedidoActivity.this);
            setTotalListaServicos();
        }

    }

    private void setTotalListaServicos() {
        infoTotalListaServicos = getString(R.string.servicos_selecionados) + " " + servicoSelecionados.size()
                + " - " + getTotalServicos();
        totalListaServicos.setText(infoTotalListaServicos);
    }

    private String getTotalServicos( ) {
        Double valorTotal = 0D;
        for(ServicoProfissional s : servicoSelecionados){
            valorTotal = valorTotal + s.getValor();
        }
        return ViewUtils.getValorFormatado( valorTotal );
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

    @Override
    public void OnItemClickListener(int position, int id) {
        ServicoProfissional servicoProfissional = servicoCard.getServicos().get(position);
        if(servicoSelecionados.contains(servicoProfissional)){
            servicoSelecionados.remove(servicoProfissional);
        }else{
            servicoSelecionados.add(servicoProfissional);
        }
        setTotalListaServicos();
    }
}
