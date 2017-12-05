package br.com.aio.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.aio.R;
import br.com.aio.activity.ListagemActivity;
import br.com.aio.entity.Categoria;

/**
 * Created by elton on 24/07/2017.
 */

public class CategoriaCardRecyclerViewAdapter extends RecyclerView.Adapter<CategoriaCardRecyclerViewAdapter.CustomViewHolder> {
    private List<Categoria> categorias;
    private Context mContext;
    private OnRecyclerViewCategoriaItemClickListener listener;
    private List<Integer> idColors;

    public interface OnRecyclerViewCategoriaItemClickListener {
        public void onRecyclerViewCategoriaItemClicked(int position, int id);
    }
    public void setOnItemClickListener(OnRecyclerViewCategoriaItemClickListener listener)
    {
        this.listener = listener;
    }
    public CategoriaCardRecyclerViewAdapter(Context context, List<Categoria> categorias) {
        this.mContext = context;
        this.categorias = categorias;
        idColors = new ArrayList<>();
        idColors.add(R.color.color_card_amarelo);
        idColors.add(R.color.color_card_azul);
        idColors.add(R.color.color_card_lilas);
        idColors.add(R.color.color_card_verde);
        idColors.add(R.color.color_card_vermelho);
        idColors.add(R.color.color_card_indigo);
        idColors.add(R.color.color_card_laranja);
    }


    public void addItem(Categoria categoria, int index) {
        categorias.add(index, categoria);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        categorias.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row_card_categoria,null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, final int i) {
        final Categoria categoria = categorias.get(i);
        customViewHolder.cardCategoria.setCardBackgroundColor(mContext.getResources().getColor(idColors.get(i)));
        customViewHolder.nomeCategoria.setText(Html.fromHtml(categoria.getDescricao()));
        customViewHolder.nomeCategoria.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onRecyclerViewCategoriaItemClicked(i, v.getId());
            }
        });
        customViewHolder.cardCategoria.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onRecyclerViewCategoriaItemClicked(i, ListagemActivity.idCard);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != categorias ? categorias.size() : 0);
    }

    class  CustomViewHolder extends RecyclerView.ViewHolder  {
        CardView cardCategoria;
        TextView nomeCategoria;
        public CustomViewHolder(View view) {
            super(view);
            this.cardCategoria = (CardView) view.findViewById(R.id.card_row_categoria);
            this.nomeCategoria = (TextView) view.findViewById(R.id.nome_categoria);
        }


    }
}