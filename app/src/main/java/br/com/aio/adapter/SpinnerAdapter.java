package br.com.aio.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.aio.R;

import static br.com.aio.utils.SpinnerUtils.getListaObjects;

/**
 * Created by elton on 24/09/2017.
 */

public class SpinnerAdapter extends ArrayAdapter {

    // Vars
    private LayoutInflater mInflater;
    private List objects;
    private Class aClass;

    public SpinnerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects, Class aClass) {
        super(context, resource, getListaObjects(objects, aClass));
        init(context, objects, aClass);
    }


    private void init(Context context, List objects, Class aClass) {
        this.mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.objects = getListaObjects(objects, aClass);
        this.aClass = aClass;
    }

    @Override
    public void setDropDownViewResource(@LayoutRes int resource) {
        super.setDropDownViewResource(resource);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.spinner_custom, parent, false);
            TextView titleSpinner = (TextView) convertView.findViewById(R.id.title_spinner);
            titleSpinner.setText(getItem(position).toString());
        }
        return convertView;
    }

    public Object getItemAtPosition(int position){
        return objects.get(position);
    }

}
