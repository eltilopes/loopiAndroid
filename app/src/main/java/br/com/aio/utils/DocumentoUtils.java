package br.com.aio.utils;

import java.util.List;

import br.com.aio.entyti.Documento;

/**
 * Created by elton on 31/08/2017.
 */

public class DocumentoUtils {

    public static Documento getDocumento(List<Documento> documentos, Long id){
        for(Documento d : documentos){
            if(d.getId().equals(id)){
                return d;
            }
        }
        return new Documento();
    }
}
