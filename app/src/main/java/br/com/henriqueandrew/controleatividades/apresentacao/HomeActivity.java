package br.com.henriqueandrew.controleatividades.apresentacao;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import br.com.henriqueandrew.controleatividades.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }

    /*
    //botao para chamar tela de cadastro atividade
    public void chamarCadastro(View view){
        Intent intent = new Intent(this, PrincipalActivity.class); //explicita
        startActivity(intent);
    }

    //botao para chamar tela de consulta das atividades cadastradas
    public void chamarConsulta(View view){
        Intent intent = new Intent(this, HistoricoProfActivity.class); //explicita
        startActivity(intent);
    }
    */
}