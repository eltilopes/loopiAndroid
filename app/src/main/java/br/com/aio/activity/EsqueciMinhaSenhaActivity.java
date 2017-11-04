package br.com.aio.activity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.aio.R;
import br.com.aio.exception.EditTextValidation;
import br.com.aio.fonts.RobotoTextView;
import br.com.aio.service.ExecutorMetodoService;
import br.com.aio.service.LoginService;
import br.com.aio.utils.ConexaoUtils;
import br.com.aio.utils.CpfCnpjMaks;
import br.com.aio.utils.ToastUtils;
import br.com.aio.utils.ViewUtils;
import br.com.aio.view.ProgressDialogAsyncTask;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by elton on 17/07/2017.
 */

public class EsqueciMinhaSenhaActivity extends Activity implements View.OnClickListener, ProgressDialogAsyncTask.IProgressActivity  {


    private TextWatcher cpfCnpjMaks;
    private TextView validationCpfCnpj;
    private EditText cpfCnpj;
    private RobotoTextView enviar;
    private RobotoTextView infoEmail;
    private View validationCpf;
    private TextView validationTextSenha;
    private EditText novaSenha;
    private View validationSenha;
    private RelativeLayout layoutProgress;
    private ImageView visualizarSenha;
    private static final String VISIVEL = "VISIVEL";
    private static final String ESCONDIDA = "ESCONDIDA";
    private String infoTexto = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE); // Removing
        setContentView();
    }

    private void setContentView() {
        setContentView(R.layout.activity_esqueci_minha_senha);
        layoutProgress = (RelativeLayout) findViewById(R.id.dialog_progress);
        validationCpfCnpj = (TextView) findViewById(R.id.cpf_cnpj_validation);
        validationCpfCnpj.setVisibility(View.GONE);
        validationTextSenha = (TextView) findViewById(R.id.validation_senha);
        validationTextSenha.setVisibility(View.GONE);
        validationSenha = (View) findViewById(R.id.validation_edit_text_nova_senha);
        cpfCnpj = (EditText) findViewById(R.id.edit_text_cpf_cnpj);
        validationCpf = (View) findViewById(R.id.validation_edit_text_cpf);
        cpfCnpjMaks = CpfCnpjMaks.insert(getApplicationContext(),cpfCnpj,validationCpf, validationCpfCnpj);
        cpfCnpj.addTextChangedListener(cpfCnpjMaks);
        novaSenha = (EditText) findViewById(R.id.nova_senha);
        novaSenha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                senhaValida(editable.toString());
            }
        });
        Typeface sRobotoThin = Typeface.createFromAsset(getAssets(),
                "fonts/Roboto-Thin.ttf");
        ;
        visualizarSenha = (ImageView) findViewById(R.id.visualizar_senha);
        visualizarSenha.setTag(ESCONDIDA);
        visualizarSenha.setOnClickListener(this);
        cpfCnpj.setTypeface(sRobotoThin);
        novaSenha.setTypeface(sRobotoThin);
        enviar = (RobotoTextView) findViewById(R.id.enviar);
        enviar.setOnClickListener(this);
        infoEmail = (RobotoTextView) findViewById(R.id.info_email);
        infoEmail.setVisibility(View.GONE);
        cpfCnpj.setText("92871259372");
        novaSenha.setText("01#$AAaa");
    }

    public boolean senhaValida(String senha) {
        boolean valido = true;
        if (senha == null){
            valido = false;
            validationTextSenha.setText(getString(R.string.validation_campo_obrigatorio));
            validationTextSenha.setVisibility(View.VISIBLE);
            validationSenha.setBackgroundColor(getResources().getColor(R.color.textColorInfoVermelho));
        }else {
            EditTextValidation validacao = ViewUtils.validarSenha(senha,getApplicationContext(),4);
            if(validacao!=null){
                valido = false;
                validationTextSenha.setText(validacao.getDescricao());
                validationTextSenha.setVisibility(View.VISIBLE);
                validationSenha.setBackgroundColor(validacao.getCor());
            }else{
                validationSenha.setBackgroundColor(getResources().getColor(R.color.textColorInfoVerde));
                validationTextSenha.setVisibility(View.GONE);
            }
        }
        return valido;
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.enviar:
                enviar();
                break;
            case R.id.visualizar_senha:
                if(visualizarSenha.getTag().equals(ESCONDIDA)){
                    novaSenha.setInputType(InputType.TYPE_CLASS_TEXT);
                    novaSenha.setTransformationMethod(null);
                    visualizarSenha.setTag(VISIVEL);
                    visualizarSenha.setImageDrawable(getDrawable(R.drawable.ic_visibility_off));
                }else{
                    novaSenha.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    novaSenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    visualizarSenha.setTag(ESCONDIDA);
                    visualizarSenha.setImageDrawable(getDrawable(R.drawable.ic_visibility));
                }
                break;

        }

    }
    private void enviar() {
        boolean senhaValida = senhaValida(novaSenha.getText().toString());
        boolean cpfCnpjValido = CpfCnpjMaks.verificarCpfCnpj(getApplicationContext(),
                CpfCnpjMaks.unmask(cpfCnpj.getText().toString()), cpfCnpj, validationCpfCnpj, validationCpf);
        if(cpfCnpjValido && senhaValida ){
            ProgressDialogAsyncTask task = new ProgressDialogAsyncTask(this, layoutProgress, this);
            task.execute();
        }
    }



    public void executaProgressoDialog(){
        try{
            if(ConexaoUtils.isConexao(getApplicationContext())){

                try {
                    Response resp = ExecutorMetodoService.invoke(new LoginService(this), "solicitarAlteracaoSenha", novaSenha.getText().toString(), CpfCnpjMaks.unmask(cpfCnpj.getText().toString()));
                    String email =  new String(((TypedByteArray)resp.getBody()).getBytes());
                    infoTexto = "Autorização para alteração da senha foi enviada para: "+email+" Caso esse não seja um e-mail valido recadastre seu cpf.";
                } catch (RetrofitError error) {
                    ToastUtils.showErro(this, error.getResponse());
                } catch (RuntimeException erro) {
                    this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.show(EsqueciMinhaSenhaActivity.this, getResources().getString(R.string.error_nao_encontrado), ToastUtils.ERROR);
                        }
                    });
                }
            }else {
                ToastUtils.show(this, getResources().getString(R.string.error_conexao_internet), ToastUtils.ERROR);
            }
        }catch (RetrofitError error){
            ToastUtils.showErro(this, error.getResponse());
        }

    }


    @Override
    public boolean isAddedValidation() {
        return true;
    }

    @Override
    public void onPostExecute() {
        if(infoTexto!=null){
            infoEmail.setText(infoTexto);
            infoEmail.setVisibility(View.VISIBLE);
            infoTexto = null;
        }
    }
}
