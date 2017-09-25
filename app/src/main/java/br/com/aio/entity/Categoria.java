package br.com.aio.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elton on 23/09/2017.
 */

public class Categoria extends SpinnerModel{

    private Integer id;
    private String descricao;

    public Categoria() { }

    public Categoria(Integer id, String descricao) {
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

    public static List<Categoria> getCategorias(){
        List<Categoria> categorias = new ArrayList<Categoria>();
        categorias.add(new Categoria(1,"Saúde"));
        categorias.add(new Categoria(2,"Alimentação"));
        categorias.add(new Categoria(3,"Beleza"));
        return categorias;
    }

}
