package br.com.aio.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.aio.entity.Filtro;
import br.com.aio.entity.ServicoCard;

/**
 * Created by elton on 09/11/2017.
 */

public class FiltroUtils {

    public static List<ServicoCard>  ordenarServicosPorValor(List<ServicoCard> servicoCardList, Filtro filtro) {
        if(filtro.getMenorValor()!=null){
            final int menorValor = filtro.getMenorValor() ? 1 : -1;
            Collections.sort(servicoCardList, new Comparator<ServicoCard>() {
                @Override
                public int compare(ServicoCard s1, ServicoCard s2) {
                    return Double.compare(s1.getPreco(),s2.getPreco())*menorValor;
                }
            });
        }
        return servicoCardList;
    }

    public static List<ServicoCard>  ordenarServicosPorDistancia(List<ServicoCard> servicoCardList, Filtro filtro) {
        if(filtro.getDistanciaMenor() !=null){
            final int distanciaMenor = filtro.getDistanciaMenor() ? 1 : -1;
            Collections.sort(servicoCardList, new Comparator<ServicoCard>() {
                @Override
                public int compare(ServicoCard s1, ServicoCard s2) {
                    return Integer.compare(s1.getDistanciaMetros(),s2.getDistanciaMetros())*distanciaMenor;
                }
            });
        }
        return servicoCardList;
    }

    public static List<ServicoCard>  ordenarServicosPorNomePrestador(List<ServicoCard> servicoCardList, Filtro filtro) {
        if(filtro.getOrdemAlfabeticaCrescente()!=null){
            final int ordemAlfabeticaCrescente = filtro.getOrdemAlfabeticaCrescente() ? 1 : -1;
            Collections.sort(servicoCardList,
                    new Comparator<ServicoCard>()
                    {
                        public int compare(ServicoCard f1, ServicoCard f2)
                        {
                            return f1.toString().compareTo(f2.toString())*ordemAlfabeticaCrescente;
                        }
                    });
        }
        return servicoCardList;
    }


}
