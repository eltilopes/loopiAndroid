package br.com.aio.view;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.com.aio.adapter.MyRecyclerViewAdapter;
import br.com.aio.entity.Filtro;
import br.com.aio.entity.ServicoCard;

/**
 * Created by elton on 26/10/2017.
 */

public class ServicosAsyncTask extends AsyncTask<String, Void, Integer> {

    private String TAG = "ServicosAsyncTask";
    private Context context;
    private List<ServicoCard> servicoCardList;
    private List<ServicoCard> servicos;
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;

    public ServicosAsyncTask(Context context, List<ServicoCard> servicoCardList, ProgressBar progressBar,
                             RecyclerView mRecyclerView,MyRecyclerViewAdapter adapter,String tag){
        this.context = context;
        this.servicoCardList = servicoCardList;
        servicos = servicoCardList;
        this.progressBar = progressBar;
        this.mRecyclerView = mRecyclerView;
        this.myRecyclerViewAdapter = adapter;
        TAG = tag;
    }

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
        return result;
    }

    @Override
    protected void onPostExecute(Integer result) {
        progressBar.setVisibility(View.GONE);

        if (result == 1) {
            myRecyclerViewAdapter = new MyRecyclerViewAdapter(context, servicoCardList, new Filtro());
            mRecyclerView.setAdapter(myRecyclerViewAdapter);
            myRecyclerViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onRecyclerViewItemClicked(int position, int id) {

                }
            });
        } else {
            Toast.makeText(context, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
        }
    }

    private void parseResult(String result) {
        try {
            Gson gson = new Gson();
            servicoCardList = new ArrayList<>();
            //servicoCardList = gson.fromJson(result, new TypeToken<ArrayList<ServicoCard>>(){}.getType());
            //TODO enquanto nao esta implementado salvar novo serviço, esta na memoria
            servicoCardList = servicos;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}