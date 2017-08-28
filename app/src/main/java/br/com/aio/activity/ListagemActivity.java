package br.com.aio.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import java.util.List;

import br.com.aio.R;
import br.com.aio.adapter.FeedItem;
import br.com.aio.adapter.MyRecyclerViewAdapter;
import br.com.aio.fonts.MaterialDesignIconsTextView;
import br.com.aio.listener.RecyclerItemClickListener;

public class ListagemActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "RecyclerViewExample";
    private List<FeedItem> feedsList;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter adapter;
    private ProgressBar progressBar;
    private LinearLayout buttonFiltro;
    private LinearLayout buttonCategoria;
    private LinearLayout buttonSubCategoria;
    private TextView nomeUsuario;
    private MaterialDesignIconsTextView imagemUsuario;
    private Dialog dialogMostrarFiltro;
    private Dialog dialogMostrarCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);
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
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        abrirPedido();
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        abrirPedido();
                    }
                })
        );
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        String url = "http://stacktips.com/?json=get_category_posts&slug=news&count=30";
        new DownloadTask().execute(url);
    }

    private void abrirMeuPerfil() {
        Intent newActivity = new Intent(ListagemActivity.this, MeuPerfilActivity.class);
        startActivity(newActivity);
        fecharMenu();
    }

    private void abrirPedido() {
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
        dialogMostrarFiltro = new Dialog(this);
        dialogMostrarFiltro.setTitle(R.string.filtro);
        dialogMostrarFiltro.setContentView(R.layout.dialog_filtro);
        dialogMostrarFiltro.show();

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

    private void mostrarCategorias() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Categorias");
        String[] types = {"Saúde", "Alimentação", "Transporte", "Serviços"};
        b.setItems(types, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                switch(which){
                    case 0:
                        Toast.makeText(ListagemActivity.this, "Gineco", Toast.LENGTH_SHORT);
                        break;
                    case 1:
                        Toast.makeText(ListagemActivity.this, "dfhgsdf", Toast.LENGTH_SHORT);
                        break;
                    case 2:
                        Toast.makeText(ListagemActivity.this, "sdfhgh", Toast.LENGTH_SHORT);
                        break;
                    case 3:
                        Toast.makeText(ListagemActivity.this, "sghsfgh", Toast.LENGTH_SHORT);
                        break;
                }
            }

        });

        b.show();

    }

    private void setButtonCategoria() {
        View v = findViewById(R.id.button_categoria);
        buttonCategoria = (LinearLayout) v;
        buttonCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mostrarCategorias();
            }
        });
    }

    private void setButtonSubCategoria() {
        View v = findViewById(R.id.button_sub_categoria);
        buttonSubCategoria = (LinearLayout) v;
        buttonSubCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ListagemActivity.this, "Mostrar Sub Categoria!", Toast.LENGTH_SHORT).show();
            }
        });
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
                adapter = new MyRecyclerViewAdapter(ListagemActivity.this, feedsList);
                mRecyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(ListagemActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseResult(String result) {

        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("posts");
            feedsList = new ArrayList<>();

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                FeedItem item = new FeedItem();
                item.setTitle(post.optString("title"));
                item.setThumbnail(post.optString("thumbnail"));
                feedsList.add(item);
            }
            feedsList.isEmpty();
            feedsList.clear();
            feedsList.addAll(FeedItem.getFeeds());
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.listagem, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fecharMenu(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_extrato_saque) {
            Intent newActivity = new Intent(ListagemActivity.this, ExtratoSaqueActivity.class);
            startActivity(newActivity);
        } else if (id == R.id.nav_pedido) {
            Intent newActivity = new Intent(ListagemActivity.this, MeusPedidosActivity.class);
            startActivity(newActivity);
        } else if (id == R.id.nav_profissional) {
            Intent newActivity = new Intent(ListagemActivity.this, ProfissionalActivity.class);
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
