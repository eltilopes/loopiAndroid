package br.com.aio.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elton on 23/09/2017.
 */

public class Finalidade extends SpinnerModel{

    private Integer id;
    private String descricao;

    public Finalidade() { }

    public Finalidade(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
        super.setDescricaoSpinner(descricao);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static List<Finalidade> getCategorias(){
        List<Finalidade> categorias = new ArrayList<Finalidade>();
        categorias.add(new Finalidade(1,"TED"));
        categorias.add(new Finalidade(2,"DOC"));
        categorias.add(new Finalidade(3,"Crédito em C/C"));
        return categorias;
    }

}
