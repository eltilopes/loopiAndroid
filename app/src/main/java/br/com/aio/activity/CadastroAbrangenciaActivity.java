package br.com.aio.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import java.util.ArrayList;
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
    private static final int WIDHT_ICON = 48;
    private static final int HEIGHT_ICON = 48;
    private Context context;
    private RobotoTextView nomePagina ;
    private TextView continuar ;
    private ListView listaBairros;
    private LayoutInflater inflator;
    private View headerBairros;
    private List<Bairro> bairros = new ArrayList<Bairro>();
    private Bairro bairro;
    private TouchImageView mapa;
    private int clickX = 0;
    private int clickY = 0;

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

        continuar = (TextView) findViewById(R.id.continuar);
        continuar.setOnClickListener(this);

        mapa = (TouchImageView) findViewById(R.id.mapa_fortaleza);
        Resources resources =  getApplicationContext().getResources();

        Bitmap aeroporto = getBitmap( R.drawable.ic_aviao, WIDHT_ICON, HEIGHT_ICON);
        Bitmap barco = getBitmap(R.drawable.ic_barco, WIDHT_ICON, HEIGHT_ICON);
        Bitmap icIracemaFor = getBitmap(R.drawable.ic_iracemafor, WIDHT_ICON, HEIGHT_ICON);

        Drawable drawableMapa = resources.getDrawable(R.drawable.mapa_fortaleza);
        Bitmap mapaFortaleza = Bitmap.createBitmap(drawableMapa.getIntrinsicWidth(), drawableMapa.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvasMapaFortaleza = new Canvas(mapaFortaleza);
        drawableMapa.setBounds(0, 0, canvasMapaFortaleza.getWidth() , canvasMapaFortaleza.getHeight());
        drawableMapa.draw(canvasMapaFortaleza);
        canvasMapaFortaleza.drawBitmap(icIracemaFor, canvasMapaFortaleza.getWidth()/2, canvasMapaFortaleza.getHeight()/8, null);
        canvasMapaFortaleza.drawBitmap(barco, canvasMapaFortaleza.getWidth() - canvasMapaFortaleza.getWidth()*2/5, canvasMapaFortaleza.getHeight()/8, null);
        canvasMapaFortaleza.drawBitmap(aeroporto, canvasMapaFortaleza.getWidth()*4/9, canvasMapaFortaleza.getHeight()*2/5, null);
        setTextoNoMapa(canvasMapaFortaleza, "Praia do Futuro",60, (canvasMapaFortaleza.getWidth()*5/7) +30, canvasMapaFortaleza.getHeight()/4);
        setTextoNoMapa(canvasMapaFortaleza, "Barra do Ceará",10, canvasMapaFortaleza.getWidth()*2/9, canvasMapaFortaleza.getHeight()/25);
        setTextoNoMapa(canvasMapaFortaleza, "UECE",0, canvasMapaFortaleza.getWidth()*2/5, canvasMapaFortaleza.getHeight()/2);

        Drawable d = new BitmapDrawable(getResources(), mapaFortaleza);
        mapa.setImageDrawable(d);
        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapa.setDrawingCacheEnabled(true);
                Bitmap imgbmp = Bitmap.createBitmap(mapa.getDrawingCache());
                mapa.setDrawingCacheEnabled(false);
                if(clickX != 0 && clickY != 0){
                    int pxl = imgbmp.getPixel(clickX, clickY);
                    if(pxl!=0){
                        getCorBairro(pxl);
                        processarMapa(pxl);
                    }
                }
            }
        });
        mapa.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int action = event.getAction();
                clickX = (int) event.getX();
                clickY = (int) event.getY();
                return true;
            }
        });
    }

    private void setTextoNoMapa(Canvas canvas,String texto, int rotation, int locationX, int locationY) {
        Paint paint = new Paint();
        paint.setTextSize(30);
        paint.setStrokeWidth(3);
        paint.setColor(Color.BLACK);
        canvas.save();
        canvas.rotate(rotation, locationX, locationY);
        canvas.drawText(texto, locationX, locationY, paint);
        canvas.restore();
    }

    @NonNull
    private Bitmap getBitmap(int drawable, int widhtBitmap, int heightBitmap) {
        Resources resources =  getApplicationContext().getResources();
        Drawable drawablePraia = resources.getDrawable(drawable);
        Bitmap bitmap = Bitmap.createBitmap(widhtBitmap,heightBitmap, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawablePraia.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawablePraia.draw(canvas);
        return bitmap;
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
                    int idColor = fields[i].getInt(null);
                    color = getApplicationContext().getResources().getColor(idColor);
                    if(corBairroSelecionado==color){
                        bairro = Bairro.getBairroPorCor(fields[i].getName().toLowerCase());
                        if(bairro != null){
                            bairros.add(bairro);
                            mostrarListaBairros();
                            Toast.makeText(context,bairro.getNome()+" selecionado",Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e){}
            }
        }
    }

    private void mostrarListaBairros() {
        listaBairros= (ListView) findViewById(R.id.lista_bairoos);
        if(headerBairros==null){
            headerBairros = (View) inflator.inflate(R.layout.header_bairros, null);
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
                Intent newActivity0 = new Intent(CadastroAbrangenciaActivity.this, ProfissionalActivity.class);
                startActivity(newActivity0);
                break;
        }

    }

}
