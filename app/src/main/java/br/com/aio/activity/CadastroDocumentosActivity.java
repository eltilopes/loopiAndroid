package br.com.aio.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.aio.R;
import br.com.aio.entity.Documento;
import br.com.aio.fonts.RobotoTextView;
import br.com.aio.utils.DocumentoUtils;
import br.com.aio.utils.PermissionsUtils;
import br.com.aio.utils.ToastUtils;

import static br.com.aio.utils.PermissionsUtils.ACESSO_CAMERA_NECESSARIO;
import static br.com.aio.utils.PermissionsUtils.ACESSO_CAMERA_PERMITIDO;
import static br.com.aio.utils.PermissionsUtils.ACESSO_GRAVAR_ARMAZENAMENTO_NECESSARIO;
import static br.com.aio.utils.PermissionsUtils.ACESSO_GRAVAR_ARMAZENAMENTO_PERMITIDO;
import static br.com.aio.utils.PermissionsUtils.ACESSO_LER_ARMAZENAMENTO_NECESSARIO;
import static br.com.aio.utils.PermissionsUtils.ACESSO_LER_ARMAZENAMENTO_PERMITIDO;
import static br.com.aio.utils.PermissionsUtils.PERMISSIONS_REQUEST_CAMERA_ID;
import static br.com.aio.utils.PermissionsUtils.PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_ID;
import static br.com.aio.utils.PermissionsUtils.PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_ID;
import static br.com.aio.utils.PermissionsUtils.PICKFILE_RESULT_CODE;

/**
 * Created by elton on 17/07/2017.
 */

public class CadastroDocumentosActivity extends AppCompatActivity implements AdapterView.OnClickListener  {

    private static final String TAG = "CadastroDocumentos";
    private Context context;
    private RobotoTextView nomePagina ;
    private TextView continuar ;
    private Uri imageUri;;
    private ListView listaDocumentos;
    private LayoutInflater inflator;
    private View headerDocumentos;
    private Dialog dialogImagem;
    private static final int TAKE_PICTURE = 2;
    private List<Documento> documentos;
    private Documento documento;
    private boolean acessoCamera;
    private boolean acessoLerSD;
    private boolean acessoEscreverSD;
    private File photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_documentos);
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
        setListaDocumentos();
        continuar = (TextView) findViewById(R.id.continuar);
        continuar.setOnClickListener(this);
    }


    private void setListaDocumentos() {
        getDocumentos();
        mostrarListaDocumentos();
    }

    private void getDocumentos() {
        documentos = new ArrayList<Documento>();
        documentos.add(new Documento( 1l,"RG"));
        documentos.add(new Documento( 2l,"CTPS"));
        documentos.add(new Documento( 3l,"CPF"));
    }

    private void mostrarListaDocumentos() {
        listaDocumentos = (ListView) findViewById(R.id.lista_documentos);
        if(headerDocumentos==null){
            headerDocumentos = (View) inflator.inflate(R.layout.header_documentos, null);
            listaDocumentos.addHeaderView(headerDocumentos);
        }
        listaDocumentos.setHeaderDividersEnabled(true);
        listaDocumentos.setHorizontalScrollBarEnabled(true);
        listaDocumentos.setAdapter(new DocumentosAdapter());
    }

    class DocumentosAdapter extends ArrayAdapter<Documento> {
        DocumentosAdapter() {
            super(context ,R.layout.list_row_documentos, documentos);
        }

        public View getView(int position, final View convertView, ViewGroup parent) {
            View vi=convertView;

            if(convertView==null) {
                vi = inflator.inflate(R.layout.list_row_documentos, null);
                TextView nomeDocumento = (TextView) vi.findViewById(R.id.nome_documento);
                TextView anexo = (TextView) vi.findViewById(R.id.anexo_documento);
                TextView camera = (TextView) vi.findViewById(R.id.camera_documento);
                TextView checkDocumento = (TextView) vi.findViewById(R.id.check_documento);

                documento = new Documento();
                documento = documentos.get(position);

                nomeDocumento.setText(documento.getNome());
                anexo.setText(documento.getId().toString());
                camera.setText(documento.getId().toString());
                anexo.setOnClickListener(new View.OnClickListener() {
                    @Override

                    public void onClick(View v) {
                        if(!PermissionsUtils.isDeviceWriteExternalStorageGranted(context)) {
                            PermissionsUtils.requestPermissions(context, PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_ID,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
                        }else{
                                TextView anexo = (TextView) v;
                                documento = DocumentoUtils.getDocumento(documentos, new Long(anexo.getText().toString()));
                                showFileChooser();
                        }

                    }
                });
                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!PermissionsUtils.isDeviceCameraGranted(context)) {
                            PermissionsUtils.requestPermissions(context, PERMISSIONS_REQUEST_CAMERA_ID,
                                    Manifest.permission.CAMERA,new String[]{Manifest.permission.CAMERA});

                        }else if(!PermissionsUtils.isDeviceReadExternalStorageGranted(context)) {
                            PermissionsUtils.requestPermissions(context, PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_ID,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});


                        }else{
                            TextView camera = (TextView) v;
                            documento = DocumentoUtils.getDocumento(documentos, new Long(camera.getText().toString()));
                            takePhoto(v);
                        }
                    }
                });
                if(documento.getArquivo() != null  && documento.getArquivo().exists()){
                    nomeDocumento.setTextColor(getApplicationContext().getResources().getColor(R.color.textColorInfoVerde));
                    checkDocumento.setBackgroundTintList(getResources().getColorStateList(R.color.textColorInfoVerde));
                }

            }
            return vi;
        }
    }
    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            this.startActivityForResult(intent,PICKFILE_RESULT_CODE);

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Instale um gerenciador de arquivos no seu aplicativo!", Toast.LENGTH_SHORT).show();
        }
    }

    public void takePhoto(View view) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            photo = new File(Environment.getExternalStorageDirectory(),  documento.getNome() + ".jpg");
            imageUri =  FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".br.com.aio.provider", photo);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra("android.intent.extras.FLASH_MODE_OFF",1);
            startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void mostrarDialogImagem(Bitmap myBitmap) {
        dialogImagem = new Dialog(this);
        dialogImagem.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogImagem.setContentView(R.layout.dialog_imagem_documento);
        dialogImagem.show();
        TextView alertTitle=(TextView)dialogImagem.getWindow().getDecorView().findViewById(R.id.dialog_title);
        TextView continuar=(TextView)dialogImagem.findViewById(R.id.continuar);
        alertTitle.setText(documento.getNome() + " - " + alertTitle.getText());
        ImageView imagemCarregada = (ImageView) dialogImagem.findViewById(R.id.imagem_carregada);
        imagemCarregada.setImageBitmap(myBitmap);
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogImagem.dismiss();
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
                Intent newActivity0 = new Intent(CadastroDocumentosActivity.this, CadastroAbrangenciaActivity.class);
                startActivity(newActivity0);
                break;
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_ID:
                acessoEscreverSD = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (acessoEscreverSD) {
                    ToastUtils.show(CadastroDocumentosActivity.this, ACESSO_GRAVAR_ARMAZENAMENTO_PERMITIDO, ToastUtils.INFORMATION);
                } else {
                    ToastUtils.show(CadastroDocumentosActivity.this, ACESSO_GRAVAR_ARMAZENAMENTO_NECESSARIO, ToastUtils.WARNING);
                }
                break;
            case PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_ID:
                acessoLerSD = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (acessoLerSD) {
                    ToastUtils.show(CadastroDocumentosActivity.this, ACESSO_LER_ARMAZENAMENTO_PERMITIDO, ToastUtils.INFORMATION);
                } else {
                    ToastUtils.show(CadastroDocumentosActivity.this, ACESSO_LER_ARMAZENAMENTO_NECESSARIO, ToastUtils.WARNING);
                }
                break;
            case PERMISSIONS_REQUEST_CAMERA_ID:
                acessoCamera = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (acessoCamera) {
                    ToastUtils.show(CadastroDocumentosActivity.this, ACESSO_CAMERA_PERMITIDO, ToastUtils.INFORMATION);
                } else {
                    ToastUtils.show(CadastroDocumentosActivity.this, ACESSO_CAMERA_NECESSARIO, ToastUtils.WARNING);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
