package br.com.aio.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
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
import br.com.aio.entity.Filtro;
import br.com.aio.entity.ServicoCard;
import br.com.aio.adapter.ServicoCardRecyclerViewAdapter;
import br.com.aio.fonts.RobotoTextView;

/**
 * Created by elton on 17/07/2017.
 */

public class MeusPedidosActivity extends AppCompatActivity {

    private static final String TAG = "MeusPedidosActivity";
    private List<ServicoCard> feedsList;
    private RobotoTextView nomePagina ;
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;
    private ServicoCardRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_pedidos);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(R.drawable.arrow_back_white);

        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_title_bar, null);
        nomePagina = (RobotoTextView) v.findViewById(R.id.nome_pagina);
        nomePagina.setText("Meus Pedidos");
        actionBar.setCustomView(v);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_servicos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        String url = "http://stacktips.com/?json=get_category_posts&slug=news&count=30";
        new MeusPedidosActivity.DownloadTask().execute(url);
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
                adapter = new ServicoCardRecyclerViewAdapter(MeusPedidosActivity.this, feedsList, new Filtro());
                mRecyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(MeusPedidosActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
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
                ServicoCard item = new ServicoCard();
                item.setTitle(post.optString("title"));
                item.setThumbnail(post.optString("thumbnail"));
                feedsList.add(item);
            }
            feedsList.isEmpty();
            feedsList.clear();
            feedsList.addAll(ServicoCard.getServicosCard());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

}
