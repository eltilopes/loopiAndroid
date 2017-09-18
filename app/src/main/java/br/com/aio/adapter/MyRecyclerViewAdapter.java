package br.com.aio.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.aio.R;
import br.com.aio.fonts.MaterialDesignIconsTextView;

/**
 * Created by elton on 24/07/2017.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.CustomViewHolder> {
    private List<ServicoCard> servicoCardList;
    private Context mContext;

    public MyRecyclerViewAdapter(Context context, List<ServicoCard> servicoCardList) {
        this.servicoCardList = servicoCardList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row_card, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        ServicoCard servicoCard = servicoCardList.get(i);

        //Render image using Picasso library
        if (!TextUtils.isEmpty(servicoCard.getThumbnail())) {
            Picasso.with(mContext).load(servicoCard.getThumbnail())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(customViewHolder.thumbnail);
        }

        //Setting text view title
        customViewHolder.title.setText(Html.fromHtml(servicoCard.getTitle()));
        customViewHolder.categoria.setText(Html.fromHtml(servicoCard.getCategoria()));
        customViewHolder.subCategoria.setText(Html.fromHtml(servicoCard.getSubCategoria()));
        customViewHolder.especialidade.setText(Html.fromHtml(servicoCard.getEspecialidade()));
        customViewHolder.favorito.setImageResource(servicoCard.getFavorito()? R.drawable.ic_favorite_full : R.drawable.ic_favorite_empty);
        customViewHolder.estrela1.setText(servicoCard.getEstrelas()>0? R.string.material_icon_star : R.string.material_icon_star_border);
        customViewHolder.estrela2.setText(servicoCard.getEstrelas()>1? R.string.material_icon_star : R.string.material_icon_star_border);
        customViewHolder.estrela3.setText(servicoCard.getEstrelas()>2? R.string.material_icon_star : R.string.material_icon_star_border);
        customViewHolder.estrela4.setText(servicoCard.getEstrelas()>3? R.string.material_icon_star : R.string.material_icon_star_border);
        customViewHolder.estrela5.setText(servicoCard.getEstrelas()>4? R.string.material_icon_star : R.string.material_icon_star_border);

    }

    @Override
    public int getItemCount() {
        return (null != servicoCardList ? servicoCardList.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView thumbnail;
        protected ImageView favorito;
        protected TextView title;
        protected TextView categoria;
        protected TextView subCategoria;
        protected TextView especialidade;
        protected MaterialDesignIconsTextView estrela1;
        protected MaterialDesignIconsTextView estrela2;
        protected MaterialDesignIconsTextView estrela3;
        protected MaterialDesignIconsTextView estrela4;
        protected MaterialDesignIconsTextView estrela5;

        public CustomViewHolder(View view) {
            super(view);
            this.thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            this.title = (TextView) view.findViewById(R.id.title);
            this.categoria = (TextView) view.findViewById(R.id.card_categoria);
            this.subCategoria = (TextView) view.findViewById(R.id.card_sub_categoria);
            this.especialidade = (TextView) view.findViewById(R.id.card_especialidade);
            this.favorito = (ImageView) view.findViewById(R.id.card_favorito);
            this.estrela1 = (MaterialDesignIconsTextView) view.findViewById(R.id.card_estrela_1);
            this.estrela2 = (MaterialDesignIconsTextView) view.findViewById(R.id.card_estrela_2);
            this.estrela3 = (MaterialDesignIconsTextView) view.findViewById(R.id.card_estrela_3);
            this.estrela4 = (MaterialDesignIconsTextView) view.findViewById(R.id.card_estrela_4);
            this.estrela5 = (MaterialDesignIconsTextView) view.findViewById(R.id.card_estrela_5);
        }
    }
}