package br.com.aio.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by elton on 23/09/2017.
 */

public class SubCategoria extends SpinnerModel implements Serializable {

    @Expose
    private Integer id;
    @Expose
    private String descricao;
    @Expose
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
        super.setDescricaoSpinner(descricao);
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

    public static List<SubCategoria> prepareSubCategorias(List<SubCategoria> subCategorias) {
        for(SubCategoria sc : subCategorias){
            sc.setDescricao(sc.getDescricao());
        }
        return subCategorias;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SubCategoria other = (SubCategoria) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
