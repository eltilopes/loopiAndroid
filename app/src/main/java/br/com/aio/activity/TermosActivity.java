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
        dialogTexto.setText(Html.fromHtml("<h4>Termos e Condições de uso</h4>\n" +
                "    <small>Os presentes termos e condições regem a utilização do site do Público e respectiva subscrição da assinatura digital Público numa das seguintes modalidades: mensal e anual, disponíveis através da loja online do Público, Comunicação Social, S.A..</small><br> <br>\n" +
                "    <li><strong><small> Conteúdo da Aplicação/ Propriedade Intelectual</small></strong></li><br>\n" +
                "    <small> * Entende-se por conteúdo do site toda a informação disponível para o assinante, nomeadamente texto, imagens, fotogalerias, vídeos, webdesign e software. Todo este conteúdo advém do jornal Público (papel e digital) e é protegido por direitos de autor ao abrigo das leis portuguesas e da União Europeia, não podendo ser utilizado fora das condições admitidas no uso deste site, sem consentimento do Público – Comunicação Social, S.A.. Os direitos de propriedade intelectual de todos os conteúdos do Público – Comunicação Social S.A., que não visem o fornecimento externo e consequentemente não sejam devidamente identificados, são pertença do Público, incluindo as informações, as ferramentas, o grafismo, as imagens, gráficos ou textos. O Público rejeita qualquer responsabilidade pela usurpação e uso indevido dos elementos acima citados, salvo as excepções permitidas por lei, nomeadamente o direito de citação, desde que claramente identificada a sua origem. O conteúdo deste site não poderá ser copiado, alterado ou distribuído, salvo com autorização expressa do Público – Comunicação Social, S.A..</small><br><br>\n" +
                "    <li><strong><small> Registo</small></strong></li><br>\n" +
                "    <small> ** Alguns conteúdos do site do Público são pagos. Para ter acesso aos mesmos, o utilizador deverá aceder às áreas de registo do Público – Comunicação Social, S.A., tendo para isso de efectuar o seu registo e subscrever uma das modalidades acima citadas. O utilizador concorda em assumir a responsabilidade dos seus dados de login, como o endereço de email e palavra-chave. Ao efectuar o registo e subscrever uma assinatura digital Público o utilizador concorda que:</small>\n" +
                "    <small>  -  O seu registo é pessoal e intransmissível, não podendo ser utilizado por qualquer outra pessoa no acesso aos conteúdos digitais Público;</small><br>\n" +
                "    <small>  -  Não fará nada que possa lesar o Público – Comunicação Social, S.A., nomeadamente aceder a uma área/conta não autorizada e respectiva informação;</small><br>\n" +
                "    <small>  -  Entrará de imediato em contacto com o Público – Comunicação Social S.A., caso se aperceba de qualquer uso não autorizado dos seus dados de login;</small><br>\n" +
                "    <small>  -  Não irá avaliar e testar a vulnerabilidade do sistema e quebrar a segurança instalada;</small><br>\n" +
                "    <small>  -  Não irá enviar emails não solicitados que incluam promoções ou publicidade a produtos ou outros serviços.</small><br>\n"));
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
        dialogTexto.setText(Html.fromHtml("<h4> Proteção à Privacidade e aos Direitos Autorais</h4> <br>\n" +
                "<small>As <a href=\"../../policies/privacy/\">Políticas de Privacidade</a> do Google explicam o modo como tratamos seus dados pessoais e protegemos sua privacidade quando você usa nossos Serviços. Ao utilizar nossos Serviços, você concorda que o Google poderá usar esses dados de acordo com nossas políticas de privacidade.</small><br><br>\n" +
                "<small>Nós respondemos às notificações de alegação de violação de direitos autorais e encerramos contas de infratores reincidentes de acordo com os procedimentos estabelecidos na Lei de Direitos Autorais Digital do Milênio dos Estados Unidos (U.S. Digital Millennium Copyright Act).</small><br><br>\n" +
                "<small>Fornecemos informações para ajudar os detentores de direitos autorais a gerenciarem sua propriedade intelectual on-line. Caso você entenda que alguém está violando seus direitos autorais e quiser nos notificar, você pode encontrar informações sobre o envio de notificações e sobre a política do Google para respondê-las <a href=\"https://support.google.com/bin/static.py?hl=br&amp;ts=1114905&amp;page=ts.cs\">em nossa Central de Ajuda</a>.</small><br><br>\n" +
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
