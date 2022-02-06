package br.com.henriqueandrew.controleatividades.modelo;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;


import br.com.henriqueandrew.controleatividades.R;

public class AcoesGUI {

    public static void confirmaExclusao(Context contexto,
                                    String msg,
                                    DialogInterface.OnClickListener listener){

        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);

        builder.setTitle(R.string.gui_excluir_atividade);
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setMessage(msg);

        builder.setPositiveButton(R.string.gui_excluir_sim, listener);
        builder.setNegativeButton(R.string.gui_excluir_nao, listener);

        AlertDialog alert = builder.create();
        alert.show();
    }
}
