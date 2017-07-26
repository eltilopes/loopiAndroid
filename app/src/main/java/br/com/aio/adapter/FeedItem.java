package br.com.aio.adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elton on 24/07/2017.
 */

public class FeedItem {

    private String title;
    private String thumbnail;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public FeedItem(){}

    public FeedItem(String title, String thumbnail){
        setTitle(title);
        setThumbnail(thumbnail);
    }

    public static List<FeedItem> getFeeds(){
        FeedItem feedItem1 = new FeedItem("Serviço Judiciário" ,"http://www.alvestambelli.adv.br/public/img/default/servicos-de-advogados/home/direito-civel.jpg");
        FeedItem feedItem2 = new FeedItem("Serviço Bombeiro Hidáulico" ,"http://img.olx.com.br/images/41/418516038337171.jpg");
        FeedItem feedItem3 = new FeedItem("Serviço Eletricista" ,"http://www.hertzinstalacoeseletricas.com.br/images/instalacoes+eletricas+comercial+sp.jpg");
        FeedItem feedItem4 = new FeedItem("Manicure" ,"http://www.spetacollare.com.br/wp-content/uploads/2016/06/manicure.jpg");
        FeedItem feedItem5 = new FeedItem("Médica" ,"http://www.medicel.com.br/galeria/noticia/49/thumb_20141020113329168.jpg");
        FeedItem feedItem6 = new FeedItem("Entrega Água" ,"https://static9.depositphotos.com/1051435/1221/i/950/depositphotos_12217291-stock-photo-bottled-water-delivery-service.jpg");
        List<FeedItem> lista = new ArrayList<FeedItem>();
        lista.add(feedItem1);
        lista.add(feedItem2);
        lista.add(feedItem3);
        lista.add(feedItem4);
        lista.add(feedItem5);
        lista.add(feedItem6);
        return lista;

    }
}