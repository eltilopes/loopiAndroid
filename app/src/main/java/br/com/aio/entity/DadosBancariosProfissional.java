package br.com.aio.entity;

import java.io.Serializable;

/**
 * Created by elton on 23/09/2017.
 */

public class DadosBancariosProfissional implements Serializable{

    private Integer id;
    private Profissional profissional;
    private Banco banco;
    private Finalidade finalidade;
    private String agencia;
    private String dvAgencia;
    private String conta;
    private String dvConta;

    public DadosBancariosProfissional(Integer id, Profissional profissional, Banco banco, Finalidade finalidade, String agencia, String dvAgencia, String conta, String dvConta) {
        this.id = id;
        this.profissional = profissional;
        this.banco = banco;
        this.finalidade = finalidade;
        this.agencia = agencia;
        this.dvAgencia = dvAgencia;
        this.conta = conta;
        this.dvConta = dvConta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Finalidade getFinalidade() {
        return finalidade;
    }

    public void setFinalidade(Finalidade finalidade) {
        this.finalidade = finalidade;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getDvAgencia() {
        return dvAgencia;
    }

    public void setDvAgencia(String dvAgencia) {
        this.dvAgencia = dvAgencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getDvConta() {
        return dvConta;
    }

    public void setDvConta(String dvConta) {
        this.dvConta = dvConta;
    }
}
