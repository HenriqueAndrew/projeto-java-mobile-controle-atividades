package br.com.henriqueandrew.controleatividades.modelo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.henriqueandrew.controleatividades.R;

public class AtivProfArrayAdapter extends RecyclerView.Adapter<AtivProfArrayAdapter.FixView> {

    private List<AtividadeProfissional> atividadesp;

    public class FixView extends RecyclerView.ViewHolder{

        TextView textViewNmCliente, textViewData, textViewHrInicial, textViewHrFinal, textViewSetor, textViewSistema, textViewTpAtividade, textViewAtividade;

        public FixView(@NonNull View itemView) {
            super(itemView);

            textViewNmCliente = itemView.findViewById(R.id.textView_adap_nome);
            textViewData = itemView.findViewById(R.id.textView_adap_data);
            textViewHrInicial = itemView.findViewById(R.id.textView_adap_horaI);
            textViewHrFinal = itemView.findViewById(R.id.textView_adap_horaF);
            textViewSetor = itemView.findViewById(R.id.textView_adap_Setor);
            textViewSistema = itemView.findViewById(R.id.textView_adap_Sistema);
            textViewTpAtividade = itemView.findViewById(R.id.textView_adap_tp_ativ);
            textViewAtividade = itemView.findViewById(R.id.textView_adap_ativ);
        }
    }

    public AtivProfArrayAdapter(List<AtividadeProfissional> atividades) {
        this.atividadesp = atividades;
    }

    @NonNull
    @Override
    public FixView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View itemLista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_historico_prof, viewGroup, false);

        return new FixView(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull FixView fixView, int i){

        AtividadeProfissional atividadeProfissional = atividadesp.get(i);

        fixView.textViewNmCliente.setText(atividadeProfissional.getNomeCliente());
        fixView.textViewData.setText(atividadeProfissional.getData());
        fixView.textViewHrInicial.setText(atividadeProfissional.getInicial());
        fixView.textViewHrFinal.setText(atividadeProfissional.getFinal());
        fixView.textViewSetor.setText(atividadeProfissional.getSetor());
        fixView.textViewSistema.setText(atividadeProfissional.getSistema());
        fixView.textViewTpAtividade.setText(atividadeProfissional.getTipoAtividade());
        fixView.textViewAtividade.setText(atividadeProfissional.getAtividade());
    }

    @Override
    public int getItemCount() {
        return atividadesp.size();
    }

}
