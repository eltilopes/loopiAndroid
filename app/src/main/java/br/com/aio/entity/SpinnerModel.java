package br.com.aio.entity;

import java.io.Serializable;

/**
 * Created by elton on 25/09/2017.
 */

public class SpinnerModel implements Serializable{

    private String descricaoSpinner;

    public String getDescricaoSpinner() {
        return descricaoSpinner;
    }

    public void setDescricaoSpinner(String descricaoSpinner) {
        this.descricaoSpinner = descricaoSpinner;
    }

    @Override
    public String toString() {
        return descricaoSpinner;
    }

  /*  public static Object getObject(List<SpinnerModel> lista, String descricao,Class aClass ){
        Object retorno = null;
        try {
            retorno = getObjectDefaultSpinner(aClass);
        } catch (Exception e) {
        }

        for( SpinnerModel spinnerModel : lista){
            if(spinnerModel.getDescricao().equals(descricao)){
                retorno = spinnerModel;
            }
        }
        return retorno;
    }*/
}
