package br.com.aio.entity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by elton on 01/09/2017.
 */

public class Bairro {

    private Long id;
    private String nome;
    private String secretaria;
    private String cor;
    private Boolean atende;

    public Bairro(){    }

    public Bairro(Long id, String nome, String secretaria, String cor){
        setId(id);
        setNome(nome);
        setSecretaria(secretaria);
        setCor(cor);
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

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public static List<Bairro> getBairros() {
        List<Bairro> bairros = new ArrayList<Bairro>();
        bairros.add(new Bairro(1L,"Centro","CENTRO","bairro_centro"));
        bairros.add(new Bairro(2L,"Álvaro Weyne","SER I","bairro_alvaro_weyne"));
        bairros.add(new Bairro(3L,"Barra do Ceará","SER I","bairro_barra_ceara"));
        bairros.add(new Bairro(4L,"Carlito Pamplona","SER I","bairro_carlito_pamplona"));
        bairros.add(new Bairro(5L,"Cristo Redentor","SER I","bairro_cristo_redentor"));
        bairros.add(new Bairro(6L,"Farias Brito","SER I","bairro_farias_brito"));
        bairros.add(new Bairro(7L,"Floresta","SER I","bairro_floresta"));
        bairros.add(new Bairro(8L,"Jacarecanga","SER I","bairro_jacarecanga"));
        bairros.add(new Bairro(9L,"Jardim Guanabara","SER I","bairro_jardim_guanabara"));
        bairros.add(new Bairro(10L,"Jardim Iracema","SER I","bairro_jardim_iracema"));
        bairros.add(new Bairro(11L,"Monte Castelo","SER I","bairro_monte_castelo"));
        bairros.add(new Bairro(12L,"Moura Brasil","SER I","bairro_moura_brasil"));
        bairros.add(new Bairro(13L,"Pirambú","SER I","bairro_pirambu"));
        bairros.add(new Bairro(14L,"São Gerardo","SER I","bairro_sao_gerardo"));
        bairros.add(new Bairro(15L,"Vila Ellery","SER I","bairro_vila_ellery"));
        bairros.add(new Bairro(16L,"Vila Velha","SER I","bairro_vila_velha"));
        bairros.add(new Bairro(17L,"Aldeota","SER II","bairro_aldeota"));
        bairros.add(new Bairro(18L,"Cais do Porto","SER II","bairro_cais_porto"));
        bairros.add(new Bairro(19L,"Cidade 2000","SER II","bairro_cidade_2000"));
        bairros.add(new Bairro(20L,"Cocó","SER II","bairro_coco"));
        bairros.add(new Bairro(21L,"De Lourdes","SER II","bairro_de_lourdes"));
        bairros.add(new Bairro(22L,"Dionísio Torres","SER II","bairro_dionisio_torres"));
        bairros.add(new Bairro(23L,"Engenheiro Luciano Cavalcante","SER II","bairro_engenheiro_luciano_cavalcante"));
        bairros.add(new Bairro(24L,"Guararapes","SER II","bairro_guararapes"));
        bairros.add(new Bairro(25L,"Joaquim Távora","SER II","bairro_joaquim_tavora"));
        bairros.add(new Bairro(26L,"Manuel Dias Branco","SER II","bairro_manoel_dias_branco"));
        bairros.add(new Bairro(27L,"Meireles","SER II","bairro_meireles"));
        bairros.add(new Bairro(28L,"Mucuripe","SER II","bairro_mucuripe"));
        bairros.add(new Bairro(29L,"Papicu","SER II","bairro_papicu"));
        bairros.add(new Bairro(30L,"Praia de Iracema","SER II","bairro_praia_iracema"));
        bairros.add(new Bairro(31L,"Praia do Futuro I","SER II","bairro_praia_futuro_1"));
        bairros.add(new Bairro(32L,"Praia do Futuro II","SER II","bairro_praia_futuro_2"));
        bairros.add(new Bairro(33L,"Salinas","SER II","bairro_salinas"));
        bairros.add(new Bairro(34L,"São João do Tauape","SER II","bairro_sao_joao_tauape"));
        bairros.add(new Bairro(35L,"Varjota","SER II","bairro_varjota"));
        bairros.add(new Bairro(36L,"Vicente Pinzon","SER II","bairro_vicente_pinzon"));
        bairros.add(new Bairro(37L,"Amadeu Furtado","SER III","bairro_amadeu_furtado"));
        bairros.add(new Bairro(38L,"Antônio Bezerra","SER III","bairro_antonio_bezerra"));
        bairros.add(new Bairro(39L,"Autran Nunes","SER III","bairro_autran_nunes"));
        bairros.add(new Bairro(40L,"Bela Vista","SER III","bairro_bela_vista"));
        bairros.add(new Bairro(41L,"Bonsucesso","SER III","bairro_bonsucesso"));
        bairros.add(new Bairro(42L,"Dom Lustosa","SER III","bairro_dom_lustosa"));
        bairros.add(new Bairro(43L,"Henrique Jorge","SER III","bairro_henrique_jorge"));
        bairros.add(new Bairro(44L,"João XXIII","SER III","bairro_joao_XXIII"));
        bairros.add(new Bairro(45L,"Jóquei Clube","SER III","bairro_joquei_clube"));
        bairros.add(new Bairro(46L,"Olavo Oliveira","SER III","bairro_olavo_oliveira"));
        bairros.add(new Bairro(47L,"Padre Andrade","SER III","bairro_padre_andrade"));
        bairros.add(new Bairro(48L,"Parque Araxá","SER III","bairro_parque_araxa"));
        bairros.add(new Bairro(49L,"Parquelândia","SER III","bairro_parquelandia"));
        bairros.add(new Bairro(50L,"Pici","SER III","bairro_pici"));
        bairros.add(new Bairro(51L,"Presidente Kennedy","SER III","bairro_presidente_kennedy"));
        bairros.add(new Bairro(52L,"Quintino Cunha","SER III","bairro_quintino_cunha"));
        bairros.add(new Bairro(53L,"Rodolfo Teófilo","SER III","bairro_rodolfo_teofilo"));
        bairros.add(new Bairro(54L,"Aeroporto","SER III","bairro_aeroporto"));
        bairros.add(new Bairro(55L,"Benfica","SER IV","bairro_benfica"));
        bairros.add(new Bairro(56L,"Bom Futuro","SER IV","bairro_bom_futuro"));
        bairros.add(new Bairro(57L,"Couto Fernandes","SER IV","bairro_couto_fernandes"));
        bairros.add(new Bairro(58L,"Damas","SER IV","bairro_damas"));
        bairros.add(new Bairro(59L,"Demócrito Rocha","SER IV","bairro_democrito_rocha"));
        bairros.add(new Bairro(60L,"Dendê","SER IV","bairro_dende"));
        bairros.add(new Bairro(61L,"Fátima","SER IV","bairro_fatima"));
        bairros.add(new Bairro(62L,"Itaoca","SER IV","bairro_itaoca"));
        bairros.add(new Bairro(63L,"Itaperi","SER IV","bairro_itaperi"));
        bairros.add(new Bairro(64L,"Jardim América","SER IV","bairro_jardim_america"));
        bairros.add(new Bairro(65L,"José Bonifácio","SER IV","bairro_jose_bonifacio"));
        bairros.add(new Bairro(66L,"Montese","SER IV","bairro_montese"));
        bairros.add(new Bairro(67L,"Panamericano","SER IV","bairro_panamericano"));
        bairros.add(new Bairro(68L,"Parangaba","SER IV","bairro_parangaba"));
        bairros.add(new Bairro(69L,"Parreão","SER IV","bairro_parreao"));
        bairros.add(new Bairro(70L,"Serrinha","SER IV","bairro_serrinha"));
        bairros.add(new Bairro(71L,"Vila Peri","SER IV","bairro_vila_peri"));
        bairros.add(new Bairro(72L,"Vila União","SER IV","bairro_vila_uniao"));
        bairros.add(new Bairro(73L,"Bom Jardim","SER V","bairro_bom_jardim"));
        bairros.add(new Bairro(74L,"Canindezinho","SER V","bairro_canindezinho"));
        bairros.add(new Bairro(75L,"Conjunto Ceará I","SER V","bairro_conjunto_ceara_1"));
        bairros.add(new Bairro(76L,"Conjunto Ceará II","SER V","bairro_conjunto_ceara_2"));
        bairros.add(new Bairro(77L,"Conjunto Esperança","SER V","bairro_conjunto_esperanca"));
        bairros.add(new Bairro(78L,"Genibaú","SER V","bairro_genibau"));
        bairros.add(new Bairro(79L,"Granja Lisboa","SER V","bairro_granja_lisboa"));
        bairros.add(new Bairro(80L,"Granja Portugal","SER V","bairro_granja_portugal"));
        bairros.add(new Bairro(81L,"Jardim Cearense","SER V","bairro_jardim_cearense"));
        bairros.add(new Bairro(82L,"Manoel Sátiro","SER V","bairro_manoel_satiro"));
        bairros.add(new Bairro(83L,"Maraponga","SER V","bairro_maraponga"));
        bairros.add(new Bairro(84L,"Mondubim","SER V","bairro_mondubim"));
        bairros.add(new Bairro(85L,"Planalto Ayrton Senna","SER V","bairro_planalto_ayrton_senna"));
        bairros.add(new Bairro(86L,"Prefeito José Walter","SER V","bairro_prefeito_jose_walter"));
        bairros.add(new Bairro(87L,"Presidente Vargas","SER V","bairro_presidente_vargas"));
        bairros.add(new Bairro(88L,"São José","SER V","bairro_sao_jose"));
        bairros.add(new Bairro(89L,"Santa Rosa","SER V","bairro_santa_rosa"));
        bairros.add(new Bairro(90L,"Siqueira","SER V","bairro_siqueira"));
        bairros.add(new Bairro(91L,"Aerolândia","SER VI","bairro_aerolandia"));
        bairros.add(new Bairro(92L,"Alto da Balança","SER VI","bairro_alto_balanca"));
        bairros.add(new Bairro(93L,"Ancuri","SER VI","bairro_ancuri"));
        bairros.add(new Bairro(94L,"Barroso","SER VI","bairro_barroso"));
        bairros.add(new Bairro(95L,"Boa Vista","SER VI","bairro_boa_vista"));
        bairros.add(new Bairro(96L,"Cajazeiras","SER VI","bairro_cajazeiras"));
        bairros.add(new Bairro(97L,"Cambeba","SER VI","bairro_cambeba"));
        bairros.add(new Bairro(98L,"Cidade dos Funcionários","SER VI","bairro_cidade_funcionarios"));
        bairros.add(new Bairro(99L,"Coaçu","SER VI","bairro_coacu"));
        bairros.add(new Bairro(100L,"Curió","SER VI","bairro_curio"));
        bairros.add(new Bairro(101L,"Conjunto Palmeiras","SER VI","bairro_conjunto_palmeiras"));
        bairros.add(new Bairro(102L,"Dias Macedo","SER VI","bairro_dias_macedo"));
        bairros.add(new Bairro(103L,"Edson Queiroz","SER VI","bairro_edson_queiroz"));
        bairros.add(new Bairro(104L,"Guajeru","SER VI","bairro_guajeru"));
        bairros.add(new Bairro(105L,"Jangurussu","SER VI","bairro_jangurussu"));
        bairros.add(new Bairro(106L,"José de Alencar","SER VI","bairro_jose_alencar"));
        bairros.add(new Bairro(107L,"Jardim das Oliveiras","SER VI","bairro_jardim_oliveiras"));
        bairros.add(new Bairro(108L,"Lagoa Redonda","SER VI","bairro_lagoa_redonda"));
        bairros.add(new Bairro(109L,"Messejana","SER VI","bairro_messejana"));
        bairros.add(new Bairro(110L,"Parque Dois Irmãos","SER VI","bairro_parque_irmaos"));
        bairros.add(new Bairro(111L,"Parque Iracema","SER VI","bairro_parque_iracema"));
        bairros.add(new Bairro(112L,"Parque Manibura","SER VI","bairro_parque_manibura"));
        bairros.add(new Bairro(113L,"Passaré","SER VI","bairro_passare"));
        bairros.add(new Bairro(114L,"Paupina","SER VI","bairro_paupina"));
        bairros.add(new Bairro(115L,"Pedras","SER VI","bairro_pedras"));
        bairros.add(new Bairro(116L,"Sabiaguaba","SER VI","bairro_sabiaguaba"));
        bairros.add(new Bairro(117L,"São Bento","SER VI","bairro_sao_bento"));
        bairros.add(new Bairro(118L,"Santa Maria","SER VI","bairro_santa_maria"));
        bairros.add(new Bairro(119L,"Sapiranga / Coité","SER VI","bairro_sapiranga_coite"));
        return bairros;

    }

    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    public static BigInteger stringToBigInteger(String text) {
        BigInteger bigInt = new BigInteger(text.getBytes());
        return bigInt;
    }

    public static Bairro getBairroPorCor( String color) {
        Bairro bairroSelecionado = null;
        for (Bairro bairro : getBairros()){
            if(bairro.getCor().equals(color)){
                bairroSelecionado = bairro;
            }
        }
        return bairroSelecionado;
    }
}
