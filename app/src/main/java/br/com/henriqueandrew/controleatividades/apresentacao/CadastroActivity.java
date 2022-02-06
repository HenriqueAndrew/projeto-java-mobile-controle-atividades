package br.com.henriqueandrew.controleatividades.apresentacao;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import br.com.henriqueandrew.controleatividades.R;
import br.com.henriqueandrew.controleatividades.modelo.AtividadeProfissional;
import br.com.henriqueandrew.controleatividades.persistencia.AtividadesDatabase;

import static br.com.henriqueandrew.controleatividades.R.id.radioButtonContabilidade;

public class CadastroActivity extends AppCompatActivity {

    private EditText editTextNomeCliente, editTextData, editTextHoraInicial, editTextHoraFinal, editTextAtividade;
    private CheckBox cbpresencial, cbremoto;
    private RadioGroup rgSetores;
    private Spinner spinnerSistema;
    private ArrayList<String> listaSistemas = new ArrayList<>();

    public static final String MODO = "MODO";
    public static final String ID = "ID";
    public static final int ADICIONAR = 1;
    public static final int EDITAR = 2;

    private int modo;
    private AtividadeProfissional atividadeProfissional;

    // startActivityForResult - Botão Adicionar (novoCadastro)
    public static void novaAtividade(AppCompatActivity activity) {

        Intent intent = new Intent(activity, CadastroActivity.class);

        intent.putExtra(MODO, ADICIONAR);

        activity.startActivityForResult(intent, ADICIONAR);
    }

    public static void editarAtividade(AppCompatActivity activity, AtividadeProfissional atividade) {

        Intent intent = new Intent(activity, CadastroActivity.class);

        intent.putExtra(MODO, EDITAR);
        intent.putExtra(ID, atividade.getId());

        activity.startActivityForResult(intent, EDITAR);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //botão up
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        editTextNomeCliente = findViewById(R.id.editTextNomeCliente);
        editTextData = findViewById(R.id.editTextData);
        editTextHoraInicial = findViewById(R.id.editTextHoraInicial);
        editTextHoraFinal = findViewById(R.id.editTextHoraFinal);
        editTextAtividade = findViewById(R.id.editTextAtividade);
        cbpresencial = findViewById(R.id.checkBoxPresencial);
        cbremoto = findViewById(R.id.checkBoxRemoto);
        rgSetores = findViewById(R.id.radioGroupSetores);
        spinnerSistema = findViewById(R.id.spinnerSistema);
        valoresSpinner();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        //if (bundle != null) {
        //modo = bundle.getInt(MODO);

        modo = bundle.getInt(this.MODO, ADICIONAR);

        if (modo == ADICIONAR) {
            setTitle(getString(R.string.registrar_atividade));

            atividadeProfissional = new AtividadeProfissional("");

        } else {

            setTitle(getString(R.string.editar_atividade));

            long id = bundle.getLong(ID);

            AtividadesDatabase database = AtividadesDatabase.getDatabase(this);

            atividadeProfissional = database.atividadeDAO().queryForId(id);

            editTextNomeCliente.setText(atividadeProfissional.getNomeCliente());
            editTextData.setText(atividadeProfissional.getData());
            editTextHoraInicial.setText(atividadeProfissional.getInicial());
            editTextHoraFinal.setText(atividadeProfissional.getFinal());

            String setor = atividadeProfissional.getSetor();
            if (setor.equals(getString(R.string.setor_contabilidade))) {
                rgSetores.check(R.id.radioButtonContabilidade);
            }
            if (setor.equals(getString(R.string.setor_arrecadacao))) {
                rgSetores.check(R.id.radioButtonArrecadacao);
            }
            if (setor.equals(getString(R.string.setor_rh))) {
                rgSetores.check(R.id.radioButtonRH);
            }
            if (setor.equals(getString(R.string.setor_adm))) {
                rgSetores.check(R.id.radioButtonAdm);
            }

            String tp = atividadeProfissional.getTipoAtividade();
            if (tp.equals(getString(R.string.presencial))) {
                cbpresencial.setChecked(true);
            }
            if (tp.equals(getString(R.string.remoto))) {
                cbremoto.setChecked(true);
            }
            if (tp.equals(getString(R.string.presencial) + " e " + getString(R.string.remoto))) {
                cbpresencial.setChecked(true);
                cbremoto.setChecked(true);
            }

            editTextAtividade.setText(atividadeProfissional.getAtividade());
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cadastro_opcoes, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuItemSalvar:
                salvarCampos();
                //Toast.makeText(CadastroActivity.this, getString(R.string.m_salvar) + getString(R.string.foi_clicado), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuItemLimpar:
                limparCampos();
                //Toast.makeText(CadastroActivity.this, getString(R.string.m_limpar) + getString(R.string.foi_clicado), Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void valoresSpinner() {

        listaSistemas.add(getString(R.string.govbrCP));
        listaSistemas.add(getString(R.string.govbrAR));
        listaSistemas.add(getString(R.string.govbrGP));
        listaSistemas.add(getString(R.string.govbrLC));
        listaSistemas.add(getString(R.string.govbrPP));
        listaSistemas.add(getString(R.string.govbrCM));
        listaSistemas.add(getString(R.string.govbrAF));
        listaSistemas.add(getString(R.string.govbrTP));
        listaSistemas.add(getString(R.string.govbrOP));
        listaSistemas.add(getString(R.string.govbrTB));
        listaSistemas.add(getString(R.string.govbrAS));
        listaSistemas.add(getString(R.string.cidadeMOB));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaSistemas);

        spinnerSistema.setAdapter(adapter);
    }

    public void limparCampos() {
        editTextNomeCliente.setText(null);
        editTextData.setText(null);
        editTextHoraInicial.setText(null);
        editTextHoraFinal.setText(null);
        editTextAtividade.setText(null);
        cbpresencial.setChecked(false);
        cbremoto.setChecked(false);
        rgSetores.clearCheck();
        editTextNomeCliente.requestFocus();

        Toast.makeText(this, R.string.campos_limpos, Toast.LENGTH_LONG).show();
    }

    // valida campos e startActivityForResult (passa dados para lista - Result OK)
    public void salvarCampos() {
        String nomeCliente = editTextNomeCliente.getText().toString();
        String data = editTextData.getText().toString();
        String hInicial = editTextHoraInicial.getText().toString();
        String hFinal = editTextHoraFinal.getText().toString();
        String atividade = editTextAtividade.getText().toString();
        String tipoAtividade = "";
        String setor = "";
        String sistema = (String) spinnerSistema.getSelectedItem();

        //valida campos
        if (nomeCliente == null || nomeCliente.trim().isEmpty()) {
            Toast.makeText(this, getString(R.string.erro_nomeCliente), Toast.LENGTH_SHORT).show();
            editTextNomeCliente.requestFocus();
            return;
        }
        if (data == null || data.trim().isEmpty()) {
            Toast.makeText(this, getString(R.string.erro_data), Toast.LENGTH_SHORT).show();
            editTextData.requestFocus();
            return;
        }
        if (hInicial == null || hInicial.trim().isEmpty()) {
            Toast.makeText(this, getString(R.string.erro_horaInicial), Toast.LENGTH_SHORT).show();
            editTextHoraInicial.requestFocus();
            return;
        }
        if (hFinal == null || hFinal.trim().isEmpty()) {
            Toast.makeText(this, getString(R.string.erro_horaFinal), Toast.LENGTH_SHORT).show();
            editTextHoraFinal.requestFocus();
            return;
        }
        if (atividade == null || atividade.trim().isEmpty()) {
            Toast.makeText(this, getString(R.string.erro_atividade), Toast.LENGTH_SHORT).show();
            editTextAtividade.requestFocus();
            return;
        }
        if ((cbpresencial.isChecked()) && (!cbremoto.isChecked())) {
            tipoAtividade = getString(R.string.presencial);
        }
        if ((cbremoto.isChecked()) && (!cbpresencial.isChecked())) {
            tipoAtividade = getString(R.string.remoto);
        }
        if ((cbpresencial.isChecked()) && (cbremoto.isChecked())) {
            tipoAtividade = getString(R.string.presencial) + " e " + getString(R.string.remoto);
        }
        if (tipoAtividade.equals("")) {
            Toast.makeText(this, getString(R.string.erro_tipoAtividade), Toast.LENGTH_SHORT).show();
            cbpresencial.requestFocus();
            return;
        }
        switch (rgSetores.getCheckedRadioButtonId()) {
            case radioButtonContabilidade:
                setor = getString(R.string.setor_contabilidade);
                break;
            case R.id.radioButtonArrecadacao:
                setor = getString(R.string.setor_arrecadacao);
                break;
            case R.id.radioButtonRH:
                setor = getString(R.string.setor_rh);
                break;
            case R.id.radioButtonAdm:
                setor = getString(R.string.setor_adm);
                break;
            default:
                Toast.makeText(this, getString(R.string.erro_setor), Toast.LENGTH_SHORT).show();
                rgSetores.requestFocus();
                return;
        }

        //emite mensagem Toast sobre dados salvos
        Toast.makeText(this, R.string.aviso_atividadeSalva, Toast.LENGTH_LONG).show();
        Toast.makeText(this, "Cliente: " + nomeCliente +
                getString(R.string.toast_data) + data +
                getString(R.string.toast_hora) + hInicial + " - " + hFinal +
                getString(R.string.toast_setor) + setor +
                getString(R.string.toast_tipo_atendimento) + tipoAtividade +
                getString(R.string.toast_sistema) + sistema +
                getString(R.string.toast_atividade) + atividade, Toast.LENGTH_LONG).show();

        atividadeProfissional.setNomeCliente(nomeCliente);
        atividadeProfissional.setData(data);
        atividadeProfissional.setInicial(hInicial);
        atividadeProfissional.setFinal(hFinal);
        atividadeProfissional.setSetor(setor);
        atividadeProfissional.setSistema(sistema);
        atividadeProfissional.setTipoAtividade(tipoAtividade);
        atividadeProfissional.setAtividade(atividade);

        AtividadesDatabase database = AtividadesDatabase.getDatabase(this);

        if (modo == ADICIONAR) {
            database.atividadeDAO().insert(atividadeProfissional);
        } else {
            database.atividadeDAO().update(atividadeProfissional);
        }

        setResult(Activity.RESULT_OK);
        finish();
    }

    // startActivityForResult (cancelar chamada - result canceled)
    private void cancelar() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    public void onBackPressed() {
        cancelar();
    }
}