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
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import br.com.aio.R;
import br.com.aio.entity.Bairro;
import br.com.aio.fonts.RobotoTextView;
import br.com.aio.fonts.RoundedBackgroundSpan;
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
    private TextView bairrosSelecionados ;
    private TextView continuar ;
    private LayoutInflater inflator;
    private List<Bairro> bairros = new ArrayList<Bairro>();
    private Bairro bairro;
    private ImageView iconeMapaFortaleza;
    private TouchImageView mapa;
    private Bitmap mapaFortaleza;
    private int clickX = 0;
    private int clickY = 0;
    private float textSize ;

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
        //textSize = 60 / getResources().getDisplayMetrics().density; // PX to DP
        textSize = 15f;
        inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_title_bar, null);
        nomePagina = (RobotoTextView) v.findViewById(R.id.nome_pagina);
        nomePagina.setText("Cadastro");
        actionBar.setCustomView(v);

        bairrosSelecionados = (TextView) findViewById(R.id.bairros_selecionados);
        continuar = (TextView) findViewById(R.id.continuar);
        continuar.setOnClickListener(this);

        mapa = (TouchImageView) findViewById(R.id.mapa_fortaleza);
        iconeMapaFortaleza = (ImageView) findViewById(R.id.ic_mapa_fortaleza);
        LinearLayout layoutMapaFortaleza = (LinearLayout) findViewById(R.id.layout_mapa_fortaleza);
        layoutMapaFortaleza.setOnClickListener(this);
        Resources resources =  getApplicationContext().getResources();

        Bitmap aeroporto = getBitmap( R.drawable.ic_aviao, WIDHT_ICON, HEIGHT_ICON);
        Bitmap barco = getBitmap(R.drawable.ic_barco, WIDHT_ICON, HEIGHT_ICON);
        Bitmap icIracemaFor = getBitmap(R.drawable.ic_iracemafor, WIDHT_ICON, HEIGHT_ICON);
        Bitmap hospital = getBitmap(R.drawable.ic_hospital, WIDHT_ICON, HEIGHT_ICON);
        Bitmap onibus = getBitmap(R.drawable.ic_onibus, WIDHT_ICON, HEIGHT_ICON);
        Bitmap banho = getBitmap(R.drawable.ic_banho_mar, WIDHT_ICON, HEIGHT_ICON);

        Drawable drawableMapa = resources.getDrawable(R.drawable.mapa_fortaleza);
        mapaFortaleza = Bitmap.createBitmap(drawableMapa.getIntrinsicWidth(), drawableMapa.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvasMapaFortaleza = new Canvas(mapaFortaleza);
        int widhtMapa = canvasMapaFortaleza.getWidth();
        int heightMapa = canvasMapaFortaleza.getHeight();
        drawableMapa.setBounds(0, 0, widhtMapa , heightMapa);
        drawableMapa.draw(canvasMapaFortaleza);
        canvasMapaFortaleza.drawBitmap(icIracemaFor, widhtMapa/2, heightMapa/8, null);
        canvasMapaFortaleza.drawBitmap(barco, widhtMapa - widhtMapa*2/5, heightMapa/8, null);
        canvasMapaFortaleza.drawBitmap(aeroporto, widhtMapa*4/9, heightMapa*2/5, null);
        canvasMapaFortaleza.drawBitmap(hospital, widhtMapa*2/5, heightMapa*2/3, null);
        canvasMapaFortaleza.drawBitmap(onibus, widhtMapa/4, heightMapa/2, null);
        canvasMapaFortaleza.drawBitmap(banho, widhtMapa*79/100, heightMapa*37/100, null);
        setTextoNoMapa(canvasMapaFortaleza, "Praia do Futuro",60, widhtMapa*71/100, heightMapa*15/100);
        setTextoNoMapa(canvasMapaFortaleza, "Sabiaguaba",55, widhtMapa*83/100, heightMapa*47/100);
        setTextoNoMapa(canvasMapaFortaleza, "Lagoa Redonda",0, widhtMapa*74/100, heightMapa*72/100);
        setTextoNoMapa(canvasMapaFortaleza, "Barra do Ceará",10, widhtMapa*2/9, heightMapa/25);
        setTextoNoMapa(canvasMapaFortaleza, "UECE",0, widhtMapa*2/5, heightMapa/2);
        setTextoNoMapa(canvasMapaFortaleza, "Hospital Gonzaga Mota",0, widhtMapa*2/7 , heightMapa*3/4);
        setTextoNoMapa(canvasMapaFortaleza, "Terminal Siqueira",0, widhtMapa/6, heightMapa*3/5);

        mapa.setImageDrawable(new BitmapDrawable(getResources(), mapaFortaleza));
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
    private Bitmap getBitmap(int drawableId, int widhtBitmap, int heightBitmap) {
        Drawable drawable = getResources().getDrawable(drawableId);
        Bitmap bitmap = Bitmap.createBitmap(widhtBitmap,heightBitmap, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
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
                        bairro = Bairro.getBairroPorCor(fields[i].getName().toLowerCase(), idColor);
                        if(bairro != null){
                            if(bairros.contains(bairro)){
                                Toast.makeText(context,bairro.getNome()+" já foi selecionado(a)",Toast.LENGTH_SHORT).show();
                            }else{
                                bairros.add(bairro);
                                Toast.makeText(context,bairro.getNome()+" Selecionado(a)",Toast.LENGTH_SHORT).show();
                            }
                            mostrarBairro();
                          }
                    }
                }catch (Exception e){}
            }
        }
    }

    private void mostrarBairro() {
        bairrosSelecionados.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize); // Tricking the text view for getting a bigger line height
        bairrosSelecionados.setText(emboldenKeywords());
    }

    private SpannableStringBuilder emboldenKeywords() {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();

        String between = " ";
        int tagStart = 0;

        for (Bairro b : bairros) {
            String tag = b.getNome();
            stringBuilder.append(tag);
            stringBuilder.append(between);
            RoundedBackgroundSpan tagSpan = new RoundedBackgroundSpan(context, b.getIdCor(), R.color.branco, bairrosSelecionados.getTextSize() );
            stringBuilder.setSpan(tagSpan, tagStart, tagStart + tag.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            tagStart += tag.length() + between.length();
        }

        return stringBuilder;
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
            case R.id.layout_mapa_fortaleza:
                mapa.setImageDrawable(new BitmapDrawable(getResources(), mapaFortaleza));
                break;
        }

    }

}
