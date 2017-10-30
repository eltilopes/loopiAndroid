package br.com.aio.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by elton on 23/09/2017.
 */

public class Especialidade implements Serializable {

    @Expose
    private Integer id;
    @Expose
    private String descricao;
    @Expose
    private SubCategoria subCategoria;

    public Especialidade(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
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

    public SubCategoria getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(SubCategoria subCategoria) {
        this.subCategoria = subCategoria;
    }
}
