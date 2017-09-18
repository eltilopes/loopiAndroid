package br.com.aio.adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elton on 24/07/2017.
 */

public class ServicoCard {

    private String title;
    private String thumbnail;
    private String categoria;
    private String subCategoria;
    private String especialidade;
    private Integer estrelas;
    private Boolean favorito;


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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(String subCategoria) {
        this.subCategoria = subCategoria;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public Integer getEstrelas() {
        return estrelas;
    }

    public void setEstrelas(Integer estrelas) {
        this.estrelas = estrelas;
    }

    public Boolean getFavorito() {
        return favorito;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }

    public ServicoCard(){}

    public ServicoCard(String title, String thumbnail,String categoria,String subCategoria,String especialidade,Integer estrelas,Boolean favorito){
        setTitle(title);
        setThumbnail(thumbnail);
        setCategoria(categoria);
        setSubCategoria(subCategoria);
        setEspecialidade(especialidade);
        setFavorito(favorito);
        setEstrelas(estrelas);
    }

    public static List<ServicoCard> getFeeds(){
//        ServicoCard servicoCard1 = new ServicoCard("Serviço Judiciário" ,"http://www.alvestambelli.adv.br/public/img/default/servicos-de-advogados/home/direito-civel.jpg");
//        ServicoCard servicoCard2 = new ServicoCard("Serviço Bombeiro Hidáulico" ,"http://img.olx.com.br/images/41/418516038337171.jpg");
//        ServicoCard servicoCard3 = new ServicoCard("Serviço Eletricista" ,"http://www.hertzinstalacoeseletricas.com.br/images/instalacoes+eletricas+comercial+sp.jpg");
//        ServicoCard servicoCard4 = new ServicoCard("Manicure" ,"http://www.spetacollare.com.br/wp-content/uploads/2016/06/manicure.jpg");
//        ServicoCard servicoCard5 = new ServicoCard("Médica" ,"http://www.medicel.com.br/galeria/noticia/49/thumb_20141020113329168.jpg");
//        ServicoCard servicoCard6 = new ServicoCard("Entrega Água" ,"https://static9.depositphotos.com/1051435/1221/i/950/depositphotos_12217291-stock-photo-bottled-water-delivery-service.jpg");
        List<ServicoCard> lista = new ArrayList<ServicoCard>();
        lista.add(new ServicoCard("Eduardo Turma","http://www.vereadoreduardotuma.com.br/img/Foto-Site-FundoBranco-500x750.png","Direito","Advogado","Trabalhista",4,true));
        lista.add(new ServicoCard("Lucia Alves","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQm2E2fsLZZuoVqHKAuGt4wnedIjS8wxgHC5QobZpHAJLDpAJxqlQ","Estética","Manicure","",2,false));
        lista.add(new ServicoCard("Fernanda Reis","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTj64LmoyztSuU8vYyiVh4WimnAUM_FjCKR1ChzTYsUNdIf8TnZ","Saúde","Médica","Pediatra",5,true));
        lista.add(new ServicoCard("Ana Maria","https://st2.depositphotos.com/1465849/9046/i/950/depositphotos_90464920-stock-photo-beautiful-blonde-doctor-in-white.jpg","Saúde","Médica","Nutricionista",5,true));
        lista.add(new ServicoCard("Cláudia Bertran","http://1.bp.blogspot.com/-7zZcgKxners/UgpFo_8xyuI/AAAAAAAAFvA/rSVWUtn0NuE/s1600/3.jpg","Saúde","Médica","Ginecologista",4,false));
        lista.add(new ServicoCard("Antônio José","https://s3-media1.fl.yelpcdn.com/bphoto/Ne2scwgWQNzAD0x0rrag3Q/258s.jpg","Geral","Elétrica","Casa",3,true));
        lista.add(new ServicoCard("Mercadinho","https://static9.depositphotos.com/1051435/1221/i/950/depositphotos_12217291-stock-photo-bottled-water-delivery-service.jpg","Alimentação","Água","",2,false));

        return lista;

    }
}