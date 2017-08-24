package br.com.aio.adapter;

/**
 * Created by elton on 23/08/2017.
 */


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.aio.R;
import br.com.aio.activity.ExtratoSaqueActivity;


public class LazyAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;

    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row_movimentacoes, null);

        TextView dia = (TextView)vi.findViewById(R.id.movimentacao_dia); // title
        TextView historico = (TextView)vi.findViewById(R.id.movimentacao_historico); // artist name
        TextView valor = (TextView)vi.findViewById(R.id.movimentacao_valor); // duration

        HashMap<String, String> movimentacoes = new HashMap<String, String>();
        movimentacoes = data.get(position);

        // Setting all values in listview
        dia.setText(movimentacoes.get(ExtratoSaqueActivity.KEY_DIA));
        historico.setText(movimentacoes.get(ExtratoSaqueActivity.KEY_HISTORICO));
        valor.setText(movimentacoes.get(ExtratoSaqueActivity.KEY_VALOR));
        if(movimentacoes.get(ExtratoSaqueActivity.KEY_TIPO_MOVIMENTACAO).equals(ExtratoSaqueActivity.CREDITO)){
            valor.setTextColor(activity.getApplicationContext().getResources().getColor(R.color.material_green_300));
        }else{
            valor.setTextColor(activity.getApplicationContext().getResources().getColor(R.color.material_red_300));
        }
        return vi;
    }
}