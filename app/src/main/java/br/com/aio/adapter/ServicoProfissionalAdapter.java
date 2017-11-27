package br.com.aio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

import br.com.aio.R;
import br.com.aio.entity.ServicoProfissional;
import br.com.aio.fonts.RobotoTextView;
import br.com.aio.utils.ViewUtils;

/**
 * Created by elton on 21/11/2017.
 */

public class ServicoProfissionalAdapter  extends ArrayAdapter<ServicoProfissional> {

    private List<ServicoProfissional> servicoProfissionals ;
    private List<ServicoProfissional> servicosSelecionados ;
    private Context context;
    private LayoutInflater inflator;
    private ServicoProfissionalAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        public void OnItemClickListener(int position, int id);
    }
    public void setOnItemClickListener(ServicoProfissionalAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public ServicoProfissionalAdapter( Context context,List<ServicoProfissional> servicoProfissionals ) {
        super(context ,R.layout.list_row_servico_profissional, servicoProfissionals);
        this.servicoProfissionals = servicoProfissionals;
        inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        servicosSelecionados = new ArrayList<>();
    }

    public int getCount() {
        return servicoProfissionals.size();
    }

    public ServicoProfissional getItem(int position) {
        return servicoProfissionals.get(position);
    }

    public List<ServicoProfissional> getServicosSelecionados() {
        return servicosSelecionados;
    }

    public View getView(final int position, final View convertView, ViewGroup parent) {
        View vi=convertView;

        if(convertView==null) {
            vi = inflator.inflate(R.layout.list_row_servico_profissional, null);
        }
            RobotoTextView nome = (RobotoTextView) vi.findViewById(R.id.nome_servico_profissional);
            RobotoTextView valor = (RobotoTextView) vi.findViewById(R.id.valor_servico_profissional);
            RobotoTextView descrição = (RobotoTextView) vi.findViewById(R.id.descricao_servico_profissional);
            final CheckBox aceitar = (CheckBox) vi.findViewById(R.id.selecionar_servico_profissional);

            final ServicoProfissional servicoProfissional = servicoProfissionals.get(position);

            nome.setText(servicoProfissional.getNome());
            valor.setText(ViewUtils.getValorFormatado(servicoProfissional.getValor()));
            descrição.setText(servicoProfissional.getDescricao());
            aceitar.setTag(servicoProfissional.getNome());
            vi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    aceitar.setChecked(!aceitar.isChecked());
                    listener.OnItemClickListener(position, v.getId());
                }
            });
            aceitar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    aceitar.setChecked(!aceitar.isChecked());
                    listener.OnItemClickListener(position, v.getId());
                }
            });

        vi.setMinimumHeight(parent.getHeight()/getCount());

        return vi;
    }
}
