package br.com.aio.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.aio.R;
import br.com.aio.entity.UsuarioSession;
import br.com.aio.exception.EditTextValidation;
import br.com.aio.fonts.RobotoTextView;
import br.com.aio.model.Convite;
import br.com.aio.service.ConviteService;
import br.com.aio.service.ExecutorMetodoService;
import br.com.aio.utils.ConexaoUtils;
import br.com.aio.utils.CpfCnpjMaks;
import br.com.aio.utils.SessionUtils;
import br.com.aio.utils.TelefoneMaskUtil;
import br.com.aio.utils.ToastUtils;
import br.com.aio.utils.ViewUtils;
import br.com.aio.view.FloatLabeledEditText;
import br.com.aio.view.ProgressDialogAsyncTask;
import retrofit.RetrofitError;

import static br.com.aio.utils.BundleUtils.ACTIVITY_NAO_TENHO_CONVITE;
import static br.com.aio.utils.BundleUtils.PREFS_NAME;

/**
 * Created by elton on 17/07/2017.
 */

public class NaoTenhoConviteActivity extends Activity implements View.OnClickListener, ProgressDialogAsyncTask.IProgressActivity {

    private FloatLabeledEditText nomeCompleto;
    private FloatLabeledEditText cpfCnpj;
    private FloatLabeledEditText email;
    private FloatLabeledEditText senha;
    private FloatLabeledEditText convite;
    private FloatLabeledEditText telefone;
    private View validationTelefone;
    private View validationConvite;
    private View validationNome;
    private View validationCpf;
    private View validationEmail;
    private View validationSenha;
    private TextWatcher cpfCnpjMaks;
    private TextWatcher telefoneMask;
    private RelativeLayout layoutProgress;
    private LinearLayout linearConvite;
    private RobotoTextView tenhoConvite;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE); // Removing
        setContentView();
    }

    private void setContentView() {
        setContentView(R.layout.activity_nao_tenho_convite);
        mPrefs = getSharedPreferences(PREFS_NAME, 0);
        linearConvite = (LinearLayout) findViewById(R.id.linear_convite);
        linearConvite.setVisibility(View.GONE);
        layoutProgress = (RelativeLayout) findViewById(R.id.dialog_progress);
        cpfCnpj = (FloatLabeledEditText) findViewById(R.id.edit_text_cpf_cnpj);
        email = (FloatLabeledEditText) findViewById(R.id.edit_text_email);
        senha = (FloatLabeledEditText) findViewById(R.id.edit_text_senha);
        convite = (FloatLabeledEditText) findViewById(R.id.edit_text_convite);
        telefone = (FloatLabeledEditText) findViewById(R.id.edit_text_telefone);
        senha.setPassword(true);
        senha.getEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
        validationTelefone = (View) findViewById(R.id.validation_edit_text_telefone);
        validationConvite = (View) findViewById(R.id.validation_edit_text_convite);
        validationSenha = (View) findViewById(R.id.validation_edit_text_senha);
        validationNome = (View) findViewById(R.id.validation_edit_text_nome);
        validationEmail = (View) findViewById(R.id.validation_edit_text_email);
        validationCpf = (View) findViewById(R.id.validation_edit_text_cpf);
        nomeCompleto = (FloatLabeledEditText) findViewById(R.id.nome_completo);
        cpfCnpjMaks = CpfCnpjMaks.insert(getApplicationContext(),cpfCnpj.getEditText(),validationCpf, null);
        telefoneMask = TelefoneMaskUtil.insert(getApplicationContext(),telefone.getEditText(),validationTelefone );
        telefone.getEditText().addTextChangedListener(telefoneMask);
        telefone.getEditText().setInputType(InputType.TYPE_CLASS_PHONE);
        cpfCnpj.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        cpfCnpj.getEditText().addTextChangedListener(cpfCnpjMaks);
        email.getEditText().setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        email.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!emailValido(editable.toString())){
                    email.getEditText().setError(getString(R.string.validation_email_invalido));
                    validationEmail.setBackgroundColor(getResources().getColor(R.color.textColorInfoVermelho));
                }else{
                    validationEmail.setBackgroundColor(getResources().getColor(R.color.textColorInfoVerde));
                }
            }
        });
        nomeCompleto.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!nomeValido(editable.toString())){
                    validationNome.setBackgroundColor(getResources().getColor(R.color.textColorInfoVermelho));
                }else{
                    validationNome.setBackgroundColor(getResources().getColor(R.color.textColorInfoVerde));
                }
            }
        });
        senha.getEditText().addTextChangedListener(new TextWatcher() {
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
        convite.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(!conviteValido(editable.toString())){
                    validationConvite.setBackgroundColor(getResources().getColor(R.color.textColorInfoVermelho));
                }else{
                    validationConvite.setBackgroundColor(getResources().getColor(R.color.textColorInfoVerde));
                }
            }
        });
        TextView pedirConvite;
        pedirConvite = (TextView) findViewById(R.id.pedirConvite);

        tenhoConvite = (RobotoTextView) findViewById(R.id.tenho_convite) ;
        tenhoConvite.setOnClickListener(this);
        pedirConvite.setOnClickListener(this);
        nomeCompleto.setText("Elton Lopes");
        email.setText("eltilopes@gmail.com");
        cpfCnpj.setText("01234567890");
        senha.setText("elt#nA2@");
        telefone.setText("85989214075");
        convite.setText("A1B2C3D4");
    }

    public boolean emailValido(String email) {
        boolean valido = true;
        if (email == null || email.isEmpty()){
            valido = false;
            this.email.getEditText().setError(getString(R.string.validation_campo_obrigatorio));
        }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            valido = false;
            this.email.getEditText().setError(getString(R.string.validation_email_invalido));
        }
        return valido;
    }

    public boolean nomeValido(String nome) {
        boolean valido = true;
        if (nome == null){
            valido = false;
            nomeCompleto.getEditText().setError(getString(R.string.validation_campo_obrigatorio));
        }else if(nome.split(" ").length < 2){
            valido = false;
            nomeCompleto.getEditText().setError(getString(R.string.validation_nome_invalido));
        }
        return valido;
    }
    public boolean conviteValido(String convite) {
        boolean valido = true;
        if (convite == null || convite.length() != 8){
            valido = false;
            this.convite.getEditText().setError(getString(R.string.validation_convite_invalido));
        }
        return valido;
    }

    public boolean senhaValida(String senha) {
        boolean valido = true;
        if (senha == null){
            valido = false;
            this.senha.getEditText().setError(getString(R.string.validation_campo_obrigatorio));
        }else {
            EditTextValidation validacao = ViewUtils.validarSenha(senha,getApplicationContext(),4);
            if(validacao!=null){
                valido = false;
                this.senha.getEditText().setError(validacao.getDescricao());
                validationSenha.setBackgroundColor(validacao.getCor());
            }else{
                validationSenha.setBackgroundColor(getResources().getColor(R.color.textColorInfoVerde));
            }
        }
        return valido;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.pedirConvite:
                encaminharConvite();
                break;
            case R.id.tenho_convite:
                tenhoConvite.setVisibility(View.GONE);
                linearConvite.setVisibility(View.VISIBLE);
                break;

        }

    }

    private void encaminharConvite() {
        boolean nomeValido = nomeValido(nomeCompleto.getText().toString());
        boolean emailValido = emailValido(email.getText().toString());
        boolean senhaValida = senhaValida(senha.getText().toString());
        boolean cpfCnpjValido = CpfCnpjMaks.verificarCpfCnpj(getApplicationContext(),
                CpfCnpjMaks.unmask(cpfCnpj.getText().toString()), cpfCnpj.getEditText(), null, validationCpf);
        boolean telefoneValido = TelefoneMaskUtil.verificarTelefone(getApplicationContext(),
                TelefoneMaskUtil.unmask(telefone.getText().toString()), telefone.getEditText(), validationTelefone);
        if(telefoneValido && nomeValido && emailValido && cpfCnpjValido && senhaValida){
            ProgressDialogAsyncTask task = new ProgressDialogAsyncTask(this, layoutProgress, this);
            task.execute();
        }
    }


    public void executaProgressoDialog(){
        Convite conviteNovo = new Convite(
                nomeCompleto.getText().toString(),
                email.getText().toString(),
                CpfCnpjMaks.unmask(cpfCnpj.getText().toString()),
                TelefoneMaskUtil.unmask(telefone.getText().toString()));
        if(conviteValido(convite.getText().toString())){
            conviteNovo.setCodigoConvite(convite.getText().toString());
        }

        try{
            if(ConexaoUtils.isConexao(getApplicationContext())){

                try {
                    Convite conviteResponse = ExecutorMetodoService.invoke(new ConviteService(this), "solicitarConvite", conviteNovo);
                    SessionUtils.setActivityAnterior(mPrefs,ACTIVITY_NAO_TENHO_CONVITE);
                    SessionUtils.setUsuarioSession(mPrefs, new UsuarioSession(conviteResponse, senha.getText().toString()));
                    ToastUtils.show(this, getResources().getString(R.string.sucess_convite), ToastUtils.INFORMATION);
                } catch (RetrofitError error) {
                    ToastUtils.showErro(this, error.getResponse());
                } catch (RuntimeException erro) {
                    this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.show(NaoTenhoConviteActivity.this, getResources().getString(R.string.error_nao_encontrado), ToastUtils.ERROR);
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
    public void onPostExecute() {}
}
