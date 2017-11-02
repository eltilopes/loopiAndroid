package br.com.aio.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by elton on 23/09/2017.
 */

public class Categoria extends SpinnerModel implements Serializable{

    @Expose
    private Integer id;
    @Expose
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
        super.setDescricaoSpinner(descricao);
        this.descricao = descricao;
    }

    public static List<Categoria> getCategorias(){
        List<Categoria> categorias = new ArrayList<Categoria>();
        categorias.add(new Categoria(1,"Saúde"));
        categorias.add(new Categoria(2,"Alimentação"));
        categorias.add(new Categoria(3,"Beleza"));
        return categorias;
    }

    public static List<Categoria> prepareCategorias(List<Categoria> categorias){
        for(Categoria c : categorias){
            c.setDescricao(c.getDescricao());
        }
        return categorias;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Categoria other = (Categoria) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
