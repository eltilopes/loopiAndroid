package br.com.aio.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import br.com.aio.R;
import br.com.aio.fonts.RobotoTextView;
import br.com.aio.utils.PathUtils;
import br.com.aio.utils.PermissionsUtils;
import br.com.aio.utils.SessionUtils;
import br.com.aio.utils.ToastUtils;

import static br.com.aio.utils.BundleUtils.PREFS_NAME;
import static br.com.aio.utils.PermissionsUtils.ACESSO_GRAVAR_ARMAZENAMENTO_NECESSARIO;
import static br.com.aio.utils.PermissionsUtils.ACESSO_GRAVAR_ARMAZENAMENTO_PERMITIDO;
import static br.com.aio.utils.PermissionsUtils.PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_ID;
import static br.com.aio.utils.PermissionsUtils.PICKFILE_RESULT_CODE;

/**
 * Created by elton on 17/07/2017.
 */

public class ProfissionalActivity extends AppCompatActivity {

    private RobotoTextView continuar ;
    private RobotoTextView nomePagina ;
    private LinearLayout anexarFoto ;
    private LinearLayout verTaxasAnuncio ;
    private Dialog dialogTaxasAnuncio ;
    private Context context;
    private ImageView thumbnail;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profissional);
        context = this;
        mPrefs = getSharedPreferences(PREFS_NAME, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(R.drawable.arrow_back_white);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_title_bar, null);
        nomePagina = (RobotoTextView) v.findViewById(R.id.nome_pagina);
        nomePagina.setText("Profissional");

        verTaxasAnuncio = (LinearLayout) findViewById(R.id.ver_taxas_anuncio);
        verTaxasAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirDialogTaxasAnuncio();
            }
        });
        anexarFoto = (LinearLayout) findViewById(R.id.anexar_foto_perfil);
        anexarFoto.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if(!PermissionsUtils.isDeviceWriteExternalStorageGranted(context)) {
                    PermissionsUtils.requestPermissions(context, PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_ID,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
                }else{
                    showFileChooser();
                }

            }
        });
        thumbnail = (ImageView) findViewById(R.id.thumbnail);
        continuar = (RobotoTextView) findViewById(R.id.continuar);
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionUtils.setCadastroProfissional(mPrefs);
                Intent newActivity0 = new Intent(ProfissionalActivity.this, TermosActivity.class);
                startActivity(newActivity0);
            }
        });

        actionBar.setCustomView(v);
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

    private void abrirDialogTaxasAnuncio() {
        //dialogMostrarFiltro = new Dialog(this, R.style.MyDialogTheme);
        dialogTaxasAnuncio = new Dialog(this);
        dialogTaxasAnuncio.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogTaxasAnuncio.setContentView(R.layout.dialog_taxas_termos);
        dialogTaxasAnuncio.show();
        TextView alertTitle=(TextView)dialogTaxasAnuncio.getWindow().getDecorView().findViewById(R.id.dialog_title);
        alertTitle.setText(getString(R.string.taxas_anuncio));
        TextView dialogTexto =(TextView)dialogTaxasAnuncio.getWindow().getDecorView().findViewById(R.id.text_dialog);
        dialogTexto.setText(Html.fromHtml("<h4>Taxas de Anúncio conforme tipo de pagamento recebido</h4> \n" +
                "  <small> - Cartão de crédito/débito   15%</small><br> \n" +
                "  <small> - Cashback   15%</small><br>\n" +
                "  <h4>Para pagamentos realizados via:</h4> \n" +
                "  <strong><small>Cartão de crédito/débito</small></strong><br>\n" +
                "  <small> * Depósito será realizado na sua conta bancária após 30 dias.</small><br><br> \n" +
                "  <strong><small>Cashback</small></strong><br>\n" +
                "  <small> ** Depósito será realizado no seu saldo cashback imediatamente.</small><br><br> "));
        Button aplicar = (Button) dialogTaxasAnuncio.findViewById(R.id.btn_dialog);
        aplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTaxasAnuncio.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case PICKFILE_RESULT_CODE:
                if(resultCode==RESULT_OK){
                    String imagem = PathUtils.getPath(context, data.getData());
                    File imgFile = new  File(imagem);
                    if(imgFile.exists()){
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        thumbnail.setImageBitmap(myBitmap);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_ID:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ToastUtils.show(ProfissionalActivity.this, ACESSO_GRAVAR_ARMAZENAMENTO_PERMITIDO, ToastUtils.INFORMATION);
                } else {
                    ToastUtils.show(ProfissionalActivity.this, ACESSO_GRAVAR_ARMAZENAMENTO_NECESSARIO, ToastUtils.WARNING);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
