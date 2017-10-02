package br.com.aio.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elton on 23/09/2017.
 */

public class Banco extends SpinnerModel{

    private Integer id;
    private String descricao;

    public Banco() { }

    public Banco(Integer id, String descricao) {
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

    public static List<Banco> getCategorias(){
        List<Banco> categorias = new ArrayList<Banco>();
        categorias.add(new Banco(1,"Banco do Brasil"));
        categorias.add(new Banco(2,"Caixa - CEF"));
        categorias.add(new Banco(3,"Itaú"));
        return categorias;
    }

}
