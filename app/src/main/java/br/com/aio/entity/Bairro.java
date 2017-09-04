package br.com.aio.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elton on 01/09/2017.
 */

public class Bairro {

    private Long id;
    private String nome;
    private String secretaria;
    private Boolean atende;

    public Bairro(){    }

    public Bairro(Long id, String nome, String secretaria){
        setId(id);
        setNome(nome);
        setSecretaria(secretaria);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSecretaria() {
        return secretaria;
    }

    public void setSecretaria(String secretaria) {
        this.secretaria = secretaria;
    }

    public Boolean getAtende() {
        return atende;
    }

    public void setAtende(Boolean atende) {
        this.atende = atende;
    }

    public static List<Bairro> getBairros() {
        List<Bairro> bairros = new ArrayList<Bairro>();
        bairros.add(new Bairro(1L,"Centro","CENTRO"));
        bairros.add(new Bairro(2L,"Álvaro Weyne","SER I"));
        bairros.add(new Bairro(3L,"Barra do Ceará","SER I"));
        bairros.add(new Bairro(4L,"Carlito Pamplona","SER I"));
        bairros.add(new Bairro(5L,"Cristo Redentor","SER I"));
        bairros.add(new Bairro(6L,"Farias Brito","SER I"));
        bairros.add(new Bairro(7L,"Floresta","SER I"));
        bairros.add(new Bairro(8L,"Jacarecanga","SER I"));
        bairros.add(new Bairro(9L,"Jardim Guanabara","SER I"));
        bairros.add(new Bairro(10L,"Jardim Iracema","SER I"));
        bairros.add(new Bairro(11L,"Monte Castelo","SER I"));
        bairros.add(new Bairro(12L,"Moura Brasil","SER I"));
        bairros.add(new Bairro(13L,"Pirambú","SER I"));
        bairros.add(new Bairro(14L,"São Gerardo","SER I"));
        bairros.add(new Bairro(15L,"Vila Ellery","SER I"));
        bairros.add(new Bairro(16L,"Vila Velha","SER I"));
        bairros.add(new Bairro(17L,"Aldeota","SER II"));
        bairros.add(new Bairro(18L,"Cais do Porto","SER II"));
        bairros.add(new Bairro(19L,"Cidade 2000","SER II"));
        bairros.add(new Bairro(20L,"Cocó","SER II"));
        bairros.add(new Bairro(21L,"De Lourdes","SER II"));
        bairros.add(new Bairro(22L,"Dionísio Torres","SER II"));
        bairros.add(new Bairro(23L,"Engenheiro Luciano Cavalcante","SER II"));
        bairros.add(new Bairro(24L,"Guararapes","SER II"));
        bairros.add(new Bairro(25L,"Joaquim Távora","SER II"));
        bairros.add(new Bairro(26L,"Manuel Dias Branco","SER II"));
        bairros.add(new Bairro(27L,"Meireles","SER II"));
        bairros.add(new Bairro(28L,"Mucuripe","SER II"));
        bairros.add(new Bairro(29L,"Papicu","SER II"));
        bairros.add(new Bairro(30L,"Praia de Iracema","SER II"));
        bairros.add(new Bairro(31L,"Praia do Futuro I","SER II"));
        bairros.add(new Bairro(32L,"Praia do Futuro II","SER II"));
        bairros.add(new Bairro(33L,"Salinas","SER II"));
        bairros.add(new Bairro(34L,"São João do Tauape","SER II"));
        bairros.add(new Bairro(35L,"Varjota","SER II"));
        bairros.add(new Bairro(36L,"Vicente Pinzon","SER II"));
        bairros.add(new Bairro(37L,"Amadeu Furtado","SER III"));
        bairros.add(new Bairro(38L,"Antônio Bezerra","SER III"));
        bairros.add(new Bairro(39L,"Autran Nunes","SER III"));
        bairros.add(new Bairro(40L,"Bela Vista","SER III"));
        bairros.add(new Bairro(41L,"Bonsucesso","SER III"));
        bairros.add(new Bairro(42L,"Dom Lustosa","SER III"));
        bairros.add(new Bairro(43L,"Henrique Jorge","SER III"));
        bairros.add(new Bairro(44L,"João XXIII","SER III"));
        bairros.add(new Bairro(45L,"Jóquei Clube","SER III"));
        bairros.add(new Bairro(46L,"Olavo Oliveira","SER III"));
        bairros.add(new Bairro(47L,"Padre Andrade","SER III"));
        bairros.add(new Bairro(48L,"Parque Araxá","SER III"));
        bairros.add(new Bairro(49L,"Parquelândia","SER III"));
        bairros.add(new Bairro(50L,"Pici","SER III"));
        bairros.add(new Bairro(51L,"Presidente Kennedy","SER III"));
        bairros.add(new Bairro(52L,"Quintino Cunha","SER III"));
        bairros.add(new Bairro(53L,"Rodolfo Teófilo","SER III"));
        bairros.add(new Bairro(54L,"Aeroporto","SER III"));
        bairros.add(new Bairro(55L,"Benfica","SER IV"));
        bairros.add(new Bairro(56L,"Bom Futuro","SER IV"));
        bairros.add(new Bairro(57L,"Couto Fernandes","SER IV"));
        bairros.add(new Bairro(58L,"Damas","SER IV"));
        bairros.add(new Bairro(59L,"Demócrito Rocha","SER IV"));
        bairros.add(new Bairro(60L,"Dendê","SER IV"));
        bairros.add(new Bairro(61L,"Fátima","SER IV"));
        bairros.add(new Bairro(62L,"Itaoca","SER IV"));
        bairros.add(new Bairro(63L,"Itaperi","SER IV"));
        bairros.add(new Bairro(64L,"Jardim América","SER IV"));
        bairros.add(new Bairro(65L,"José Bonifácio","SER IV"));
        bairros.add(new Bairro(66L,"Montese","SER IV"));
        bairros.add(new Bairro(67L,"Panamericano","SER IV"));
        bairros.add(new Bairro(68L,"Parangaba","SER IV"));
        bairros.add(new Bairro(69L,"Parreão","SER IV"));
        bairros.add(new Bairro(70L,"Serrinha","SER IV"));
        bairros.add(new Bairro(71L,"Vila Peri","SER IV"));
        bairros.add(new Bairro(72L,"Vila União","SER IV"));
        bairros.add(new Bairro(73L,"Bom Jardim","SER V"));
        bairros.add(new Bairro(74L,"Canindezinho","SER V"));
        bairros.add(new Bairro(75L,"Conjunto Ceará I","SER V"));
        bairros.add(new Bairro(76L,"Conjunto Ceará II","SER V"));
        bairros.add(new Bairro(77L,"Conjunto Esperança","SER V"));
        bairros.add(new Bairro(78L,"Genibaú","SER V"));
        bairros.add(new Bairro(79L,"Granja Lisboa","SER V"));
        bairros.add(new Bairro(80L,"Granja Portugal","SER V"));
        bairros.add(new Bairro(81L,"Jardim Cearense","SER V"));
        bairros.add(new Bairro(82L,"Manoel Sátiro","SER V"));
        bairros.add(new Bairro(83L,"Maraponga","SER V"));
        bairros.add(new Bairro(84L,"Mondubim","SER V"));
        bairros.add(new Bairro(85L,"Planalto Ayrton Senna","SER V"));
        bairros.add(new Bairro(86L,"Prefeito José Walter","SER V"));
        bairros.add(new Bairro(87L,"Presidente Vargas","SER V"));
        bairros.add(new Bairro(88L,"São José","SER V"));
        bairros.add(new Bairro(89L,"Santa Rosa","SER V"));
        bairros.add(new Bairro(90L,"Siqueira","SER V"));
        bairros.add(new Bairro(91L,"Aerolândia","SER VI"));
        bairros.add(new Bairro(92L,"Alto da Balança","SER VI"));
        bairros.add(new Bairro(93L,"Ancuri","SER VI"));
        bairros.add(new Bairro(94L,"Barroso","SER VI"));
        bairros.add(new Bairro(95L,"Boa Vista","SER VI"));
        bairros.add(new Bairro(96L,"Cajazeiras","SER VI"));
        bairros.add(new Bairro(97L,"Cambeba","SER VI"));
        bairros.add(new Bairro(98L,"Cidade dos Funcionários","SER VI"));
        bairros.add(new Bairro(99L,"Coaçu","SER VI"));
        bairros.add(new Bairro(100L,"Curió","SER VI"));
        bairros.add(new Bairro(101L,"Conjunto Palmeiras","SER VI"));
        bairros.add(new Bairro(102L,"Dias Macedo","SER VI"));
        bairros.add(new Bairro(103L,"Edson Queiroz","SER VI"));
        bairros.add(new Bairro(104L,"Guajeru","SER VI"));
        bairros.add(new Bairro(105L,"Jangurussu","SER VI"));
        bairros.add(new Bairro(106L,"José de Alencar","SER VI"));
        bairros.add(new Bairro(107L,"Jardim das Oliveiras","SER VI"));
        bairros.add(new Bairro(108L,"Lagoa Redonda","SER VI"));
        bairros.add(new Bairro(109L,"Messejana","SER VI"));
        bairros.add(new Bairro(110L,"Parque Dois Irmãos","SER VI"));
        bairros.add(new Bairro(111L,"Parque Iracema","SER VI"));
        bairros.add(new Bairro(112L,"Parque Manibura","SER VI"));
        bairros.add(new Bairro(113L,"Passaré","SER VI"));
        bairros.add(new Bairro(114L,"Paupina","SER VI"));
        bairros.add(new Bairro(115L,"Pedras","SER VI"));
        bairros.add(new Bairro(116L,"Sabiaguaba","SER VI"));
        bairros.add(new Bairro(117L,"São Bento","SER VI"));
        bairros.add(new Bairro(118L,"Santa Maria","SER VI"));
        bairros.add(new Bairro(119L,"Sapiranga / Coité","SER VI"));
        return bairros;

    }

}
