package br.com.henriqueandrew.controleatividades.persistencia;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.henriqueandrew.controleatividades.modelo.AtividadeProfissional;

@Dao
public interface AtividadeDAO {

    @Insert
    long insert(AtividadeProfissional atividadeProfissional);

    @Update
    void update(AtividadeProfissional atividadeProfissional);

    @Delete
    void delete(AtividadeProfissional atividadeProfissional);

    @Query("SELECT * FROM atividadeprofissional WHERE id = :id")
    AtividadeProfissional queryForId(long id);

    @Query("SELECT * FROM atividadeprofissional ORDER BY id ASC")
    List<AtividadeProfissional> queryAll();

}
