package br.com.aio.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by elton on 23/09/2017.
 */

public class ServicoProfissional implements Serializable {

    @Expose
    private Integer id;
    @Expose
    private String nome;
    @Expose
    private String descricao;
    @Expose
    private Double valor;
    @Expose
    private Integer tempo;
    @Expose
    private Especialidade especialidade;
    @Expose
    private Profissional profissional;

    public ServicoProfissional() {}

    public ServicoProfissional(Profissional profissional) {
        setProfissional(profissional);
        setEspecialidade(profissional.getEspecialidade());
    }

    public ServicoProfissional(ServicoCard servicoCard) {
        setProfissional(servicoCard.getProfissional());
        setEspecialidade(servicoCard.getEspecialidade());
        setId(servicoCard.getId().intValue());
        setNome(servicoCard.getTitle());
        setDescricao(servicoCard.getDescricao());
        setTempo(servicoCard.getTempo());
        setValor(servicoCard.getPreco());
    }

    public ServicoProfissional(Integer id, String descricao) {
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getTempo() {
        return tempo;
    }

    public void setTempo(Integer tempo) {
        this.tempo = tempo;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }
    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }

}
