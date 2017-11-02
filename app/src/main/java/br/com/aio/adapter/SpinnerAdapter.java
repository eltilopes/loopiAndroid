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
    private int idViewParent;
    private Context context;
    private Integer itemChecked;

    public SpinnerAdapter(@NonNull Context context, @NonNull List objects, Class aClass, int idViewParent) {
        super(context, R.layout.spinner_custom, getListaObjects(objects, aClass));
        init(context, objects, aClass, idViewParent);
    }



    private void init(Context context, List objects, Class aClass,int idViewParent) {
        this.mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.objects = getListaObjects(objects, aClass);
        this.aClass = aClass;
        this.idViewParent = idViewParent;
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
        RelativeLayout.LayoutParams margins = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        Drawable icon = ViewUtils.drawableSetTint(context.getResources().getDrawable(idDrawable),
                context.getResources().getColor(R.color.branco));
        switch (idViewParent){
            case R.id.spinner_button_header:
                layoutSpinner.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                titleSpinner.setTextColor(context.getResources().getColor(R.color.textColorCinzaEscuro));
                int top = position == 0 ? 10 : 0;
                int botton = (objects.size() - 1) == position ? 10 : 0;
                margins.setMargins(10, top, 10, botton);
                titleSpinner.setBackgroundColor(context.getResources().getColor(R.color.backgroundEditText));
                titleSpinner.setLayoutParams(margins);
                titleSpinner.setPadding(40,10,40,10);
                icon = ViewUtils.drawableSetTint(icon,context.getResources().getColor(R.color.textColorCinzaEscuro));
                break;
            case R.id.spinner_header_categoria:
                margins.setMargins(10, 10, 10, 10);
                titleSpinner.setLayoutParams(margins);
                layoutSpinner.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                break;
            default:
                margins.setMargins(10, 10, 10, 10);
                titleSpinner.setLayoutParams(margins);
                layoutSpinner.setBackground(context.getResources().getDrawable(R.drawable.background_edit_text));
                titleSpinner.setTextColor(context.getResources().getColor(R.color.textColorCinzaEscuro));
                icon = ViewUtils.drawableSetTint(icon,context.getResources().getColor(R.color.textColorCinzaEscuro));
                break;
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
        switch (idViewParent){
            case R.id.spinner_header_categoria:
                break;
            default:
                titleSpinner.setTextColor(context.getResources().getColor(R.color.textColorCinzaEscuro));
                icon = ViewUtils.drawableSetTint(icon,context.getResources().getColor(R.color.textColorCinzaEscuro));
                break;
        }

        titleSpinner.setCompoundDrawablesWithIntrinsicBounds(null,null,icon,null);
    }

}
