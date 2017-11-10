package br.com.aio.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

/**
 * Created by elton on 23/09/2017.
 */

public class Especialidade extends SpinnerModel implements Serializable {

    @Expose
    private Integer id;
    @Expose
    private String descricao;
    @Expose
    private SubCategoria subCategoria;

    public Especialidade(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
        super.setDescricaoSpinner(descricao);
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
        super.setDescricaoSpinner(descricao);
        this.descricao = descricao;
    }

    public SubCategoria getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(SubCategoria subCategoria) {
        this.subCategoria = subCategoria;
    }

    public static List<Especialidade> prepareEspecialidades(List<Especialidade> especialidades) {
        for(Especialidade e : especialidades){
            e.setDescricao(e.getDescricao());
        }
        return especialidades;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Especialidade other = (Especialidade) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
