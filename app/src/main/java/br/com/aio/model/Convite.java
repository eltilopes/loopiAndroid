package br.com.aio.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by elton on 14/10/2017.
 */

public class Convite implements Serializable {

    @Expose
    private Integer idConvite;

    @Expose
    private String nome;

    @Expose
    private String email;

    @Expose
    private String cpf;

    @Expose
    private String telefone;

    @Expose
    private String codigoConvite;

    public Convite() { super();}

    public Convite(String nome, String email, String cpf, String telefone) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public Integer getIdConvite() {
        return idConvite;
    }

    public void setIdConvite(Integer idConvite) {
        this.idConvite = idConvite;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCodigoConvite() {
        return codigoConvite;
    }

    public void setCodigoConvite(String codigoConvite) {
        this.codigoConvite = codigoConvite;
    }
}
