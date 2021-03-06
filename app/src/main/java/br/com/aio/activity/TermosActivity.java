package br.com.aio.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.aio.R;
import br.com.aio.utils.ToastUtils;

/**
 * Created by elton on 17/07/2017.
 */

public class TermosActivity extends Activity implements View.OnClickListener {

    private LinearLayout verTermosCondicoes ;
    private Dialog dialogTermosCondicoes ;
    private LinearLayout verTermosPrivacidade;
    private Dialog dialogTermosPrivacidade;
    private CheckBox checkboxConcordar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE); // Removing
        setContentView();
    }

    private void setContentView() {
        setContentView(R.layout.activity_termos);
        TextView enviar;
        enviar = (TextView) findViewById(R.id.enviar);
        verTermosPrivacidade = (LinearLayout) findViewById(R.id.termos_privacidade);
        verTermosPrivacidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirDialogTermosPrivacidade();
            }
        });
        verTermosCondicoes= (LinearLayout) findViewById(R.id.termos_condicoes);
        verTermosCondicoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirDialogverTermosCondicoes();
            }
        });
        enviar.setOnClickListener(this);
        checkboxConcordar = (CheckBox) findViewById(R.id.checkbox_concordar);
        checkboxConcordar.setOnClickListener(this);

    }

    private void abrirDialogverTermosCondicoes() {
        dialogTermosCondicoes = new Dialog(this);
        dialogTermosCondicoes.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogTermosCondicoes.setContentView(R.layout.dialog_taxas_termos);
        dialogTermosCondicoes.show();
        TextView alertTitle=(TextView) dialogTermosCondicoes.getWindow().getDecorView().findViewById(R.id.dialog_title);
        alertTitle.setText(getString(R.string.termos_condicoes));
        TextView dialogTexto =(TextView) dialogTermosCondicoes.getWindow().getDecorView().findViewById(R.id.text_dialog);
        dialogTexto.setText(Html.fromHtml("<h4>Termos e Condi????es de uso</h4>\n" +
                "    <small>Os presentes termos e condi????es regem a utiliza????o do site do P??blico e respectiva subscri????o da assinatura digital P??blico numa das seguintes modalidades: mensal e anual, dispon??veis atrav??s da loja online do P??blico, Comunica????o Social, S.A..</small><br> <br>\n" +
                "    <li><strong><small> Conte??do da Aplica????o/ Propriedade Intelectual</small></strong></li><br>\n" +
                "    <small> * Entende-se por conte??do do site toda a informa????o dispon??vel para o assinante, nomeadamente texto, imagens, fotogalerias, v??deos, webdesign e software. Todo este conte??do adv??m do jornal P??blico (papel e digital) e ?? protegido por direitos de autor ao abrigo das leis portuguesas e da Uni??o Europeia, n??o podendo ser utilizado fora das condi????es admitidas no uso deste site, sem consentimento do P??blico ??? Comunica????o Social, S.A.. Os direitos de propriedade intelectual de todos os conte??dos do P??blico ??? Comunica????o Social S.A., que n??o visem o fornecimento externo e consequentemente n??o sejam devidamente identificados, s??o perten??a do P??blico, incluindo as informa????es, as ferramentas, o grafismo, as imagens, gr??ficos ou textos. O P??blico rejeita qualquer responsabilidade pela usurpa????o e uso indevido dos elementos acima citados, salvo as excep????es permitidas por lei, nomeadamente o direito de cita????o, desde que claramente identificada a sua origem. O conte??do deste site n??o poder?? ser copiado, alterado ou distribu??do, salvo com autoriza????o expressa do P??blico ??? Comunica????o Social, S.A..</small><br><br>\n" +
                "    <li><strong><small> Registo</small></strong></li><br>\n" +
                "    <small> ** Alguns conte??dos do site do P??blico s??o pagos. Para ter acesso aos mesmos, o utilizador dever?? aceder ??s ??reas de registo do P??blico ??? Comunica????o Social, S.A., tendo para isso de efectuar o seu registo e subscrever uma das modalidades acima citadas. O utilizador concorda em assumir a responsabilidade dos seus dados de login, como o endere??o de email e palavra-chave. Ao efectuar o registo e subscrever uma assinatura digital P??blico o utilizador concorda que:</small>\n" +
                "    <small>  -  O seu registo ?? pessoal e intransmiss??vel, n??o podendo ser utilizado por qualquer outra pessoa no acesso aos conte??dos digitais P??blico;</small><br>\n" +
                "    <small>  -  N??o far?? nada que possa lesar o P??blico ??? Comunica????o Social, S.A., nomeadamente aceder a uma ??rea/conta n??o autorizada e respectiva informa????o;</small><br>\n" +
                "    <small>  -  Entrar?? de imediato em contacto com o P??blico ??? Comunica????o Social S.A., caso se aperceba de qualquer uso n??o autorizado dos seus dados de login;</small><br>\n" +
                "    <small>  -  N??o ir?? avaliar e testar a vulnerabilidade do sistema e quebrar a seguran??a instalada;</small><br>\n" +
                "    <small>  -  N??o ir?? enviar emails n??o solicitados que incluam promo????es ou publicidade a produtos ou outros servi??os.</small><br>\n"));
        Button aplicar = (Button) dialogTermosCondicoes.findViewById(R.id.btn_dialog);
        aplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTermosCondicoes.dismiss();
            }
        });
    }

    private void abrirDialogTermosPrivacidade() {
        dialogTermosPrivacidade = new Dialog(this);
        dialogTermosPrivacidade.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogTermosPrivacidade.setContentView(R.layout.dialog_taxas_termos);
        dialogTermosPrivacidade.show();
        TextView alertTitle=(TextView) dialogTermosPrivacidade.getWindow().getDecorView().findViewById(R.id.dialog_title);
        alertTitle.setText(getString(R.string.termos_privacidade));
        TextView dialogTexto =(TextView) dialogTermosPrivacidade.getWindow().getDecorView().findViewById(R.id.text_dialog);
        dialogTexto.setText(Html.fromHtml("<h4> Prote????o ?? Privacidade e aos Direitos Autorais</h4> <br>\n" +
                "<small>As <a href=\"../../policies/privacy/\">Pol??ticas de Privacidade</a> do Google explicam o modo como tratamos seus dados pessoais e protegemos sua privacidade quando voc?? usa nossos Servi??os. Ao utilizar nossos Servi??os, voc?? concorda que o Google poder?? usar esses dados de acordo com nossas pol??ticas de privacidade.</small><br><br>\n" +
                "<small>N??s respondemos ??s notifica????es de alega????o de viola????o de direitos autorais e encerramos contas de infratores reincidentes de acordo com os procedimentos estabelecidos na Lei de Direitos Autorais Digital do Mil??nio dos Estados Unidos (U.S. Digital Millennium Copyright Act).</small><br><br>\n" +
                "<small>Fornecemos informa????es para ajudar os detentores de direitos autorais a gerenciarem sua propriedade intelectual on-line. Caso voc?? entenda que algu??m est?? violando seus direitos autorais e quiser nos notificar, voc?? pode encontrar informa????es sobre o envio de notifica????es e sobre a pol??tica do Google para respond??-las <a href=\"https://support.google.com/bin/static.py?hl=br&amp;ts=1114905&amp;page=ts.cs\">em nossa Central de Ajuda</a>.</small><br><br>\n" +
                "\n"));
        Button aplicar = (Button) dialogTermosPrivacidade.findViewById(R.id.btn_dialog);
        aplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTermosPrivacidade.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.enviar:
                Intent newActivity0 = new Intent(TermosActivity.this, ListagemActivity.class);
                startActivity(newActivity0);
                break;
            case R.id.checkbox_concordar:
                ToastUtils.show(TermosActivity.this, "Concordou!", ToastUtils.INFORMATION);
                break;
        }

    }
}
