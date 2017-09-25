package br.com.aio.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elton on 23/09/2017.
 */

public class SubCategoria extends SpinnerModel {

    private Integer id;
    private String descricao;
    private Categoria categoria;

    public SubCategoria(Integer id, String descricao) {
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public static List<SubCategoria> getSubCategorias(){
        List<SubCategoria> lista = new ArrayList<SubCategoria>();
        lista.add(new SubCategoria(1,"Médicos"));
        lista.add(new SubCategoria(2,"Manicure"));
        lista.add(new SubCategoria(3,"Restaurante"));
        return lista;
    }

}
