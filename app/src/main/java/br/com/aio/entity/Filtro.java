package br.com.aio.entity;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by elton on 01/11/2017.
 */

public class Filtro implements Serializable {

    @Expose
    private Categoria categoria;
    @Expose
    private SubCategoria subCategoria;
    @Expose
    private Especialidade especialidade;
    @Expose
    private Boolean menorValor;
    private Boolean distanciaMenor;
    @Expose
    private Boolean ordemAlfabeticaCrescente;
    @Expose
    private String pesquisaToolbar;
    private LatLng minhaLatLng ;

    public LatLng getMinhaLatLng() {
        return minhaLatLng;
    }

    public void setMinhaLatLng(LatLng minhaLatLng) {
        this.minhaLatLng = minhaLatLng;
    }

    public Boolean getOrdemAlfabeticaCrescente() {
        return ordemAlfabeticaCrescente;
    }

    public void setOrdemAlfabeticaCrescente(Boolean ordemAlfabeticaCrescente) {
        this.ordemAlfabeticaCrescente = ordemAlfabeticaCrescente;
    }

    public Boolean getDistanciaMenor() {
        return distanciaMenor;
    }

    public void setDistanciaMenor(Boolean distanciaMenor) {
        this.distanciaMenor = distanciaMenor;
    }

    public Boolean getMenorValor() {
        return menorValor;
    }

    public void setMenorValor(Boolean menorValor) {
        this.menorValor = menorValor;
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

    public String getPesquisaToolbar() {
        return pesquisaToolbar;
    }

    public void setPesquisaToolbar(String pesquisaToolbar) {
        this.pesquisaToolbar = pesquisaToolbar;
    }

}
