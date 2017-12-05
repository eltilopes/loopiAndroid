package br.com.aio.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.aio.R;
import br.com.aio.adapter.CategoriaCardRecyclerViewAdapter;
import br.com.aio.adapter.ServicoCardRecyclerViewAdapter;
import br.com.aio.entity.Categoria;
import br.com.aio.entity.Filtro;
import br.com.aio.entity.Localizacao;
import br.com.aio.entity.ServicoCard;
import br.com.aio.entity.UsuarioSession;
import br.com.aio.fonts.MaterialDesignIconsTextView;
import br.com.aio.fonts.RobotoTextView;
import br.com.aio.service.CategoriaService;
import br.com.aio.service.ExecutorMetodoService;
import br.com.aio.service.GoogleService;
import br.com.aio.service.ServicoProfissionalService;
import br.com.aio.utils.ConexaoUtils;
import br.com.aio.utils.PermissionsUtils;
import br.com.aio.utils.SessionUtils;
import br.com.aio.utils.ToastUtils;
import br.com.aio.view.ProgressDialogAsyncTask;
import retrofit.RetrofitError;

import static br.com.aio.utils.BundleUtils.ACTIVITY_LISTAGEM;
import static br.com.aio.utils.BundleUtils.ACTIVITY_MAPS;
import static br.com.aio.utils.BundleUtils.PREFS_NAME;
import static br.com.aio.utils.PermissionsUtils.ACESSO_LOCALIZACAO_NECESSARIO;
import static br.com.aio.utils.PermissionsUtils.ACESSO_LOCALIZACAO_PERMITIDO;
import static br.com.aio.utils.PermissionsUtils.PERMISSIONS_REQUEST_LOCATION_ID;

public class ListagemActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ServicoCardRecyclerViewAdapter.OnRecyclerViewItemClickListener,
        CategoriaCardRecyclerViewAdapter.OnRecyclerViewCategoriaItemClickListener,
                   View.OnClickListener
        , ProgressDialogAsyncTask.IProgressActivity  {

    //Localizacao
    private boolean permissaoGPS;
    private Geocoder geocoder;

    private static final String TAG = "RecyclerViewListagem";
    public static final int idCard = -1;
    private List<ServicoCard> servicoCards;
    private List<Categoria> categoriaCards;
    private RecyclerView mRecyclerView;
    private ServicoCardRecyclerViewAdapter myRecyclerViewAdapter;
    private RecyclerView mRecyclerViewCategoria;
    private CategoriaCardRecyclerViewAdapter myRecyclerViewAdapterCategoria;
    private RelativeLayout layoutProgress;
    private TextView nomeUsuario;
    private MaterialDesignIconsTextView imagemUsuario;
    private Dialog dialogMostrarFiltro;
    private Dialog dialogFavoritosServico;
    private SearchView.OnQueryTextListener queryTextListener;
    private SearchView searchView;
    private SharedPreferences mPrefs;
    private Localizacao localizacaoMapa;
    private RobotoTextView localizacao;
    private UsuarioSession usuarioSession;
    private Filtro filtro;
    private RadioGroup radioGroupValor;
    private RadioButton radioButtonMenorValor;
    private RadioButton radioButtonMaiorValor;
    private RadioGroup radioGroupOrdemAlfabetica;
    private RadioButton radioButtonAZ;
    private RadioButton radioButtonZA;
    private CheckBox checkboxDistanciaMenor;
    private MenuItem menuFiltro;
    private MenuItem menuPesquisar;
    private Context context;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);
        context = getApplicationContext();
        layoutProgress = (RelativeLayout) findViewById(R.id.dialog_progress);
        filtro =  new Filtro();
        mPrefs = getSharedPreferences(PREFS_NAME, 0);
        geocoder = new Geocoder(this, Locale.getDefault());
        if (PermissionsUtils.checkPlayServices(context)) {
            if (!PermissionsUtils.isDeviceLocationGranted(context)) {
                PermissionsUtils.requestPermissions(context, PERMISSIONS_REQUEST_LOCATION_ID,
                        Manifest.permission.ACCESS_FINE_LOCATION, new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
            }else{
                permissaoGPS = true;
                startServiceGoocle();
            }
        }
        localizacao = (RobotoTextView) findViewById(R.id.localizacao);
        localizacaoMapa = new Localizacao(1L,"Localização Casa",-3.741395,-38.499196);
        filtro.setMinhaLatLng(new LatLng(localizacaoMapa.getLatitude(), localizacaoMapa.getLongitude()));
        ToastUtils.show(this,getResources().getString(R.string.localizacao) + ": " + localizacaoMapa.getNome(), ToastUtils.INFORMATION);
        localizacao.setText(localizacaoMapa.getNome());
        getUsuarioLogado();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        setDrawerState(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        nomeUsuario = (TextView) header.findViewById(R.id.nome_usuario);
        nomeUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(ListagemActivity.this,"v.getId() : " + v.getId(), ToastUtils.INFORMATION);
            }
        });
        imagemUsuario = (MaterialDesignIconsTextView) header.findViewById(R.id.image_usuario);
        imagemUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirMeuPerfil();
            }
        });
        navigationView.setNavigationItemSelectedListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewCategoria = (RecyclerView) findViewById(R.id.recycler_view_categoria);
        mRecyclerViewCategoria.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        new ProgressDialogAsyncTask(this, layoutProgress, this).execute();
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        if(ACTIVITY_MAPS.equals(SessionUtils.getNomeActivityAnterior(mPrefs))){
            localizacaoMapa = SessionUtils.getLocalizacaoMapa(mPrefs);
            localizacao.setText(localizacaoMapa.getNome());
            ToastUtils.show(this,getResources().getString(R.string.localizacao) + ": " + localizacaoMapa.getNome(), ToastUtils.INFORMATION);
        }
        localizacao.setOnClickListener(this);
    }

    private void abrirMeuPerfil() {
        Intent newActivity = new Intent(ListagemActivity.this, MeuPerfilActivity.class);
        startActivity(newActivity);
        fecharMenu();
    }

    private void abrirPedido(ServicoCard servicoCard) {
        SessionUtils.setServicoCard(mPrefs,servicoCard);
        Intent newActivity = new Intent(ListagemActivity.this, SolicitarPedidoActivity.class);
        startActivity(newActivity);
    }

    public void mostrarFiltro() {
        //dialogMostrarFiltro = new Dialog(this, R.style.MyDialogTheme);
        dialogMostrarFiltro = new Dialog(this);
        dialogMostrarFiltro.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogMostrarFiltro.setContentView(R.layout.dialog_filtro);
        dialogMostrarFiltro.show();
        TextView alertTitle=(TextView)dialogMostrarFiltro.getWindow().getDecorView().findViewById(R.id.dialog_title);

        checkboxDistanciaMenor = (CheckBox) dialogMostrarFiltro.findViewById(R.id.checkbox_distancia);
        checkboxDistanciaMenor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtro.setDistanciaMenor(checkboxDistanciaMenor.isChecked());
                Toast.makeText(v.getContext(),
                        "checkbox_distancia",
                        Toast.LENGTH_SHORT).show();

            }
        });
        if(filtro.getDistanciaMenor()!=null){
            checkboxDistanciaMenor.setChecked(filtro.getDistanciaMenor());
        }
        radioGroupValor = (RadioGroup) dialogMostrarFiltro.findViewById(R.id.radio_group_valor);
        radioButtonMenorValor =(RadioButton) dialogMostrarFiltro.findViewById(R.id.radio_menor_valor);
        radioButtonMaiorValor =(RadioButton) dialogMostrarFiltro.findViewById(R.id.radio_maior_valor);
        radioGroupValor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_menor_valor:
                        radioButtonMenorValor.setChecked(true);
                        radioButtonMaiorValor.setChecked(false);
                        filtro.setMenorValor(true);
                        Toast.makeText(getApplicationContext(), radioButtonMenorValor.getText(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio_maior_valor:
                        radioButtonMenorValor.setChecked(false);
                        radioButtonMaiorValor.setChecked(true);
                        filtro.setMenorValor(false);
                        Toast.makeText(getApplicationContext(), radioButtonMaiorValor.getText(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        if(filtro.getMenorValor()!=null){
            radioButtonMenorValor.setChecked(filtro.getMenorValor());
            radioButtonMaiorValor.setChecked(!filtro.getMenorValor());
        }

        radioGroupOrdemAlfabetica = (RadioGroup) dialogMostrarFiltro.findViewById(R.id.radio_group_ordem_alfabetica);
        radioButtonAZ =(RadioButton) dialogMostrarFiltro.findViewById(R.id.radio_a_z);
        radioButtonZA =(RadioButton) dialogMostrarFiltro.findViewById(R.id.radio_z_a);
        radioGroupOrdemAlfabetica.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_a_z:
                        radioButtonAZ.setChecked(true);
                        radioButtonZA.setChecked(false);
                        filtro.setOrdemAlfabeticaCrescente(true);
                        Toast.makeText(getApplicationContext(), radioButtonAZ.getText(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio_z_a:
                        radioButtonAZ.setChecked(false);
                        radioButtonZA.setChecked(true);
                        filtro.setOrdemAlfabeticaCrescente(false);
                        Toast.makeText(getApplicationContext(), radioButtonZA.getText(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        if(filtro.getOrdemAlfabeticaCrescente()!=null){
            radioButtonAZ.setChecked(filtro.getOrdemAlfabeticaCrescente());
            radioButtonZA.setChecked(!filtro.getOrdemAlfabeticaCrescente());
        }
        TextView aplicar = (TextView) dialogMostrarFiltro.findViewById(R.id.dialog_ok);
        aplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogMostrarFiltro.dismiss();
                new ProgressDialogAsyncTask(ListagemActivity.this, layoutProgress, ListagemActivity.this).execute();
            }
        });
    }

    @Override
    public void onRecyclerViewItemClicked(int position, int id) {
        ServicoCard servicoCard = servicoCards.get(position);
        switch(id) {
            case R.id.card_favorito:
                servicoCard.setFavorito(!servicoCard.getFavorito());
                if(servicoCard.getFavorito()){
                    servicoCard.setQuantidadeFavorito(servicoCard.getQuantidadeFavorito()+1);
                }else{
                    servicoCard.setQuantidadeFavorito(servicoCard.getQuantidadeFavorito()-1);
                }
                myRecyclerViewAdapter.deleteItem(position);
                myRecyclerViewAdapter.addItem(servicoCard,position);
                break;
            case R.id.card_favorito_quantidade:
                mostrarFavoritosServico();
                break;
            case R.id.card_categoria:
                filtro.setCategoria(servicoCard.getCategoria());
                pesquisarAposClickCard(servicoCard.getCategoria().getDescricao());
                break;
            case R.id.card_sub_categoria:
                filtro.setSubCategoria(servicoCard.getSubCategoria());
                pesquisarAposClickCard(servicoCard.getSubCategoria().getDescricao());
                break;
            case R.id.card_especialidade:
                filtro.setEspecialidade(servicoCard.getEspecialidade());
                pesquisarAposClickCard(servicoCard.getEspecialidade().getDescricao());
                break;
            case idCard:
                abrirPedido(servicoCard);
                break;
        }
    }

    private void pesquisarAposClickCard(String selecionado) {
        myRecyclerViewAdapter = new ServicoCardRecyclerViewAdapter(ListagemActivity.this, new ArrayList<ServicoCard>(), filtro);
        mRecyclerView.setAdapter(myRecyclerViewAdapter);
        new ProgressDialogAsyncTask(ListagemActivity.this, layoutProgress, ListagemActivity.this).execute();
        ToastUtils.show(ListagemActivity.this,
                selecionado + " " + getString(R.string.selecionado) ,
                ToastUtils.INFORMATION);
    }

    private void mostrarFavoritosServico() {
        dialogFavoritosServico = new Dialog(this);
        dialogFavoritosServico.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogFavoritosServico.setContentView(R.layout.dialog_favoritos_servico);
        dialogFavoritosServico.show();
        TextView alertTitle=(TextView)dialogFavoritosServico.getWindow().getDecorView().findViewById(R.id.dialog_title);
        ListView listaFavoritosServico = (ListView) dialogFavoritosServico.findViewById(R.id.lista_favoritos);
        List<String> favoritosServico = new ArrayList<String>();
        favoritosServico.add("Elton Lopes");
        favoritosServico.add("Marlon Moraes");
        favoritosServico.add("Ricardo Rocha");
        favoritosServico.add("Rodger Urso");
        favoritosServico.add("Renan Ferreira");
        listaFavoritosServico.setAdapter(new ArrayAdapter(this, R.layout.list_row_favorito_servico, favoritosServico));
        listaFavoritosServico.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                dialogFavoritosServico.dismiss();
            }
        });
    }


    public void getUsuarioLogado() {
        usuarioSession = new UsuarioSession(1,"Rodger Maia","92871259372");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.localizacao) {
            SessionUtils.setActivityAnterior(mPrefs, ACTIVITY_LISTAGEM);
            startActivity(new Intent(this, MapsActivity.class));
        }else if (v.getId() == R.id.nome_pagina) {
            filtro.setCategoria(null);
            setDrawerState(true);
            new ProgressDialogAsyncTask(ListagemActivity.this, layoutProgress, ListagemActivity.this).execute();

        }
        finish();
    }

    @Override
    public void executaProgressoDialog() {

            try{
                if(ConexaoUtils.isConexao(getApplicationContext())){
                    try {
                        if(filtro.getMinhaLatLng()==null){
                            ToastUtils.show(ListagemActivity.this, getResources().getString(R.string.informar_localizacao), ToastUtils.WARNING);
                        }else {
                            categoriaCards = ExecutorMetodoService.invoke(new CategoriaService(this), "getCategorias");
                            servicoCards = ExecutorMetodoService.invoke(new ServicoProfissionalService(this), "getServicoCardPorFiltro", filtro);
                        }
                    } catch (RetrofitError error) {
                        ToastUtils.showErro(this, error.getResponse());
                    } catch (RuntimeException erro) {
                        this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.show(ListagemActivity.this, getResources().getString(R.string.error_nao_encontrado), ToastUtils.ERROR);
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
    public void onPostExecute() {
        if (servicoCards != null && !servicoCards.isEmpty()) {
            myRecyclerViewAdapter = new ServicoCardRecyclerViewAdapter(ListagemActivity.this, servicoCards, filtro);
            mRecyclerView.setAdapter(myRecyclerViewAdapter);
            myRecyclerViewAdapter.setOnItemClickListener(ListagemActivity.this);
        }if (categoriaCards != null && !categoriaCards.isEmpty()) {
            myRecyclerViewAdapterCategoria = new CategoriaCardRecyclerViewAdapter(ListagemActivity.this, categoriaCards);
            mRecyclerViewCategoria.setAdapter(myRecyclerViewAdapterCategoria);
            myRecyclerViewAdapterCategoria.setOnItemClickListener(ListagemActivity.this);
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menuFiltro= menu.findItem(R.id.action_filtro);
        menuPesquisar = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuPesquisar);
        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
        searchView.setQueryHint(getString(R.string.pesquisar_toolbar));
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.requestFocus();
            }
        });
        if (menuPesquisar != null) {
            searchView = (SearchView) menuPesquisar.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    if(filtro.getPesquisaToolbar()!=null){
                        filtro.setPesquisaToolbar(newText);
                        if(newText.isEmpty()){
                            new ProgressDialogAsyncTask(ListagemActivity.this, layoutProgress, ListagemActivity.this).execute();
                        }
                    }
                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    filtro.setPesquisaToolbar(query);
                    new ProgressDialogAsyncTask(ListagemActivity.this, layoutProgress, ListagemActivity.this).execute();
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_search) {
            return false;
        }else if(id == R.id.action_filtro) {
            MenuItemCompat.collapseActionView(menuPesquisar);
            mostrarFiltro();
            return false;
        }

        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    private void fecharMenu(){
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        SessionUtils.setUsuarioSession(mPrefs, usuarioSession);
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_extrato_saque) {
            Intent newActivity = new Intent(ListagemActivity.this, ExtratoSaqueActivity.class);
            startActivity(newActivity);
        } else if (id == R.id.nav_pedido) {
            Intent newActivity = new Intent(ListagemActivity.this, MeusPedidosActivity.class);
            startActivity(newActivity);
        } else if (id == R.id.nav_profissional) {
            Intent newActivity = new Intent(ListagemActivity.this, CadastroServicoActivity.class);
            startActivity(newActivity);
        } else if (id == R.id.nav_configuracoes) {
            Intent newActivity = new Intent(ListagemActivity.this, ConfiguracoesActivity.class);
            startActivity(newActivity);
        } else if (id == R.id.nav_problema) {
            Intent newActivity = new Intent(ListagemActivity.this, RelateProblemaActivity.class);
            startActivity(newActivity);
        } else if (id == R.id.nav_indicacao) {
            Intent newActivity = new Intent(ListagemActivity.this, IndiqueCashBackActivity.class);
            startActivity(newActivity);
        } else if (id == R.id.nav_switch) {
            Toast.makeText(ListagemActivity.this, "Id: " + id, Toast.LENGTH_SHORT).show();
        }
        fecharMenu();
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_LOCATION_ID:
                permissaoGPS = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (permissaoGPS) {
                    ToastUtils.show(ListagemActivity.this, ACESSO_LOCALIZACAO_PERMITIDO, ToastUtils.INFORMATION);
                    startServiceGoocle();
                } else {
                    ToastUtils.show(ListagemActivity.this, ACESSO_LOCALIZACAO_NECESSARIO, ToastUtils.WARNING);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            localizacaoMapa = SessionUtils.getLocalizacaoMapa(mPrefs);
            filtro.setMinhaLatLng(new LatLng(localizacaoMapa.getLatitude(), localizacaoMapa.getLongitude()));
            new ProgressDialogAsyncTask(ListagemActivity.this, layoutProgress, ListagemActivity.this).execute();
            localizacao.setText(localizacaoMapa.getNome());
            ToastUtils.show(ListagemActivity.this,getResources().getString(R.string.localizacao) + ": " + localizacaoMapa.getNome(), ToastUtils.INFORMATION);

        }
    };

    private void startServiceGoocle(){
        Intent intent = new Intent(context, GoogleService.class);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(GoogleService.receiver));

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onRecyclerViewCategoriaItemClicked(int position, int id) {
        Categoria categoriaCard = categoriaCards.get(position);
        filtro.setCategoria(categoriaCard);
        pesquisarAposClickCard(categoriaCard.getDescricao());
        setDrawerState(false);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(R.drawable.arrow_back_white);
        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_title_bar, null);
        RobotoTextView nomePagina = (RobotoTextView) v.findViewById(R.id.nome_pagina);
        nomePagina.setText(categoriaCard.getDescricao() );
        actionBar.setCustomView(v);
        actionBar.getCustomView().setOnClickListener(this);
    }

    public void setDrawerState(boolean isEnabled) {
        if ( isEnabled ) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            toggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_UNLOCKED);
            toggle.setDrawerIndicatorEnabled(true);
            toggle.syncState();

        }
        else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            toggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            toggle.setDrawerIndicatorEnabled(false);
            toggle.syncState();
        }
    }
}
