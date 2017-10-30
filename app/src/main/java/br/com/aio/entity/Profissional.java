package br.com.aio.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

/**
 * Created by elton on 23/09/2017.
 */

public class Profissional implements Serializable{

    @Expose
    private Integer id;
    @Expose
    private UsuarioSession usuario;
    @Expose
    private Categoria categoria;
    @Expose
    private SubCategoria subCategoria;
    @Expose
    private Especialidade especialidade;
    @Expose
    private List<ServicoProfissional> servicos;

    public Profissional(Integer id, UsuarioSession usuario) {
        this.id = id;
        this.usuario = usuario;
    }

    public Profissional(Integer id, UsuarioSession usuario, Categoria categoria, SubCategoria subCategoria, Especialidade especialidade, List<ServicoProfissional> servicos) {
        this.id = id;
        this.usuario = usuario;
        this.categoria = categoria;
        this.subCategoria = subCategoria;
        this.especialidade = especialidade;
        this.servicos = servicos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UsuarioSession getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioSession usuario) {
        this.usuario = usuario;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public SubCategoria getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(SubCategoria subCategoria) {
        this.subCategoria = subCategoria;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public List<ServicoProfissional> getServicos() {
        return servicos;
    }

    public void setServicos(List<ServicoProfissional> servicos) {
        this.servicos = servicos;
    }
}
