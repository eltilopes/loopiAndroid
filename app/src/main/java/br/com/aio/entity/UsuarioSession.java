package br.com.aio.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

import br.com.aio.model.Convite;

/**
 * Created by elton on 23/09/2017.
 */

public class UsuarioSession implements Serializable {

    @Expose
    private Integer id;
    @Expose
    private String nome;
    @Expose
    private String senha;
    private String login;
    private String token;
    private String cpf;
    private String telefone;
    private List<Role> roles;
    private String idUsuarioGlpi;

    public UsuarioSession() {

    }

    public UsuarioSession(String login, String senha, String nome, String token, String idUsuarioGlpi) {
        this.login = login;
        this.senha = senha;
        this.token = token;
        this.idUsuarioGlpi = idUsuarioGlpi;
    }

    public UsuarioSession(Integer id, String nome, String cpf) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
    }

    public UsuarioSession(Convite convite, String senha) {
        login = convite.getEmail();
        this.senha = senha;
        nome = convite.getNome();
        cpf = convite.getCpf();
    }

    public UsuarioSession(Integer id, String login, String senha, String nome, String token, String idUsuarioGlpi, String cpf, List<Role> roles) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.token = token;
        this.roles = roles;
        this.cpf = cpf;
        this.idUsuarioGlpi = idUsuarioGlpi;
        this.id = id;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getIdUsuarioGlpi() {
        return idUsuarioGlpi;
    }
    public void setIdUsuarioGlpi(String idUsuarioGlpi) {
        this.idUsuarioGlpi = idUsuarioGlpi;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<Role> getRoles() { return roles; }
    public void setRoles(List<Role> roles) { this.roles = roles; }

    public boolean isLogado(){
        return getToken() != null && !"".equals(getToken());
    }
}