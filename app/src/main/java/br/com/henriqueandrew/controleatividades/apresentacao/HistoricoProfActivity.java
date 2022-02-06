package br.com.henriqueandrew.controleatividades.apresentacao;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.henriqueandrew.controleatividades.R;
import br.com.henriqueandrew.controleatividades.modelo.AcoesGUI;
import br.com.henriqueandrew.controleatividades.modelo.AtivProfArrayAdapter;
import br.com.henriqueandrew.controleatividades.modelo.AtividadeProfissional;
import br.com.henriqueandrew.controleatividades.modelo.RecyclerTouchListener;
import br.com.henriqueandrew.controleatividades.persistencia.AtividadesDatabase;

public class HistoricoProfActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAtividades;
    private RecyclerView.LayoutManager layoutManager;
    private AtivProfArrayAdapter atividadeAdapter;
    private List<AtividadeProfissional> lista;

    //private TextView textViewResutado;

    private ActionMode actionMode;
    private int posicaoSel = -1;
    private View viewSelecionada;

    private static final String ARQUIVO = "br.com.henriqueandrew.controleatividades.apresentacao.ORDENACAO_LISTA"; //arquivo de preferencia (shared preferences)
    private static int opcao = R.id.menuItemOrdNome;
    private static String ORDEM = "ORDEM";

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        //inflar menu de ação contextual
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflate = mode.getMenuInflater();
            inflate.inflate(R.menu.historico_prof_itens, menu);
            return true;
        }

        //exibir o menu ação contextual
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        //clique de alguma opção do menu ação contextual
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            switch (item.getItemId()) {
                case R.id.menuItemEditar:
                    editarAtividade();
                    mode.finish(); //sai do menu
                    return true;
                case R.id.menuItemExcluir:
                    excluirAtividade();
                    mode.finish(); //sai do menu
                    return true;
                default:
                    return false;
            }

        }

        //para quando sai do menu (encerrar actionMode)
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if (viewSelecionada != null) {
                viewSelecionada.setBackgroundColor(Color.TRANSPARENT);
            }

            actionMode = null;
            viewSelecionada = null;

            recyclerViewAtividades.setEnabled(true);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_prof);

        //textViewResutado = findViewById(R.id.textViewResultado);

        recyclerViewAtividades = findViewById(R.id.recyclerViewAtividades);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewAtividades.setLayoutManager(layoutManager);
        recyclerViewAtividades.setHasFixedSize(true);
        recyclerViewAtividades.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        recyclerViewAtividades.addOnItemTouchListener(new RecyclerTouchListener(HistoricoProfActivity.this, recyclerViewAtividades, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {

                posicaoSel = position;
                editarAtividade();

                AtividadeProfissional atividadeProfissional = lista.get(posicaoSel);
                Toast.makeText(HistoricoProfActivity.this, atividadeProfissional.getNomeCliente() + " " + getString(R.string.ativ_selecionada), Toast.LENGTH_SHORT).show();

            }

            @Override
            public boolean onLongClick(View view, int position) {

                if (actionMode != null) {
                    return false;
                }

                posicaoSel = position;
                view.setBackgroundColor(Color.LTGRAY);
                viewSelecionada = view;
                recyclerViewAtividades.setEnabled(false);
                actionMode = startActionMode(mActionModeCallback);

                AtividadeProfissional atividadeProfissional = lista.get(posicaoSel);
                Toast.makeText(HistoricoProfActivity.this, atividadeProfissional.getNomeCliente() + " " + getString(R.string.selecionada_clicklongo), Toast.LENGTH_SHORT).show();

                return true;
            }
        }

        ));

        popularLista();
        lerOrdenacaoLista();

    }

    //inflar menu de opções para activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.historico_prof_opcoes, menu);

        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item;
        switch (opcao) {
            case R.id.menuItemOrdNome:
                item = menu.findItem(R.id.menuItemOrdNome);
                break;
            case R.id.menuItemOrdSetor:
                item = menu.findItem(R.id.menuItemOrdSetor);
                break;
            case R.id.menuItemOrdSistema:
                item = menu.findItem(R.id.menuItemOrdSistema);
                break;
            default:
                return false;
        }
        item.setChecked(true);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuItemAdicionar:
                novaAtividade();
                Toast.makeText(HistoricoProfActivity.this, getString(R.string.adicionar) + getString(R.string.foi_clicado), Toast.LENGTH_SHORT).show();
                return true;

            case R.id.menuItemSobre:
                acessarSobre();
                Toast.makeText(HistoricoProfActivity.this, getString(R.string.sobre) + getString(R.string.foi_clicado), Toast.LENGTH_SHORT).show();
                return true;

            case R.id.menuItemOrdNome:
                opcao = R.id.menuItemOrdNome;
                salvarOrdenacaoLista(opcao);
                Toast.makeText(HistoricoProfActivity.this, getString(R.string.toast_ord_nome), Toast.LENGTH_SHORT).show();
                return true;

            case R.id.menuItemOrdSetor:
                opcao = R.id.menuItemOrdSetor;
                salvarOrdenacaoLista(opcao);
                Toast.makeText(HistoricoProfActivity.this, getString(R.string.toast_ord_setor), Toast.LENGTH_SHORT).show();
                return true;

            case R.id.menuItemOrdSistema:
                opcao = R.id.menuItemOrdSistema;
                salvarOrdenacaoLista(opcao);
                Toast.makeText(HistoricoProfActivity.this, getString(R.string.toast_ord_sistema), Toast.LENGTH_SHORT).show();

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //Retorno de startActivityForResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if ((requestCode == CadastroActivity.ADICIONAR || requestCode == CadastroActivity.EDITAR) && resultCode == Activity.RESULT_OK) {

            popularLista();
            atividadeAdapter.notifyDataSetChanged();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void popularLista() {

        AtividadesDatabase database = AtividadesDatabase.getDatabase(this);

        //List<AtividadeProfissional> lista = database.atividadeDAO().queryAll();
        lista = database.atividadeDAO().queryAll();

        atividadeAdapter = new AtivProfArrayAdapter(lista);

        recyclerViewAtividades.setAdapter(atividadeAdapter);

    }

    //Shared Preferences - Ordenar Lista
    private void lerOrdenacaoLista() {

        SharedPreferences shared = getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE);

        opcao = shared.getInt(ORDEM, opcao);

        alterarOrdenacao();
    }

    private void alterarOrdenacao() {

        if (opcao == R.id.menuItemOrdNome) {
            ordenarListaNome();
        }
        if (opcao == R.id.menuItemOrdSetor) {
            ordenarListaSetor();
        }
        if (opcao == R.id.menuItemOrdSistema) {
            ordenarListaSistema();
        }
    }

    private void salvarOrdenacaoLista(int i) {

        SharedPreferences shared = getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = shared.edit();
        editor.putInt(ORDEM, i);
        editor.commit();
        opcao = i;
        alterarOrdenacao();
    }

    private void ordenarListaNome() {
        Collections.sort(lista, new Comparator<AtividadeProfissional>() {
            @Override
            public int compare(AtividadeProfissional o1, AtividadeProfissional o2) {
                return o1.getNomeCliente().compareTo(o2.getNomeCliente());
            }
        });
        atividadeAdapter.notifyDataSetChanged();
    }

    private void ordenarListaSetor() {
        Collections.sort(lista, new Comparator<AtividadeProfissional>() {
            @Override
            public int compare(AtividadeProfissional o1, AtividadeProfissional o2) {
                return o1.getSetor().compareTo(o2.getSetor());
            }
        });
        atividadeAdapter.notifyDataSetChanged();
    }

    private void ordenarListaSistema() {
        Collections.sort(lista, new Comparator<AtividadeProfissional>() {
            @Override
            public int compare(AtividadeProfissional o1, AtividadeProfissional o2) {
                return o1.getSistema().compareTo(o2.getSistema());
            }
        });
        atividadeAdapter.notifyDataSetChanged();
    }

    public void novaAtividade() {
        CadastroActivity.novaAtividade(this);
    }

    // startActivity - Botão Sobre (acessarSobre)
    public void acessarSobre() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void excluirAtividade() {
        AtividadeProfissional atividade = lista.get(posicaoSel);

        String msg = getString(R.string.confirmar_exclusao) + " " + atividade.getNomeCliente() + " ?";

        DialogInterface.OnClickListener listener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                AtividadesDatabase atividadesDatabase = AtividadesDatabase.getDatabase(HistoricoProfActivity.this);
                                atividadesDatabase.atividadeDAO().delete (atividade);

                                lista.remove(posicaoSel);
                                atividadeAdapter.notifyDataSetChanged();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
        AcoesGUI.confirmaExclusao(this, msg, listener);
    }


    private void editarAtividade() {
        AtividadeProfissional atividade = lista.get(posicaoSel);
        CadastroActivity.editarAtividade(this, atividade);
    }
}