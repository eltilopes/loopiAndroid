package br.com.aio.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import br.com.aio.R;
import br.com.aio.utils.ViewUtils;

import static br.com.aio.utils.SpinnerUtils.getListaObjects;

/**
 * Created by elton on 24/09/2017.
 */

public class SpinnerAdapter extends ArrayAdapter {

    // Vars
    private LayoutInflater mInflater;
    private List objects;
    private Class aClass;
    private  boolean isHeaderView;
    private Context context;
    private Integer itemChecked;

    public SpinnerAdapter(@NonNull Context context, @NonNull List objects, Class aClass, boolean isHeaderView) {
        super(context, R.layout.spinner_custom, getListaObjects(objects, aClass));
        init(context, objects, aClass, isHeaderView);
    }


    private void init(Context context, List objects, Class aClass,boolean isHeaderView) {
        this.mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.objects = getListaObjects(objects, aClass);
        this.aClass = aClass;
        this.isHeaderView = isHeaderView;
        this.context = context;
        itemChecked = null;
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
        View row = mInflater.inflate(R.layout.spinner_custom, parent, false);
        RelativeLayout layoutSpinner = (RelativeLayout) row.findViewById(R.id.layout_spinner);
        TextView titleSpinner = (TextView) row.findViewById(R.id.title_spinner);
        titleSpinner.setText(getItemAtPosition(position).toString());
        int idDrawable = position > 0 ? R.drawable.ic_check_box_no_select: R.drawable.ic_arrow_down;
        Drawable icon = ViewUtils.drawableSetTint(context.getResources().getDrawable(idDrawable),
                context.getResources().getColor(R.color.branco));
        if(!isHeaderView){
            layoutSpinner.setBackground(context.getResources().getDrawable(R.drawable.background_edit_text));
            titleSpinner.setTextColor(context.getResources().getColor(R.color.textColorCinzaEscuro));
            icon = ViewUtils.drawableSetTint(icon,context.getResources().getColor(R.color.textColorCinzaEscuro));
        }
        titleSpinner.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,icon,null);
        if(itemChecked != null && position==itemChecked.intValue()){
            setChecked(row);
        }

        return row;
    }

    public Object getItemAtPosition(int position){
        return objects.get(position);
    }

    public void setItemChecked(View v, int position) {
        itemChecked = position;
        setChecked(v);
    }

    private void setChecked(View row) {
        TextView titleSpinner = (TextView) row.findViewById(R.id.title_spinner);
        Drawable icon = ViewUtils.drawableSetTint(context.getResources().getDrawable(R.drawable.ic_check_box_select),
                context.getResources().getColor(R.color.branco));
        if(!isHeaderView){
            titleSpinner.setTextColor(context.getResources().getColor(R.color.textColorCinzaEscuro));
            icon = ViewUtils.drawableSetTint(icon,context.getResources().getColor(R.color.textColorCinzaEscuro));
        }

        titleSpinner.setCompoundDrawablesWithIntrinsicBounds(null,null,icon,null);
    }
}
