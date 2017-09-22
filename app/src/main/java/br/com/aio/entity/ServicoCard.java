package br.com.aio.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by elton on 24/07/2017.
 */

public class ServicoCard implements Serializable {

    private Long id;
    private String title;
    private String thumbnail;
    private String categoria;
    private String subCategoria;
    private String especialidade;
    private Double preco;
    private String distancia;
    private Integer tempo;
    private Integer estrelas;
    private Boolean favorito;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public Integer getTempo() {
        return tempo;
    }

    public void setTempo(Integer tempo) {
        this.tempo = tempo;
    }

    public ServicoCard(){}

    public ServicoCard(	Long id, String title, String thumbnail,String categoria,String subCategoria,String especialidade,
                       Integer estrelas,Boolean favorito,Double preco, String distancia ,Integer tempo){
        setId(id);
        setTitle(title);
        setThumbnail(thumbnail);
        setCategoria(categoria);
        setSubCategoria(subCategoria);
        setEspecialidade(especialidade);
        setFavorito(favorito);
        setEstrelas(estrelas);
        setTempo(tempo);
        setPreco(preco);
        setDistancia(distancia);
    }

    public static List<ServicoCard> getServicos(){
//        ServicoCard servicoCard1 = new ServicoCard("Serviço Judiciário" ,"http://www.alvestambelli.adv.br/public/img/default/servicos-de-advogados/home/direito-civel.jpg");
//        ServicoCard servicoCard2 = new ServicoCard("Serviço Bombeiro Hidáulico" ,"http://img.olx.com.br/images/41/418516038337171.jpg");
//        ServicoCard servicoCard3 = new ServicoCard("Serviço Eletricista" ,"http://www.hertzinstalacoeseletricas.com.br/images/instalacoes+eletricas+comercial+sp.jpg");
//        ServicoCard servicoCard4 = new ServicoCard("Manicure" ,"http://www.spetacollare.com.br/wp-content/uploads/2016/06/manicure.jpg");
//        ServicoCard servicoCard5 = new ServicoCard("Médica" ,"http://www.medicel.com.br/galeria/noticia/49/thumb_20141020113329168.jpg");
//        ServicoCard servicoCard6 = new ServicoCard("Entrega Água" ,"https://static9.depositphotos.com/1051435/1221/i/950/depositphotos_12217291-stock-photo-bottled-water-delivery-service.jpg");
        List<ServicoCard> lista = new ArrayList<ServicoCard>();
        lista.add(new ServicoCard(1L,"Eduardo Turma","http://www.vereadoreduardotuma.com.br/img/Foto-Site-FundoBranco-500x750.png","Direito","Advogado","Trabalhista",4,
                true,200D, "Guararapes - 0,1Km", 2));
        lista.add(new ServicoCard(2L,"Lucia Alves","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQm2E2fsLZZuoVqHKAuGt4wnedIjS8wxgHC5QobZpHAJLDpAJxqlQ","Estética","Manicure","",2,
                false,120D, "Aldeota - 0,2Km", 4));
        lista.add(new ServicoCard(3L,"Fernanda Reis","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTj64LmoyztSuU8vYyiVh4WimnAUM_FjCKR1ChzTYsUNdIf8TnZ","Saúde","Médica","Pediatra",5,
                true,90D, "Aldeota - 0,7Km", 15));
        lista.add(new ServicoCard(4L,"Ana Maria","https://st2.depositphotos.com/1465849/9046/i/950/depositphotos_90464920-stock-photo-beautiful-blonde-doctor-in-white.jpg","Saúde","Médica","Nutricionista",5,
                true,145D, "Varjota - 1Km", 16));
        lista.add(new ServicoCard(5L,"Cláudia Bertran","http://1.bp.blogspot.com/-7zZcgKxners/UgpFo_8xyuI/AAAAAAAAFvA/rSVWUtn0NuE/s1600/3.jpg","Saúde","Médica","Ginecologista",4,
                false,120D, "Centro - 2Km", 26));
        lista.add(new ServicoCard(6L,"Antônio José","https://s3-media1.fl.yelpcdn.com/bphoto/Ne2scwgWQNzAD0x0rrag3Q/258s.jpg","Geral","Elétrica","Casa",3,
                true,60D, "Edson Q. - 0,8Km", 15));
        lista.add(new ServicoCard(7L,"Mercadinho","https://static9.depositphotos.com/1051435/1221/i/950/depositphotos_12217291-stock-photo-bottled-water-delivery-service.jpg","Alimentação","Água","",2,
                false,10D, "Aldeota - 0,2Km", 4));

        return lista;

    }

}