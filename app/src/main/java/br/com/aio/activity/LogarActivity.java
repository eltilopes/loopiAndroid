package br.com.aio.activity;

/**
 * Created by elton on 16/07/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

import br.com.aio.R;
import br.com.aio.exception.AcessoNegadoException;
import br.com.aio.fonts.RobotoTextView;
import br.com.aio.service.ExecutorMetodoService;
import br.com.aio.service.GcmService;
import br.com.aio.service.LoginService;
import br.com.aio.utils.BundleUtils;
import br.com.aio.utils.ConexaoUtils;
import br.com.aio.utils.CpfCnpjMaks;
import br.com.aio.utils.GcmUtils;
import br.com.aio.utils.SessionUtils;
import br.com.aio.utils.ToastUtils;
import br.com.aio.utils.UsuarioSharedUtils;
import br.com.aio.view.ProgressDialogAsyncTask;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static br.com.aio.utils.BundleUtils.PREFS_NAME;

public class LogarActivity extends Activity implements OnClickListener, ProgressDialogAsyncTask.IProgressActivity  {

        private TextWatcher cpfCnpjMaks;
        private TextView validationCpfCnpj;
        private TextView validationSenha;
        private EditText cpfCnpj;
        private EditText senha;
        private RelativeLayout layoutProgress;
        private RobotoTextView esqueciMinhaSenha;
        private SharedPreferences mPrefs;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            mPrefs = getSharedPreferences(PREFS_NAME, 0);
            verificarFireBase();
            super.onCreate(savedInstanceState);
            getWindow().requestFeature(Window.FEATURE_NO_TITLE); // Removing
            setContentView(R.layout.activity_logar);
            layoutProgress = (RelativeLayout) findViewById(R.id.dialog_progress);
            validationCpfCnpj = (TextView) findViewById(R.id.cpf_cnpj_validation);
            validationCpfCnpj.setVisibility(View.GONE);
            validationSenha = (TextView) findViewById(R.id.senha_validation);
            validationSenha.setVisibility(View.GONE);
            cpfCnpj = (EditText) findViewById(R.id.edit_text_cpf_cnpj);
            senha = (EditText) findViewById(R.id.senha);
            cpfCnpjMaks = CpfCnpjMaks.insert(getApplicationContext(),cpfCnpj,validationCpfCnpj);
            cpfCnpj.addTextChangedListener(cpfCnpjMaks);
            senha.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    senhaDigitada(editable.toString());
                }
            });

            Typeface sRobotoThin = Typeface.createFromAsset(getAssets(),
                    "fonts/Roboto-Thin.ttf");
            ;
            cpfCnpj.setTypeface(sRobotoThin);
            senha.setTypeface(sRobotoThin);
            esqueciMinhaSenha = (RobotoTextView) findViewById(R.id.esqueci_minha_senha);
            TextView login, criarConta;
            login = (TextView) findViewById(R.id.login);
            criarConta = (TextView) findViewById(R.id.criar_conta);
            esqueciMinhaSenha.setOnClickListener(this);
            login.setOnClickListener(this);
            criarConta.setOnClickListener(this);
            senha.setText("elton");
            cpfCnpj.setText("92871259372");
        }

    private boolean senhaDigitada(String senha) {
        boolean digitouSenha = !senha.isEmpty();
        if (digitouSenha) {
            validationSenha.setVisibility(View.GONE);
        } else {
            validationSenha.setVisibility(View.VISIBLE);
            validationSenha.setText("Campo obrigat??rio");
        }
        return digitouSenha;
    }

    @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.login:
                    entrar();
                    break;
                case R.id.esqueci_minha_senha:
                    startActivity(new Intent(this, EsqueciMinhaSenhaActivity.class));
                    break;
                case R.id.criar_conta:
                    startActivity(new Intent(this, NaoTenhoConviteActivity.class));
                    break;
            }

        }

    private void entrar() {
        boolean cpfCnpjValido = CpfCnpjMaks.verificarCpfCnpj(getApplicationContext(),CpfCnpjMaks.unmask(cpfCnpj.getText().toString()), cpfCnpj, validationCpfCnpj, null);
        boolean digitouSenha = senhaDigitada(senha.getText().toString());
        if(cpfCnpjValido && digitouSenha){
            ProgressDialogAsyncTask task = new ProgressDialogAsyncTask(this, layoutProgress, this);
            if (!ConexaoUtils.isConexao(getApplicationContext())) {
                ToastUtils.show(this, getResources().getString(R.string.error_conexao_internet), ToastUtils.ERROR);
            } else  {
                task.execute();
            }
        }
    }

    @Override
    public void executaProgressoDialog() {
        try {
            ExecutorMetodoService.invoke(new LoginService(this), "login", CpfCnpjMaks.unmask(cpfCnpj.getText().toString()), senha.getText().toString());
            registrarIdGcm();
        } catch (RetrofitError error) {
            final Response resp = error.getResponse();
            ToastUtils.showErro(this, resp);
        } catch (AcessoNegadoException erro) {
            ToastUtils.show(LogarActivity.this, getResources().getString(R.string.error_acesso_negado), ToastUtils.WARNING);
        } catch (RuntimeException erro) {
            ToastUtils.show(LogarActivity.this, getResources().getString(R.string.error_dados_invalidos), ToastUtils.WARNING);
        }
        redirect();
    }

    public void redirect(){
        Intent intent = new Intent(this, ListagemActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean isAddedValidation() {
        return true;
    }

    @Override
    public void onPostExecute() {}

    private void registrarIdGcm() {
        try {
            if (GcmUtils.checkePlayService(this)) {
                String regId = UsuarioSharedUtils.getElementoSalvo(getApplicationContext(), UsuarioSharedUtils.Preferences.PREFERENCES_REG_ID);
                if ("".equals(regId) || regId.trim().length() == 0) {
                    regId = GcmUtils.registerId(getApplicationContext());
                }
                if (!"".equals(regId)) {
                    ExecutorMetodoService.invoke(new GcmService(this), "registrarApi", regId);
                }
            }
        }catch (Exception e){
            Log.e("Erro", "Ocorreu um erro ao gerar regId");
        }
    }

    private void verificarFireBase() {
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                            SessionUtils.setDeepLinkFireBase(mPrefs, deepLink.toString());
                            if(deepLink.toString().toUpperCase().contains(BundleUtils.DYNAMIC_LINK_MEU_PERFIL.toUpperCase())){
                                abrirActivity(new Intent(LogarActivity.this, MeuPerfilActivity.class));
                            }
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        ToastUtils.show(LogarActivity.this,"getDynamicLink:onFailure",ToastUtils.ERROR);

                    }
                });

    }

    private void abrirActivity(Intent i){
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
        startActivity(i);
    }
}
