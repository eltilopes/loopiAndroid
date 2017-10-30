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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import br.com.aio.R;
import br.com.aio.adapter.SpinnerAdapter;
import br.com.aio.entity.Categoria;
import br.com.aio.entity.Profissional;
import br.com.aio.entity.SubCategoria;
import br.com.aio.fonts.RobotoTextView;
import br.com.aio.service.ExecutorMetodoService;
import br.com.aio.service.ProfissionalService;
import br.com.aio.utils.ConexaoUtils;
import br.com.aio.utils.SessionUtils;
import br.com.aio.utils.TelefoneMaskUtil;
import br.com.aio.utils.ToastUtils;
import br.com.aio.view.DadosBancariosView;
import br.com.aio.view.ProgressDialogAsyncTask;
import retrofit.RetrofitError;

import static br.com.aio.utils.BundleUtils.PREFS_NAME;

/**
 * Created by elton on 17/07/2017.
 */

public class CadastroProfissionalActivity extends AppCompatActivity implements AdapterView.OnClickListener, ProgressDialogAsyncTask.IProgressActivity{

    private RobotoTextView nomePagina ;
    private TextView continuar ;
    private Spinner spinnerCategoria;
    private Spinner spinnerSubCategoria;
    private SharedPreferences mPrefs;
    private Profissional profissional;
    private EditText editTextCpf;
    private EditText editTextNome;
    private EditText editTextEmail;
    private EditText editTextTelefone;
    private DadosBancariosView dadosBancarios;
    private RelativeLayout layoutDadosBancarios;
    private LinearLayout linearDadosBancarios;
    private LinearLayout layoutDadosPessoais;
    private LinearLayout linearDadosPessoais;
    private RelativeLayout layoutProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_profissional);
        ActionBar actionBar = getSupportActionBar();
        mPrefs = getSharedPreferences(PREFS_NAME, 0);
        getProfissional();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(R.drawable.arrow_back_white);
        layoutProgress = (RelativeLayout) findViewById(R.id.dialog_progress);
        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_title_bar, null);
        nomePagina = (RobotoTextView) v.findViewById(R.id.nome_pagina);
        nomePagina.setText("Cadastro");
        actionBar.setCustomView(v);
        layoutDadosBancarios = (RelativeLayout) findViewById(R.id.layout_dados_bancarios);
        linearDadosBancarios = (LinearLayout) findViewById(R.id.linear_dados_bancarios);
        final Animation animation   =    AnimationUtils.loadAnimation(getApplicationContext(), R.animator.linear_layout_anim);
        animation.setDuration(300);
        linearDadosBancarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layoutDadosBancarios.getVisibility() == View.VISIBLE) {
                    layoutDadosBancarios.setVisibility(View.GONE); // hides layout
                } else {
                    layoutDadosBancarios.setVisibility(View.VISIBLE); // shows layout

                    /*layoutDadosBancarios.setAnimation(animation);
                    layoutDadosBancarios.animate();
                    animation.start();*/
                }

            }
        });
        layoutDadosPessoais = (LinearLayout) findViewById(R.id.layout_dados_pessoais);
        linearDadosPessoais = (LinearLayout) findViewById(R.id.linear_dados_pessoais);
        linearDadosPessoais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layoutDadosPessoais.getVisibility() == View.VISIBLE) {
                    /*TranslateAnimation animate = new TranslateAnimation(0,0,layoutDadosPessoais.getHeight(),0);
                    animate.setDuration(500);
                    animate.setFillAfter(true);
                    layoutDadosPessoais.startAnimation(animate);*/
                    layoutDadosPessoais.setVisibility(View.GONE);
                }
                else {
                    /*TranslateAnimation animate = new TranslateAnimation(0,0,0,layoutDadosPessoais.getHeight());
                    animate.setDuration(500);
                    animate.setFillAfter(true);
                    layoutDadosPessoais.startAnimation(animate);*/
                    layoutDadosPessoais.setVisibility(View.VISIBLE);
                }

            }
        });
        editTextCpf= (EditText) findViewById(R.id.edit_text_cpf);
        editTextCpf.setFocusableInTouchMode(false);
        editTextCpf.setText(profissional.getUsuario().getCpf());
        editTextCpf.setOnClickListener(new View.OnClickListener() {
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
        editTextNome= (EditText) findViewById(R.id.edit_text_nome);
        editTextNome.setFocusableInTouchMode(false);
        editTextNome.setText(profissional.getUsuario().getNome());
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
        editTextEmail.setOnClickListener(new View.OnClickListener() {
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
        editTextTelefone= (EditText) findViewById(R.id.edit_text_telefone);
        editTextTelefone.setFocusableInTouchMode(false);
        editTextTelefone.setText(profissional.getUsuario().getTelefone());
        editTextTelefone.addTextChangedListener(TelefoneMaskUtil.insert(editTextTelefone));
        editTextTelefone.setOnClickListener(new View.OnClickListener() {
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

        continuar = (TextView) findViewById(R.id.continuar);
        continuar.setOnClickListener(this);

        spinnerCategoria = (Spinner) findViewById(R.id.categoria);
        final SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(),
                Categoria.getCategorias(), Categoria.class, R.id.categoria);
        spinnerCategoria.setAdapter(adapter);
        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    adapter.setItemChecked(view, position);
                    profissional.setCategoria((Categoria) adapter.getItemAtPosition(position));
                    ToastUtils.show(CadastroProfissionalActivity.this,
                            "Categoria Selecionada : " + profissional.getCategoria().getDescricao(),
                            ToastUtils.INFORMATION);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerSubCategoria = (Spinner) findViewById(R.id.sub_categoria);
        final SpinnerAdapter adapterSub = new SpinnerAdapter(getApplicationContext(),
                SubCategoria.getSubCategorias(), SubCategoria.class, R.id.sub_categoria);
        spinnerSubCategoria.setAdapter(adapterSub);
        spinnerSubCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    adapterSub.setItemChecked(view, position);
                    profissional.setSubCategoria((SubCategoria) adapterSub.getItemAtPosition(position));
                    ToastUtils.show(CadastroProfissionalActivity.this,
                            "Selecionado : " + profissional.getSubCategoria().getDescricao(),
                            ToastUtils.INFORMATION);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dadosBancarios = new DadosBancariosView(this);

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
                ProgressDialogAsyncTask task = new ProgressDialogAsyncTask(this, layoutProgress, this);
                task.execute();
                Intent newActivity0 = new Intent(CadastroProfissionalActivity.this, CadastroDocumentosActivity.class);
                startActivity(newActivity0);
                break;
        }

    }

    public void getProfissional() {
        profissional = SessionUtils.getProfissionalCadastro(mPrefs);
    }

    public void verDadosBancarios() {
        dadosBancarios.getBanco();
        dadosBancarios.getFinalidade();
    }


    public void executaProgressoDialog(){
        try{
            if(ConexaoUtils.isConexao(getApplicationContext())){

                try {
                    Profissional profissionalResponse = ExecutorMetodoService.invoke(new ProfissionalService(this), "salvarProfissional",
                            profissional);
                    SessionUtils.setProfissionalCadastro(mPrefs, profissionalResponse);
                    ToastUtils.show(this, getResources().getString(R.string.sucess_profissional), ToastUtils.INFORMATION);
                } catch (RetrofitError error) {
                    ToastUtils.showErro(this, error.getResponse());
                } catch (RuntimeException erro) {
                    this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.show(CadastroProfissionalActivity.this, getResources().getString(R.string.error_nao_encontrado), ToastUtils.ERROR);
                        }
                    });
                }
            }else {
                ToastUtils.show(this, getResources().getString(R.string.error_conexao_internet), ToastUtils.ERROR);
            }
        }catch (RetrofitError error){
            ToastUtils.showErro(this, error.getResponse());
        }

    }


    @Override
    public boolean isAddedValidation() {
        return true;
    }

    @Override
    public void onPostExecute() {}
}
