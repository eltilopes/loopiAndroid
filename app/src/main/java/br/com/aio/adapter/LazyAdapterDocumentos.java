package br.com.aio.adapter;

/**
 * Created by elton on 23/08/2017.
 */


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.aio.R;
import br.com.aio.activity.CadastroDocumentosActivity;


public class LazyAdapterDocumentos extends BaseAdapter {

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    private TextView documento;

    public LazyAdapterDocumentos(Activity a, ArrayList<HashMap<String, String>> d) {
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

    private void showFileChooser(CharSequence nomeDocumento) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            Toast.makeText(activity, nomeDocumento,
                    Toast.LENGTH_SHORT).show();
            activity.startActivityForResult(intent,CadastroDocumentosActivity.PICKFILE_RESULT_CODE);

        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(activity, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row_documentos, null);

        documento = (TextView)vi.findViewById(R.id.nome_documento); // title
        ImageButton anexo = (ImageButton) vi.findViewById(R.id.anexo_documento);

        HashMap<String, String> documentos = new HashMap<String, String>();
        documentos = data.get(position);

        // Setting all values in listview
        documento.setText(documentos.get(CadastroDocumentosActivity.KEY_NOME_DOCUMENTO));
        anexo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(documento.getText());
            }
        });

        return vi;
    }
}