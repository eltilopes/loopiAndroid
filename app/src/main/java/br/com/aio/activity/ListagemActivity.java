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
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;

import br.com.aio.R;
import br.com.aio.adapter.MyRecyclerViewAdapter;
import br.com.aio.adapter.SpinnerAdapter;
import br.com.aio.entity.Categoria;
import br.com.aio.entity.Especialidade;
import br.com.aio.entity.Filtro;
import br.com.aio.entity.Localizacao;
import br.com.aio.entity.ServicoCard;
import br.com.aio.entity.SubCategoria;
import br.com.aio.entity.UsuarioSession;
import br.com.aio.fonts.MaterialDesignIconsTextView;
import br.com.aio.fonts.RobotoTextView;
import br.com.aio.service.ExecutorMetodoService;
import br.com.aio.service.GoogleService;
import br.com.aio.service.ServicoProfissionalService;
import br.com.aio.utils.ConexaoUtils;
import br.com.aio.utils.PermissionsUtils;
import br.com.aio.utils.SessionUtils;
import br.com.aio.utils.ToastUtils;
import br.com.aio.view.ProgressDialogAsyncTask;
import br.com.aio.view.SpinnerActionsHeader;
import retrofit.RetrofitError;

import static br.com.aio.utils.BundleUtils.ACTIVITY_LISTAGEM;
import static br.com.aio.utils.BundleUtils.ACTIVITY_MAPS;
import static br.com.aio.utils.BundleUtils.PREFS_NAME;
import static br.com.aio.utils.PermissionsUtils.ACESSO_LOCALIZACAO_NECESSARIO;
import static br.com.aio.utils.PermissionsUtils.ACESSO_LOCALIZACAO_PERMITIDO;
import static br.com.aio.utils.PermissionsUtils.PERMISSIONS_REQUEST_LOCATION_ID;

public class ListagemActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                   MyRecyclerViewAdapter.OnRecyclerViewItemClickListener,
                   View.OnClickListener
        , ProgressDialogAsyncTask.IProgressActivity  {

    //Localizacao
    private boolean permissaoGPS;
    private Geocoder geocoder;

    private static final String TAG = "RecyclerViewListagem";
    public static final int idCard = -1;
    private List<ServicoCard> servicoCards;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;
    private RelativeLayout layoutProgress;
    private LinearLayout buttonFiltro;
    private LinearLayout buttonEspecialidade;
    private LinearLayout buttonSubCategoria;
    private TextView nomeUsuario;
    private MaterialDesignIconsTextView imagemUsuario;
    private Dialog dialogMostrarFiltro;
    private Dialog dialogSubCategoria;
    private SpinnerActionsHeader spinnerSubCategoria;
    private Dialog dialogEspecialidade;
    private SpinnerActionsHeader spinnerEspecialidade;
    private SearchView.OnQueryTextListener queryTextListener;
    private SearchView searchView;
    private Spinner spinnerCategoria;
    private SharedPreferences mPrefs;
    private Localizacao localizacaoMapa;
    private UsuarioSession usuarioSession;
    private Filtro filtro;
    private RadioGroup radioGroupValor;
    private RadioButton radioButtonMenorValor;
    private RadioButton radioButtonMaiorValor;
    private RadioGroup radioGroupOrdemAlfabetica;
    private RadioButton radioButtonAZ;
    private RadioButton radioButtonZA;
    private CheckBox checkboxDistanciaMenor;
    private MenuItem menuSpinnerCategoria;
    private MenuItem menuPesquisar;
    private Context context;

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
        getUsuarioLogado();
        setButtonFiltro();
        setButtonEspecialidade();
        setButtonSubCategoria();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        nomeUsuario = (TextView) header.findViewById(R.id.nome_usuario);
        nomeUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirMeuPerfil();
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
        new ProgressDialogAsyncTask(this, layoutProgress, this).execute();
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        if(ACTIVITY_MAPS.equals(SessionUtils.getNomeActivityAnterior(mPrefs))){
            localizacaoMapa = SessionUtils.getLocalizacaoMapa(mPrefs);
            ToastUtils.show(this,getResources().getString(R.string.localizacao) + ": " + localizacaoMapa.getNome(), ToastUtils.INFORMATION);
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (spinnerSubCategoria != null && !hasFocus && spinnerSubCategoria.isEnabled()){
            spinnerSubCategoria.setDropDownMenuShown(true);
            spinnerSubCategoria.setActivated(true);
        }
        if (spinnerEspecialidade != null && !hasFocus && spinnerEspecialidade.isEnabled()){
            spinnerEspecialidade.setDropDownMenuShown(true);
            spinnerEspecialidade.setActivated(true);
        }


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

    private void setButtonFiltro() {
        View v = findViewById(R.id.button_filtro);
        buttonFiltro = (LinearLayout) v;
        buttonFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarFiltro();
            }
        });
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

    private void mostrarSubCategorias() {
        dialogSubCategoria.show();
        spinnerSubCategoria.performClick();
    }

    private void mostrarEspecialidades() {
        dialogEspecialidade.show();
        spinnerEspecialidade.performClick();
    }

    private void setButtonEspecialidade() {
        View v = findViewById(R.id.button_especialidade);
        buttonEspecialidade = (LinearLayout) v;
        buttonEspecialidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filtro.getSubCategoria()==null){
                    ToastUtils.show(ListagemActivity.this,
                            getString(R.string.alert_select_sub_categoria) ,ToastUtils.WARNING);
                }else {
                    mostrarEspecialidades();
                }
            }
        });
        dialogEspecialidade = new Dialog(this);
        dialogEspecialidade.setCancelable(true);
        dialogEspecialidade.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEspecialidade.setContentView(R.layout.dialog_button_header);
        RobotoTextView dialogTitle=(RobotoTextView)dialogEspecialidade.getWindow().getDecorView().findViewById(R.id.dialog_title);
        dialogTitle.setText(getString(R.string.especialidade));
        RobotoTextView title=(RobotoTextView) dialogEspecialidade.findViewById(R.id.dialog_title);
        title.setText(getString(R.string.especialidade));
        spinnerEspecialidade = (SpinnerActionsHeader) dialogEspecialidade.findViewById(R.id.spinner_button_header);
        spinnerEspecialidade.setPrompt(getString(R.string.especialidade));
        final SpinnerAdapter adapterEspecialidade = new SpinnerAdapter(getApplicationContext(),
                SessionUtils.getEspecialidades(mPrefs, filtro.getSubCategoria()), Especialidade.class, R.id.spinner_button_header);
        spinnerEspecialidade.setAdapter(adapterEspecialidade);
        spinnerEspecialidade.setSpinnerEventsListener(new SpinnerActionsHeader.OnSpinnerEventsListener() {
            @Override
            public void onSpinnerOpened(Spinner spinner) {
                spinnerEspecialidade.setActivated(false);
            }

            @Override
            public void onSpinnerClosed(Spinner spinner) {
                spinnerEspecialidade.setActivated(false);
                spinnerEspecialidade.clearFocus();
                Window window = dialogEspecialidade.getWindow();
                window.getDecorView().setVisibility(View.INVISIBLE);
            }
        });
        spinnerEspecialidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    adapterEspecialidade.setItemChecked(view, position);
                    Especialidade especialidade = (Especialidade) adapterEspecialidade.getItemAtPosition(position);
                    if(especialidade.getId()!=0){
                        filtro.setEspecialidade(especialidade);
                    }
                    ToastUtils.show(ListagemActivity.this,
                            "Selecionado : " + especialidade.getDescricao(),ToastUtils.INFORMATION);
                    dialogEspecialidade.dismiss();
                }else{
                    filtro.setEspecialidade(null);
                }
                new ProgressDialogAsyncTask(ListagemActivity.this, layoutProgress, ListagemActivity.this).execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dialogEspecialidade.dismiss();
            }
        });

    }

    private void setButtonSubCategoria() {
        View v = findViewById(R.id.button_sub_categoria);
        buttonSubCategoria = (LinearLayout) v;
        buttonSubCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filtro.getCategoria()==null){
                    ToastUtils.show(ListagemActivity.this,
                            getString(R.string.alert_select_categoria) ,ToastUtils.WARNING);
                }else {
                    mostrarSubCategorias();
                }
            }
        });
        dialogSubCategoria = new Dialog(this);
        dialogSubCategoria.setCancelable(true);
        dialogSubCategoria.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSubCategoria.setContentView(R.layout.dialog_button_header);
        TextView alertTitle=(TextView)dialogSubCategoria.getWindow().getDecorView().findViewById(R.id.dialog_title);
        spinnerSubCategoria = (SpinnerActionsHeader) dialogSubCategoria.findViewById(R.id.spinner_button_header);
        final SpinnerAdapter adapterSub = new SpinnerAdapter(getApplicationContext(),
                SessionUtils.getSubCategorias(mPrefs, filtro.getCategoria()), SubCategoria.class, R.id.spinner_button_header);
        spinnerSubCategoria.setAdapter(adapterSub);
        spinnerSubCategoria.setSpinnerEventsListener(new SpinnerActionsHeader.OnSpinnerEventsListener() {
            @Override
            public void onSpinnerOpened(Spinner spinner) {
                spinnerSubCategoria.setActivated(false);
            }

            @Override
            public void onSpinnerClosed(Spinner spinner) {
                spinnerSubCategoria.setActivated(false);
                spinnerSubCategoria.clearFocus();
                Window window = dialogSubCategoria.getWindow();
                window.getDecorView().setVisibility(View.INVISIBLE);
            }
        });
        spinnerSubCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    adapterSub.setItemChecked(view, position);
                    SubCategoria subCategoria = (SubCategoria) adapterSub.getItemAtPosition(position);
                    if(subCategoria.getId()!=0){
                        filtro.setSubCategoria(subCategoria);
                        setButtonEspecialidade();
                    }
                    ToastUtils.show(ListagemActivity.this,
                            "Selecionado : " + ((SubCategoria) adapterSub.getItemAtPosition(position)).getDescricao(),
                            ToastUtils.INFORMATION);
                    dialogSubCategoria.dismiss();
                }else{
                    filtro.setSubCategoria(null);
                    filtro.setEspecialidade(null);
                    setButtonEspecialidade();
                }
                new ProgressDialogAsyncTask(ListagemActivity.this, layoutProgress, ListagemActivity.this).execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dialogSubCategoria.dismiss();
            }
        });

    }

    @Override
    public void onRecyclerViewItemClicked(int position, int id) {
        ServicoCard servicoCard = servicoCards.get(position);
        switch(id) {
            case R.id.card_favorito:
                servicoCard.setFavorito(!servicoCard.getFavorito());
                myRecyclerViewAdapter.deleteItem(position);
                myRecyclerViewAdapter.addItem(servicoCard,position);
                break;
            case idCard:
                abrirPedido(servicoCard);
                break;
        }
    }

    public void getUsuarioLogado() {
        usuarioSession = new UsuarioSession(1,"Rodger Maia","92871259372");
    }

    @Override
    public void onClick(View v) {
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
            myRecyclerViewAdapter = new MyRecyclerViewAdapter(ListagemActivity.this, servicoCards, filtro);
            mRecyclerView.setAdapter(myRecyclerViewAdapter);
            myRecyclerViewAdapter.setOnItemClickListener(ListagemActivity.this);
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menuSpinnerCategoria = menu.findItem(R.id.action_spinner_header_categoria);
        spinnerCategoria = (Spinner) MenuItemCompat.getActionView(menuSpinnerCategoria);
        spinnerCategoria.setBackground(null);
        final SpinnerAdapter spinAdapter = new SpinnerAdapter(getApplicationContext(), SessionUtils.getCategorias(mPrefs), Categoria.class, R.id.action_spinner_header_categoria);
        spinnerCategoria.setAdapter(spinAdapter);
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
                MenuItemCompat.collapseActionView(menuSpinnerCategoria);
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

        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,int position, long id) {
                if(position>0) {
                    spinAdapter.setItemChecked(v, position);
                    Categoria categoria = (Categoria) spinAdapter.getItemAtPosition(position);
                    if(categoria.getId()!=0){
                        filtro.setCategoria(categoria);
                        setButtonSubCategoria();
                    }
                    ToastUtils.show(ListagemActivity.this,
                            "Selecionado : " + ((Categoria) spinAdapter.getItemAtPosition(position)).getDescricao(),
                            ToastUtils.INFORMATION);
                }else{
                    filtro.setCategoria(null);
                    filtro.setSubCategoria(null);
                    filtro.setEspecialidade(null);
                    setButtonSubCategoria();
                    setButtonEspecialidade();
                }
                new ProgressDialogAsyncTask(ListagemActivity.this, layoutProgress, ListagemActivity.this).execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_location) {
            SessionUtils.setActivityAnterior(mPrefs, ACTIVITY_LISTAGEM);
            startActivity(new Intent(this, MapsActivity.class));
            return true;
        }else if(id == R.id.action_search) {
            MenuItemCompat.collapseActionView(menuSpinnerCategoria);
            return false;
        }else if(id == R.id.action_spinner_header_categoria) {
            MenuItemCompat.collapseActionView(menuPesquisar);
            return false;
        }

        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    private void fecharMenu(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

}
