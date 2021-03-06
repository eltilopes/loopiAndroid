package br.com.aio.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import br.com.aio.R;
import br.com.aio.entity.UsuarioSession;
import br.com.aio.fonts.RobotoTextView;
import br.com.aio.utils.PathUtils;
import br.com.aio.utils.PermissionsUtils;
import br.com.aio.utils.SessionUtils;

import static br.com.aio.utils.BundleUtils.ACTIVITY_NAO_TENHO_CONVITE;
import static br.com.aio.utils.BundleUtils.PREFS_NAME;
import static br.com.aio.utils.PermissionsUtils.PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_ID;
import static br.com.aio.utils.PermissionsUtils.PICKFILE_RESULT_CODE;

/**
 * Created by elton on 17/07/2017.
 */

public class MeuPerfilActivity extends AppCompatActivity{

    private static final String CHAVE = "CHAVE:";
    private static final String ID_CONVITE = "ID_CONVITE:";

    private Dialog dialogInfo;
    private RobotoTextView nomePagina ;
    private RobotoTextView subHeader ;
    private SharedPreferences mPrefs;
    private boolean novoUsuario = false;
    private UsuarioSession usuarioSession;
    private ImageView thumbnail;
    private EditText editTextCpf;
    private EditText editTextNome;
    private EditText editTextEmail;
    private EditText editTextSenha;
    private String deepLinkFireBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meu_perfil);
        mPrefs = getSharedPreferences(PREFS_NAME, 0);
        novoUsuario = ACTIVITY_NAO_TENHO_CONVITE.equals(SessionUtils.getNomeActivityAnterior(mPrefs));
        usuarioSession = SessionUtils.getUsuarioSession(mPrefs);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(R.drawable.arrow_back_white);
        thumbnail = (ImageView) findViewById(R.id.thumbnail);
        thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if(!PermissionsUtils.isDeviceWriteExternalStorageGranted(getApplicationContext())) {
                    PermissionsUtils.requestPermissions(getApplicationContext(), PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_ID,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
                }else{
                    showFileChooser();
                }

            }
        });
        editTextCpf= (EditText) findViewById(R.id.edit_text_cpf);
        editTextCpf.setFocusableInTouchMode(false);
        String cpfFormatado = usuarioSession.getCpf();
        cpfFormatado = cpfFormatado.substring(0,3) + "." + cpfFormatado.substring(3,6) + "." + cpfFormatado.substring(6,9) + "-" + cpfFormatado.substring(9,11);
        editTextCpf.setText(cpfFormatado);
        editTextNome= (EditText) findViewById(R.id.edit_text_nome);
        editTextNome.setFocusableInTouchMode(false);
        editTextNome.setText(usuarioSession.getNome());
        editTextNome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = ((EditText) v);
                editText.setFocusableInTouchMode(true);
                editText.setFocusable(true);
                editText.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        editTextEmail= (EditText) findViewById(R.id.edit_text_email);
        editTextEmail.setFocusableInTouchMode(false);
        editTextEmail.setText(usuarioSession.getLogin());
        editTextSenha = (EditText) findViewById(R.id.edit_text_senha);
        editTextSenha.setFocusableInTouchMode(false);
        editTextSenha.setText(usuarioSession.getSenha());
        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_title_bar, null);
        nomePagina = (RobotoTextView) v.findViewById(R.id.nome_pagina);
        nomePagina.setText("Meu Perfil");
        subHeader = (RobotoTextView) findViewById(R.id.sub_header);
        subHeader.setText(novoUsuario? "Novo Usu??rio" : "Editar Usu??rio");
        actionBar.setCustomView(v);
        verificarDeepLink();
    }

    private void verificarDeepLink() {
        deepLinkFireBase = SessionUtils.getDeepLinkFireBase(mPrefs);
        deepLinkFireBase = deepLinkFireBase.toUpperCase();
        if(deepLinkFireBase.contains(CHAVE)){
            abrirDialogInfo("Foi solicitado altera????o de senha.", "Chave: " +
                    deepLinkFireBase.subSequence(
                            deepLinkFireBase.indexOf(CHAVE) + CHAVE.length(), deepLinkFireBase.length()));
        }else if(deepLinkFireBase.contains(ID_CONVITE)){
            abrirDialogInfo("Novo usu??rio.", " Convite: " +
                            deepLinkFireBase.subSequence(
                                    deepLinkFireBase.indexOf(ID_CONVITE) + ID_CONVITE.length(), deepLinkFireBase.length()));

        }
    }

    private void abrirDialogInfo(String info, String parametro) {
        dialogInfo = new Dialog(this);
        dialogInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogInfo.setContentView(R.layout.dialog_taxas_termos);
        dialogInfo.show();
        TextView alertTitle=(TextView) dialogInfo.getWindow().getDecorView().findViewById(R.id.dialog_title);
        alertTitle.setText(getString(R.string.meuPerfil));
        TextView dialogTexto =(TextView) dialogInfo.getWindow().getDecorView().findViewById(R.id.text_dialog);
        dialogTexto.setText(Html.fromHtml("<h4>"+ info +"</h4>\n" +
                "    <strong>"+ parametro +"</strong><br>\n"));
        Button aplicar = (Button) dialogInfo.findViewById(R.id.btn_dialog);
        aplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogInfo.dismiss();
            }
        });
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case PICKFILE_RESULT_CODE:
                if(resultCode==RESULT_OK){
                    String imagem = PathUtils.getPath(getApplicationContext(), data.getData());
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
    public void onBackPressed() {
        Intent i = new Intent(MeuPerfilActivity.this, ListagemActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY );
        startActivity(i);
    }
}
