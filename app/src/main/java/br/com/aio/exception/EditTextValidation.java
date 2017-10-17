package br.com.aio.exception;

import android.content.Context;

import br.com.aio.R;

/**
 * Created by elton on 16/10/2017.
 */

public class EditTextValidation {

    private String descricao;
    private int cor;

    public EditTextValidation(String descricao, int forca, Context context) {
        this.descricao = descricao;
        this.cor = getCorForca(forca, context);
    }

    private int getCorForca(int forca, Context context) {
        int color = context.getResources().getColor(R.color.textColorInfoVerde);
        switch (forca){
            case 4:
                color =  context.getResources().getColor(R.color.textColorInfoVerde);
                break;
            case 3:
                color =  context.getResources().getColor(R.color.textColorInfoAmarelo);
                break;
            case 2:
                color =  context.getResources().getColor(R.color.textColorInfoAmarelo);
                break;
            case 1:
                color =  context.getResources().getColor(R.color.textColorInfoVermelho);
                break;
        }

        return color;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCor() {
        return cor;
    }

    public void setCor(int cor) {
        this.cor = cor;
    }
}
