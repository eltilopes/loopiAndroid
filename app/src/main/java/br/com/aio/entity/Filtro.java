package br.com.aio.entity;

/**
 * Created by elton on 01/11/2017.
 */

public class Filtro {

    private Categoria categoria;
    private SubCategoria subCategoria;
    private Especialidade especialidade;
    private Boolean menorValor;
    private Boolean distanciaMenor;

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
}
