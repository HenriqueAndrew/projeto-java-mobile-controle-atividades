package br.com.henriqueandrew.controleatividades.modelo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AtividadeProfissional {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    private String nomeCliente;
    private String data;
    public String hInicial;
    public String hFinal;
    private String setor;
    private String sistema;
    private String tipoAtividade;
    private String atividade;

    //Construtores
    public  AtividadeProfissional(){
    }

    public AtividadeProfissional(String nomeCliente){
        setNomeCliente(nomeCliente);
    }

    //getters e setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomeCliente() {

        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {

        this.nomeCliente = nomeCliente;
    }

    public String getData() {

        return data;
    }

    public void setData(String data) {

        this.data = data;
    }

    public String getInicial() {

        return hInicial;
    }

    public void setInicial(String hInicial) {

        this.hInicial = hInicial;
    }

    public String getFinal() {

        return hFinal;
    }

    public void setFinal(String hFinal) {

        this.hFinal = hFinal;
    }

    public String getSetor() {

        return setor;
    }

    public void setSetor(String setor) {

        this.setor = setor;
    }

    public String getSistema() {

        return sistema;
    }

    public void setSistema(String sistema) {

        this.sistema = sistema;
    }

    public String getTipoAtividade() {

        return tipoAtividade;
    }

    public void setTipoAtividade(String tipoAtividade) {

        this.tipoAtividade = tipoAtividade;
    }

    public String getAtividade() {

        return atividade;
    }

    public void setAtividade(String atividade) {

        this.atividade = atividade;
    }

    @Override
    public String toString(){
        return  getNomeCliente() + " | " + getData() + " | " + getInicial() + "-" + getFinal() +"\n" + getSetor() + " - " + getSistema() + " - " + getTipoAtividade() + "\n" + getAtividade();
    }

}
