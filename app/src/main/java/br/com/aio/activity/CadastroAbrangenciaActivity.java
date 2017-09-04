package br.com.aio.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.List;

import br.com.aio.R;
import br.com.aio.entity.Bairro;
import br.com.aio.fonts.RobotoTextView;
import br.com.aio.view.TouchImageView;

/**
 * Created by elton on 17/07/2017.
 */

public class CadastroAbrangenciaActivity extends AppCompatActivity implements AdapterView.OnClickListener  {

    private static final String TAG = "CadastroAbrangencia";
    private static final String BAIRRO = "bairro";
    private static final String REGIONAL = "regional";
    private Context context;
    private RobotoTextView nomePagina ;
    private TextView continuar ;
    private ListView listaBairros;
    private LayoutInflater inflator;
    private View headerBairros;
    private List<Bairro> bairros;
    private Bairro bairro;
    private TouchImageView mapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_abrangencia);
        context = this;
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(R.drawable.arrow_back_white);

        inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_title_bar, null);
        nomePagina = (RobotoTextView) v.findViewById(R.id.nome_pagina);
        nomePagina.setText("Cadastro");
        actionBar.setCustomView(v);
        //setListaBairros();

        continuar = (TextView) findViewById(R.id.continuar);
        continuar.setOnClickListener(this);
        mapa = (TouchImageView) findViewById(R.id.mapa_fortaleza);
        mapa.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int action = event.getAction();

                final int evX = (int) event.getX();
                final int evY = (int) event.getY();

                switch (action) {
                    case MotionEvent.ACTION_DOWN :
                        break;
                    case MotionEvent.ACTION_UP :
                        mapa.setDrawingCacheEnabled(true);
                        Bitmap imgbmp = Bitmap.createBitmap(mapa.getDrawingCache());
                        mapa.setDrawingCacheEnabled(false);
                        int pxl = imgbmp.getPixel(evX, evY);
                        if(pxl!=0){
                            getCorBairro(pxl);
                            processarMapa(pxl);
                        }
                        break;
                }
                return true;
            }
        });

    }

    private void processarMapa(int pxl) {
        if(pxl==getApplicationContext().getResources().getColor(R.color.regional_1)) {
            mapa.setImageResource(R.drawable.regional_um);
        } else if(pxl==getApplicationContext().getResources().getColor(R.color.regional_2)) {
            mapa.setImageResource(R.drawable.regional_dois);
        }else if(pxl==getApplicationContext().getResources().getColor(R.color.regional_3)) {
            mapa.setImageResource(R.drawable.regional_tres);
        }else if(pxl==getApplicationContext().getResources().getColor(R.color.regional_4)) {
            mapa.setImageResource(R.drawable.regional_quatro);
        }else if(pxl==getApplicationContext().getResources().getColor(R.color.regional_5)) {
            mapa.setImageResource(R.drawable.regional_cinco);
        }else if(pxl==getApplicationContext().getResources().getColor(R.color.regional_6)) {
            mapa.setImageResource(R.drawable.regional_seis);
        }else if(pxl==getApplicationContext().getResources().getColor(R.color.regional_centro)) {
            mapa.setImageResource(R.drawable.regional_centro);
        }
    }

    private void getCorBairro(int corBairroSelecionado) {
        Field [] fields = R.color.class.getDeclaredFields();
        int color = 0;
        for(int i=0; i<fields.length; i++) {
            if(fields[i].getName().toLowerCase().contains(BAIRRO) || fields[i].getName().toLowerCase().contains(REGIONAL)){
                try {
                    color = getApplicationContext().getResources().getColor(fields[i].getInt(null));
                    if(corBairroSelecionado==color){
                        Toast.makeText(context,fields[i].getName(),Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){}
            }
        }
    }

    private void setListaBairros() {
        bairros = Bairro.getBairros();
        mostrarListaBairros();
    }


    private void mostrarListaBairros() {
        //listaBairros= (ListView) findViewById(R.id.lista_bairoos);
        if(headerBairros==null){
            headerBairros = (View) inflator.inflate(R.layout.header_documentos, null);
            listaBairros.addHeaderView(headerBairros);
        }
        listaBairros.setHeaderDividersEnabled(true);
        listaBairros.setHorizontalScrollBarEnabled(true);
        listaBairros.setAdapter(new CadastroAbrangenciaActivity.BairrosAdapter());
    }

    class BairrosAdapter extends ArrayAdapter<Bairro> {
        BairrosAdapter() {
            super(context ,R.layout.list_row_bairro, bairros);
        }

        public View getView(int position, final View convertView, ViewGroup parent) {
            View vi=convertView;

            if(convertView==null) {
                vi = inflator.inflate(R.layout.list_row_bairro, null);
                TextView nomeBairro = (TextView) vi.findViewById(R.id.nome_bairro);
                CheckBox atende = (CheckBox) vi.findViewById(R.id.atende);

                bairro = new Bairro();
                bairro = bairros.get(position);

                nomeBairro.setText(bairro.getNome());
                atende.setText(bairro.getId().toString());

            }
            return vi;
        }
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
                Intent newActivity0 = new Intent(CadastroAbrangenciaActivity.this, CadastroAbrangenciaActivity.class);
                startActivity(newActivity0);
                break;
        }

    }

}
