package br.com.aio.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

import br.com.aio.enumeration.TipoLocalizacao;

/**
 * Created by elton on 19/09/2017.
 */

public class Localizacao implements Serializable {

    @Expose
    private Long id;
    private String nome;
    @Expose
    private Double latitude;
    @Expose
    private Double longitude;
    private TipoLocalizacao tipoLocalizacao;

    public Localizacao(Long id, String nome,Double latitude, Double longitude) {
        this.id = id;
        this.nome = nome;
        this.latitude = latitude;
        this.longitude = longitude;
        tipoLocalizacao = TipoLocalizacao.INFORMADA;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public TipoLocalizacao getTipoLocalizacao() {
        return tipoLocalizacao;
    }

    public void setTipoLocalizacao(TipoLocalizacao tipoLocalizacao) {
        this.tipoLocalizacao = tipoLocalizacao;
    }
}
