package br.com.aio.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

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
    private Date dataLocalizacao;
    private String ultimaLocalizacao;
    private TipoLocalizacao tipoLocalizacao;

    public Localizacao() {   }

    public Localizacao(Long id, String nome,Double latitude, Double longitude) {
        this.id = id;
        this.nome = nome;
        this.latitude = latitude;
        this.longitude = longitude;
        dataLocalizacao = new Date();
        ultimaLocalizacao = DateFormat.getTimeInstance().format(dataLocalizacao);
        tipoLocalizacao = TipoLocalizacao.INFORMADA;
    }

    public Localizacao(Double latitude, Double longitude, String nome) {
        dataLocalizacao = new Date();
        ultimaLocalizacao = DateFormat.getTimeInstance().format(dataLocalizacao);
        this.latitude = latitude;
        this.longitude = longitude;
        this.nome = nome;
    }


    public Date getDataLocalizacao() {
        return dataLocalizacao;
    }

    public void setDataLocalizacao(Date dataLocalizacao) {
        this.dataLocalizacao = dataLocalizacao;
    }

    public String getUltimaLocalizacao() {
        return ultimaLocalizacao;
    }

    public void setUltimaLocalizacao(String ultimaLocalizacao) {
        this.ultimaLocalizacao = ultimaLocalizacao;
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
