package br.com.aio.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
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
import br.com.aio.activity.ListagemActivity;
import br.com.aio.entity.Filtro;
import br.com.aio.entity.ServicoCard;
import br.com.aio.fonts.MaterialDesignIconsTextView;
import br.com.aio.fonts.RobotoTextView;
import br.com.aio.utils.ViewUtils;

/**
 * Created by elton on 24/07/2017.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.CustomViewHolder> {
    private List<ServicoCard> servicoCardList;
    private Context mContext;
    private OnRecyclerViewItemClickListener listener;

    public interface OnRecyclerViewItemClickListener {
        public void onRecyclerViewItemClicked(int position, int id);
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener)
    {
        this.listener = listener;
    }
    public MyRecyclerViewAdapter(Context context, List<ServicoCard> servicoCardList, Filtro filtro) {
        this.mContext = context;
        this.servicoCardList = servicoCardList;
    }


    public void addItem(ServicoCard servicoCard, int index) {
        servicoCardList.add(index, servicoCard);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        servicoCardList.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row_card,null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, final int i) {
        final ServicoCard servicoCard = servicoCardList.get(i);
        boolean multiServicos = servicoCard.getServicos() != null ?
                (servicoCard.getServicos().isEmpty() ? false : (servicoCard.getServicos().size() > 1 ? true : false)) : false;
        //Render image using Picasso library
        if (!TextUtils.isEmpty(servicoCard.getThumbnail())) {
            Picasso.with(mContext).load(servicoCard.getThumbnail())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(customViewHolder.thumbnail);
        }

        //Setting text view title
        customViewHolder.title.setText(Html.fromHtml(servicoCard.getTitle()));
        customViewHolder.descricao.setText(multiServicos ? "" :Html.fromHtml(servicoCard.getDescricao()));
        customViewHolder.categoria.setText(Html.fromHtml(servicoCard.getCategoria().getDescricao()));
        customViewHolder.preco.setText(multiServicos ? "" : Html.fromHtml(ViewUtils.getValorFormatado(servicoCard.getPreco())));
        customViewHolder.tempo.setText(Html.fromHtml(servicoCard.getDuracao()));
        customViewHolder.localizacao.setText(Html.fromHtml(servicoCard.getDistancia()));
        customViewHolder.subCategoria.setText(Html.fromHtml(servicoCard.getSubCategoria().getDescricao()));
        customViewHolder.especialidade.setText(Html.fromHtml(servicoCard.getEspecialidade().getDescricao()));
        customViewHolder.favorito.setImageResource(servicoCard.getFavorito()? R.drawable.ic_thumb_up_full : R.drawable.ic_thumb_up_empty);
        customViewHolder.estrela1.setText(servicoCard.getEstrelas()>0? R.string.material_icon_star : R.string.material_icon_star_border);
        customViewHolder.estrela2.setText(servicoCard.getEstrelas()>1? R.string.material_icon_star : R.string.material_icon_star_border);
        customViewHolder.estrela3.setText(servicoCard.getEstrelas()>2? R.string.material_icon_star : R.string.material_icon_star_border);
        customViewHolder.estrela4.setText(servicoCard.getEstrelas()>3? R.string.material_icon_star : R.string.material_icon_star_border);
        customViewHolder.estrela5.setText(servicoCard.getEstrelas()>4? R.string.material_icon_star : R.string.material_icon_star_border);
        customViewHolder.favoritoQuantidade.setText(servicoCard.getQuantidadeFavorito()+"");
        customViewHolder.favorito.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onRecyclerViewItemClicked(i, v.getId());
            }
        });
        customViewHolder.favoritoQuantidade.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onRecyclerViewItemClicked(i, v.getId());
            }
        });
        customViewHolder.cardServico.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onRecyclerViewItemClicked(i, ListagemActivity.idCard);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != servicoCardList ? servicoCardList.size() : 0);
    }

    class  CustomViewHolder extends RecyclerView.ViewHolder  {
        CardView cardServico;
        ImageView thumbnail;
        ImageView favorito;
        TextView title;
        TextView descricao;
        TextView categoria;
        TextView subCategoria;
        TextView especialidade;
        TextView tempo;
        TextView localizacao;
        TextView preco;
        RobotoTextView favoritoQuantidade;
        MaterialDesignIconsTextView estrela1;
        MaterialDesignIconsTextView estrela2;
        MaterialDesignIconsTextView estrela3;
        MaterialDesignIconsTextView estrela4;
        MaterialDesignIconsTextView estrela5;
        public CustomViewHolder(View view) {
            super(view);
            this.cardServico = (CardView) view.findViewById(R.id.card_servico);
            this.thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            this.title = (TextView) view.findViewById(R.id.title);
            this.descricao = (TextView) view.findViewById(R.id.card_descricao);
            this.categoria = (TextView) view.findViewById(R.id.card_categoria);
            this.tempo = (TextView) view.findViewById(R.id.card_tempo);
            this.localizacao = (TextView) view.findViewById(R.id.card_localizacao);
            this.preco = (TextView) view.findViewById(R.id.card_preco);
            this.subCategoria = (TextView) view.findViewById(R.id.card_sub_categoria);
            this.especialidade = (TextView) view.findViewById(R.id.card_especialidade);
            this.favorito = (ImageView) view.findViewById(R.id.card_favorito);
            this.estrela1 = (MaterialDesignIconsTextView) view.findViewById(R.id.card_estrela_1);
            this.estrela2 = (MaterialDesignIconsTextView) view.findViewById(R.id.card_estrela_2);
            this.estrela3 = (MaterialDesignIconsTextView) view.findViewById(R.id.card_estrela_3);
            this.estrela4 = (MaterialDesignIconsTextView) view.findViewById(R.id.card_estrela_4);
            this.estrela5 = (MaterialDesignIconsTextView) view.findViewById(R.id.card_estrela_5);
            this.favoritoQuantidade = (RobotoTextView) view.findViewById(R.id.card_favorito_quantidade);
        }


    }
}