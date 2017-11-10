package br.com.aio.utils;

import java.util.ArrayList;
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

    public static List<ServicoCard> pesquisaToolbar(List<ServicoCard> servicoCardList, Filtro filtro) {
        List<ServicoCard> resultado = new ArrayList<ServicoCard>();
        if(filtro.getPesquisaToolbar()!=null && !filtro.getPesquisaToolbar().isEmpty()){
            for(ServicoCard s : servicoCardList){
                s.setPesquisaOtimizada(getPesquisaOtimizada(s));
                if(s.getPesquisaOtimizada().toUpperCase().contains(filtro.getPesquisaToolbar().toUpperCase())){
                    resultado.add(s);
                }
            }
        }else {
            resultado = servicoCardList;
        }
        return resultado;
    }

    private static String getPesquisaOtimizada(ServicoCard s) {
        return  s.getTitle() + " - " +
                s.getCategoria().getDescricao() + " - " +
                s.getSubCategoria().getDescricao() + " - " +
                s.getEspecialidade().getDescricao() + " - " +
                s.getCategoria().getDescricao() + " - " +
                s.getDescricao() + " - " +
                s.getDistancia();
    }

    public static List<ServicoCard> filtrarPorCategoria(List<ServicoCard> servicoCardList, Filtro filtro) {
        List<ServicoCard> resultado = new ArrayList<ServicoCard>();
        if(filtro.getCategoria()!=null && filtro.getSubCategoria()==null && filtro.getEspecialidade()==null  ){
            for(ServicoCard s : servicoCardList){
                if(s.getCategoria().equals(filtro.getCategoria())){
                    resultado.add(s);
                }
            }
        }else {
            resultado = servicoCardList;
        }
        return resultado;
    }

    public static List<ServicoCard> filtrarPorSubCategoria(List<ServicoCard> servicoCardList, Filtro filtro) {
        List<ServicoCard> resultado = new ArrayList<ServicoCard>();
        if(filtro.getSubCategoria()!=null && filtro.getEspecialidade()==null ){
            for(ServicoCard s : servicoCardList){
                if(s.getSubCategoria().equals(filtro.getSubCategoria())){
                    resultado.add(s);
                }
            }
        }else {
            resultado = servicoCardList;
        }
        return resultado;
    }

    public static List<ServicoCard> filtrarPorEspecialidade(List<ServicoCard> servicoCardList, Filtro filtro) {
        List<ServicoCard> resultado = new ArrayList<ServicoCard>();
        if(filtro.getEspecialidade()!=null ){
            for(ServicoCard s : servicoCardList){
                if(s.getEspecialidade().equals(filtro.getEspecialidade())){
                    resultado.add(s);
                }
            }
        }else {
            resultado = servicoCardList;
        }
        return resultado;
    }
}
