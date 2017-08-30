package br.com.aio.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import br.com.aio.R;
import br.com.aio.adapter.LazyAdapterDocumentos;
import br.com.aio.fonts.RobotoTextView;
import br.com.aio.utils.PathUtils;

/**
 * Created by elton on 17/07/2017.
 */

public class CadastroDocumentosActivity extends AppCompatActivity implements AdapterView.OnClickListener  {

    private static final String TAG = "CadastroDocumentos";
    private RobotoTextView nomePagina ;
    private TextView continuar ;
    private ImageView imageView;

    private ListView listaDocumentos;
    private LayoutInflater inflator;

    public static final int PICKFILE_RESULT_CODE = 1;
    private static final int FILE_SELECT_CODE = 0;
    public static final String KEY_NOME_DOCUMENTO = "nomeDocumento";
    public static final String KEY_ID_DOCUMENTO = "idDocumento";
    private ArrayList<HashMap<String, String>> documentosMap = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_documentos);
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
        setListaDocumentos();

        continuar = (TextView) findViewById(R.id.continuar);
        continuar.setOnClickListener(this);

        imageView = (ImageView) findViewById(R.id.imagem);
    }


    private void setListaDocumentos() {
        getDocumentos();
        mostrarListaDocumentos();
    }

    private void getDocumentos() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(KEY_NOME_DOCUMENTO, "RG");
        map.put(KEY_ID_DOCUMENTO, "1");
        documentosMap.add(map);

        map = new HashMap<String, String>();
        map.put(KEY_NOME_DOCUMENTO, "CPF");
        map.put(KEY_ID_DOCUMENTO, "2");
        documentosMap.add(map);


        map = new HashMap<String, String>();
        map.put(KEY_NOME_DOCUMENTO, "CTPS");
        map.put(KEY_ID_DOCUMENTO, "3");
        documentosMap.add(map);


    }

    private void mostrarListaDocumentos() {
        listaDocumentos = (ListView) findViewById(R.id.lista_documentos);
        View header = (View) inflator.inflate(R.layout.header_documentos, null);
        listaDocumentos.addHeaderView(header);
        listaDocumentos.setHeaderDividersEnabled(true);
        listaDocumentos.setHorizontalScrollBarEnabled(true);
        listaDocumentos.setAdapter(new LazyAdapterDocumentos(this, documentosMap));
        listaDocumentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(), parent.getItemAtPosition(position).toString() + " selecionado!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch(requestCode){
            case PICKFILE_RESULT_CODE:
                if(resultCode==RESULT_OK){
                    String imagem = PathUtils.getPath(this, data.getData());
                    File imgFile = new  File(imagem);
                    if(imgFile.exists()){
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        imageView.setImageBitmap(myBitmap);
                    }
                }
                break;

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
                Intent newActivity0 = new Intent(CadastroDocumentosActivity.this, ConfirmarSaqueActivity.class);
                startActivity(newActivity0);
                break;
        }

    }
}
