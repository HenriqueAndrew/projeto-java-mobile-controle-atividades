package br.com.henriqueandrew.controleatividades.persistencia;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.com.henriqueandrew.controleatividades.modelo.AtividadeProfissional;

@Database(entities = {AtividadeProfissional.class}, version = 1, exportSchema = false) //Parametros: lista das entidades a serem persistidas, versão do database, exportar esquema em arquivo JSON(opcional)
public abstract class AtividadesDatabase extends RoomDatabase {

    public abstract AtividadeDAO atividadeDAO();

    private static AtividadesDatabase instance;

    public static AtividadesDatabase getDatabase(final Context context){ //retorna instancia do objeto que manipula o database
        if (instance ==null){ //cria a classe que acessa o database
            synchronized (AtividadesDatabase.class){
                if(instance == null){
                    instance = Room.databaseBuilder(context, AtividadesDatabase.class, "atividades.db").allowMainThreadQueries().build(); //metodo que cria ou abre o database
                }
            }
        }
        return instance; //se já instanciada retorna objeto
    }

}
