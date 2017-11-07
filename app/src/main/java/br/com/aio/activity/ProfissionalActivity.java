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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.aio.R;
import br.com.aio.adapter.MyRecyclerViewAdapter;
import br.com.aio.entity.Profissional;
import br.com.aio.entity.ServicoCard;
import br.com.aio.entity.ServicoProfissional;
import br.com.aio.fonts.RobotoTextView;
import br.com.aio.utils.PathUtils;
import br.com.aio.utils.PermissionsUtils;
import br.com.aio.utils.SessionUtils;
import br.com.aio.utils.ToastUtils;
import br.com.aio.view.ServicosAsyncTask;

import static br.com.aio.utils.BundleUtils.PREFS_NAME;
import static br.com.aio.utils.PermissionsUtils.ACESSO_GRAVAR_ARMAZENAMENTO_NECESSARIO;
import static br.com.aio.utils.PermissionsUtils.ACESSO_GRAVAR_ARMAZENAMENTO_PERMITIDO;
import static br.com.aio.utils.PermissionsUtils.PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_ID;
import static br.com.aio.utils.PermissionsUtils.PICKFILE_RESULT_CODE;

/**
 * Created by elton on 17/07/2017.
 */

public class ProfissionalActivity extends AppCompatActivity implements MyRecyclerViewAdapter.OnRecyclerViewItemClickListener{

    private static final String TAG = "ProfissionalActivity";
    private static final String URL = "http://stacktips.com/?json=get_category_posts&slug=news&count=30";
    private RobotoTextView continuar ;
    private List<ServicoCard> servicos;
    private RobotoTextView nomePagina ;
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;
    private MyRecyclerViewAdapter adapter;
    private LinearLayout adicionarServico;
    private LinearLayout verTaxasAnuncio ;
    private Dialog dialogTaxasAnuncio ;
    private Dialog dialogAdicionarServico ;
    private Context context;
    private ImageView fotoPerfil;
    private SharedPreferences mPrefs;
    private Profissional profissional;
    private ServicoProfissional novoServicoProfissional;
    private Integer idServico = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profissional);
        context = this;
        mPrefs = getSharedPreferences(PREFS_NAME, 0);
        getProfissional();
        carregarCardProfgissional();
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
        adicionarServico = (LinearLayout) findViewById(R.id.adicionar_servico);
        adicionarServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirDialogAdicionarServico();
            }
        });
        fotoPerfil = (ImageView) findViewById(R.id.foto_perfil);
        fotoPerfil.setOnClickListener(new View.OnClickListener() {
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
        continuar = (RobotoTextView) findViewById(R.id.continuar);
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profissional.getServicos().isEmpty()){
                    ToastUtils.show(ProfissionalActivity.this, getResources().getString(R.string.error_adicionar_servico), ToastUtils.WARNING);
                }else{
                    SessionUtils.setCadastroProfissional(mPrefs);
                    Intent newActivity0 = new Intent(ProfissionalActivity.this, TermosActivity.class);
                    startActivity(newActivity0);
                }
            }
        });

        actionBar.setCustomView(v);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_servicos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
    }

    private void carregarCardProfgissional() {
        TextView nome, categoria, subCategoria,especialidade;
        nome = (TextView) findViewById(R.id.nome_profissional);
        categoria = (TextView) findViewById(R.id.categoria_profissional);
        subCategoria = (TextView) findViewById(R.id.sub_categoria_profissional);
        especialidade = (TextView) findViewById(R.id.especialidade_profissional);

        nome.setText(Html.fromHtml(profissional.getUsuario().getNome()));
        categoria.setText(Html.fromHtml(profissional.getCategoria().getDescricao()));
        subCategoria.setText(Html.fromHtml(profissional.getSubCategoria().getDescricao()));
        especialidade.setText(Html.fromHtml(profissional.getEspecialidade().getDescricao()));
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

    private void abrirDialogAdicionarServico() {
        novoServicoProfissional = new ServicoProfissional();
        novoServicoProfissional.setEspecialidade(profissional.getEspecialidade());
        dialogAdicionarServico = new Dialog(this);
        dialogAdicionarServico.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAdicionarServico.setContentView(R.layout.dialog_adicionar_servico);
        dialogAdicionarServico.show();
        final EditText nomeServico,descricaoServico,tempoServico,valorServico;
        nomeServico = (EditText) dialogAdicionarServico.findViewById(R.id.nome_servico);
        descricaoServico = (EditText) dialogAdicionarServico.findViewById(R.id.descricao_servico);
        tempoServico = (EditText) dialogAdicionarServico.findViewById(R.id.tempo_servico);
        valorServico = (EditText) dialogAdicionarServico.findViewById(R.id.valor_servico);
        RobotoTextView adicionar = (RobotoTextView) dialogAdicionarServico.findViewById(R.id.dialog_ok);
        adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    novoServicoProfissional.setNome(nomeServico.getEditableText().toString());
                    novoServicoProfissional.setDescricao(descricaoServico.getEditableText().toString());
                    novoServicoProfissional.setValor(Double.parseDouble(valorServico.getEditableText().toString()));
                    novoServicoProfissional.setTempo(Integer.parseInt(tempoServico.getEditableText().toString()));
                    novoServicoProfissional.setId(idServico++);
                    profissional.getServicos().add(novoServicoProfissional);
                    servicos = getServicosProfissional();
                    new ServicosAsyncTask(getApplicationContext(), servicos, progressBar, mRecyclerView, adapter, TAG).execute(URL);
                    dialogAdicionarServico.dismiss();
                }catch (Exception e){
                    ToastUtils.show(ProfissionalActivity.this, "Verifique os valores informados", ToastUtils.ERROR);
                }
            }
        });
    }

    private List<ServicoCard> getServicosProfissional() {
        List<ServicoCard> lista = new ArrayList<ServicoCard>();
        for(ServicoProfissional s : profissional.getServicos()){
            lista.add(new ServicoCard(
                    s.getId().longValue(), profissional.getUsuario().getNome(),
                    "https://media.licdn.com/mpr/mpr/shrinknp_200_200/AAEAAQAAAAAAAA0hAAAAJGY0Yjg0YTdkLWZhNTgtNDMwNC05MTkyLWQzMjlkMWRiZmUwZg.jpg",profissional.getCategoria(),
                    profissional.getSubCategoria(),profissional.getEspecialidade(),s.getId(),false,
                    s.getValor(),"distancia", s.getTempo()));

        }
        return lista;
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
                        fotoPerfil.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        fotoPerfil.setImageBitmap(myBitmap);
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

    public void getProfissional() {
        profissional = SessionUtils.getProfissionalCadastro(mPrefs);
        profissional.setServicos(new ArrayList<ServicoProfissional>());
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

    @Override
    public void onRecyclerViewItemClicked(int position, int id) {

    }
}
