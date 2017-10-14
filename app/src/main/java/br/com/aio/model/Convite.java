package br.com.aio.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by elton on 14/10/2017.
 */

public class Convite implements Serializable {

    public static final String TABLE = "convite";
    public static final String COLUMN_ID_CONVITE = "id_convite";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_CPF = "cpf";
    public static final String COLUMN_CHAVE = "chave";

    @Expose
    private Integer idConvite;

    @Expose
    private String nome;

    @Expose
    private String email;

    @Expose
    private String cpf;

    @Expose
    private String chave;

    public Convite() { super();}

    public Convite(String nome, String email, String cpf) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
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

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }
}
