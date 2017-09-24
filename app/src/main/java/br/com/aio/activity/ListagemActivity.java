package br.com.aio.activity;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.aio.R;
import br.com.aio.adapter.CustomSpinnerAdapter;
import br.com.aio.adapter.MyRecyclerViewAdapter;
import br.com.aio.entity.Localizacao;
import br.com.aio.entity.ServicoCard;
import br.com.aio.entity.UsuarioSession;
import br.com.aio.fonts.MaterialDesignIconsTextView;
import br.com.aio.utils.SessionUtils;
import br.com.aio.utils.ToastUtils;

import static br.com.aio.utils.BundleUtils.ACTIVITY_LISTAGEM;
import static br.com.aio.utils.BundleUtils.ACTIVITY_MAPS;
import static br.com.aio.utils.BundleUtils.PREFS_NAME;

public class ListagemActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MyRecyclerViewAdapter.OnRecyclerViewItemClickListener {

    private static final String TAG = "RecyclerViewExample";
    public static final int idCard = -1;
    private List<ServicoCard> servicoCards;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter adapter;
    private ProgressBar progressBar;
    private LinearLayout buttonFiltro;
    private LinearLayout buttonCategoria;
    private LinearLayout buttonSubCategoria;
    private TextView nomeUsuario;
    private MaterialDesignIconsTextView imagemUsuario;
    private Dialog dialogMostrarFiltro;
    private Dialog dialogSubCategoria;
    private SearchView.OnQueryTextListener queryTextListener;
    private SearchView searchView;
    private Spinner spinnerCategoria;
    private SharedPreferences mPrefs;
    private Localizacao localizacaoMapa;
    private UsuarioSession usuarioSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);
        mPrefs = getSharedPreferences(PREFS_NAME, 0);
        getUsuarioLogado();
        setButtonFiltro();
        setButtonCategoria();
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
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        String url = "http://stacktips.com/?json=get_category_posts&slug=news&count=30";
        new DownloadTask().execute(url);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        if(ACTIVITY_MAPS.equals(SessionUtils.getNomeActivityAnterior(mPrefs))){
            localizacaoMapa = SessionUtils.getLocalizacaoMapa(mPrefs);
            ToastUtils.show(this,getResources().getString(R.string.localizacao) + ": " + localizacaoMapa.getNome(), ToastUtils.INFORMATION);
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
        Spinner spinner = (Spinner) dialogMostrarFiltro.findViewById(R.id.subcategoria);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.list_sub_categoria, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(),
                        "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.performClick();
        CheckBox checkboxDistancia = (CheckBox) dialogMostrarFiltro.findViewById(R.id.checkbox_distancia);
        checkboxDistancia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),
                        "checkbox_distancia",
                        Toast.LENGTH_SHORT).show();

            }
        });

        RadioGroup radioGroup = (RadioGroup) dialogMostrarFiltro.findViewById(R.id.radio_group_valor);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb=(RadioButton) dialogMostrarFiltro.findViewById(checkedId);
                Toast.makeText(getApplicationContext(), rb.getText(), Toast.LENGTH_SHORT).show();

            }
        });

        TextView aplicar = (TextView) dialogMostrarFiltro.findViewById(R.id.dialog_ok);
        aplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogMostrarFiltro.dismiss();
            }
        });

    }

    private void mostrarSubCategorias() {
        dialogSubCategoria = new Dialog(this);
        dialogSubCategoria.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSubCategoria.setContentView(R.layout.dialog_sub_categoria);
        dialogSubCategoria.show();
        TextView alertTitle=(TextView)dialogSubCategoria.getWindow().getDecorView().findViewById(R.id.dialog_title);
        Spinner spinner = (Spinner) dialogSubCategoria.findViewById(R.id.subcategoria);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.list_sub_categoria, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(),
                        "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setButtonCategoria() {
        View v = findViewById(R.id.button_especialidade);
        buttonCategoria = (LinearLayout) v;
        buttonCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mostrarSubCategorias();
            }
        });
    }

    private void setButtonSubCategoria() {
        View v = findViewById(R.id.button_sub_categoria);
        buttonSubCategoria = (LinearLayout) v;
        buttonSubCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarSubCategorias();
            }
        });
    }

    @Override
    public void onRecyclerViewItemClicked(int position, int id) {
        ServicoCard servicoCard = servicoCards.get(position);
        switch(id) {
            case R.id.card_favorito:
                servicoCard.setFavorito(!servicoCard.getFavorito());
                adapter.deleteItem(position);
                adapter.addItem(servicoCard,position);
                break;
            case idCard:
                abrirPedido(servicoCard);
                break;
        }
    }

    public void getUsuarioLogado() {
        usuarioSession = new UsuarioSession(1,"Elton Lopes","92871259372");
    }

    public class DownloadTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            HttpURLConnection urlConnection;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                int statusCode = urlConnection.getResponseCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    parseResult(response.toString());
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            progressBar.setVisibility(View.GONE);

            if (result == 1) {
                adapter = new MyRecyclerViewAdapter(ListagemActivity.this, servicoCards);
                mRecyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(ListagemActivity.this);
            } else {
                Toast.makeText(ListagemActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseResult(String result) {

        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");
            servicoCards = new ArrayList<>();

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                ServicoCard item = new ServicoCard();
                item.setTitle(post.optString("title"));
                item.setThumbnail(post.optString("thumbnail"));
                servicoCards.add(item);
            }
            servicoCards.isEmpty();
            servicoCards.clear();
            servicoCards.addAll(ServicoCard.getServicos());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);

                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        MenuItem item = menu.findItem(R.id.spinner_categoria);
        spinnerCategoria = (Spinner) MenuItemCompat.getActionView(item);
        String[] strings = getApplicationContext().getResources().getStringArray(R.array.list_categoria);
        final CustomSpinnerAdapter spinAdapter = new CustomSpinnerAdapter(
                getApplicationContext(), new ArrayList<String>(Arrays.asList(strings)));
        spinnerCategoria.setAdapter(spinAdapter);
        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {
                // On selecting a spinner item
                String item = adapter.getItemAtPosition(position).toString();

                // Showing selected spinner item
                Toast.makeText(getApplicationContext(), "Selected  : " + item,
                        Toast.LENGTH_LONG).show();
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
            Intent newActivity = new Intent(ListagemActivity.this, MapsActivity.class);
            startActivity(newActivity);
            return true;
        }else if(id == R.id.action_search) {
            Toast.makeText(ListagemActivity.this, "Id: " + id, Toast.LENGTH_SHORT).show();
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
}
