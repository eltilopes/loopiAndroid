package br.com.aio.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

import java.util.List;

import br.com.aio.utils.ToastUtils;

/**
 * Created by elton on 25/09/2017.
 */

public class SpinnerAdapterView extends AdapterView implements AdapterView.OnItemSelectedListener {

    private Context context;
    private List objects;
    private interface OnSpinnerSelect{
        void onSpinnerSelect(AdapterView<?> parent, View view, int position, long id);
    }

    public SpinnerAdapterView(final Activity context, List objects) {
        super(context);
        init(context,objects);
    }

    private void init(final Activity context, List objects) {
        this.objects = objects;
        this.context = context;
    }

    @Override
    public Adapter getAdapter() {
        return null;
    }

    @Override
    public void setAdapter(Adapter adapter) {

    }

    @Override
    public View getSelectedView() {
        return null;
    }

    @Override
    public void setSelection(int position) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

     if(position>0) {
         ToastUtils.show((Activity) context,
                 "Selecionado : " + objects.get(position),
                 ToastUtils.INFORMATION);
     }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
