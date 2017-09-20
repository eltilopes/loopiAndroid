package br.com.aio.enumeration;

/**
 * Created by elton on 19/09/2017.
 */

public enum TipoLocalizacao {

    CASA(1L,"Casa"),TRABALHO(2L,"Trabalho"),INFORMADA(3L,"Informada");

    private Long id;
    private String descricao;

    TipoLocalizacao(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

}
