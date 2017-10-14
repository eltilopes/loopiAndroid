package br.com.aio.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import br.com.aio.R;
import br.com.aio.model.Convite;
import br.com.aio.service.ConviteService;
import br.com.aio.service.ExecutorMetodoService;
import br.com.aio.utils.ConexaoUtils;
import br.com.aio.utils.CpfCnpjMaks;
import br.com.aio.utils.ToastUtils;
import br.com.aio.view.FloatLabeledEditText;
import br.com.aio.view.ProgressDialogAsyncTask;
import retrofit.RetrofitError;

/**
 * Created by elton on 17/07/2017.
 */

public class NaoTenhoConviteActivity extends Activity implements View.OnClickListener, ProgressDialogAsyncTask.IProgressActivity {

    private FloatLabeledEditText nomeCompleto;
    private FloatLabeledEditText cpfCnpj;
    private FloatLabeledEditText email;
    private TextWatcher cpfCnpjMaks;
    private RelativeLayout layoutProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE); // Removing
        setContentView();
    }

    private void setContentView() {
        setContentView(R.layout.activity_nao_tenho_convite);
        layoutProgress = (RelativeLayout) findViewById(R.id.dialog_progress);
        cpfCnpj = (FloatLabeledEditText) findViewById(R.id.cpf_cnpj);
        email = (FloatLabeledEditText) findViewById(R.id.edit_text_email);
        nomeCompleto = (FloatLabeledEditText) findViewById(R.id.nome_completo);
        cpfCnpjMaks = CpfCnpjMaks.insert(getApplicationContext(),cpfCnpj.getEditText(), null);
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

                }
            }
        });
        TextView pedirConvite;
        pedirConvite = (TextView) findViewById(R.id.pedirConvite);

        pedirConvite.setOnClickListener(this);
        nomeCompleto.setText("Elton Lopes");
        email.setText("eltilopes@gmail.com");
        cpfCnpj.setText("01234567890");
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

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.pedirConvite:
                encaminharConvite();
                break;
        }

    }

    private void encaminharConvite() {
        boolean nomeValido = nomeValido(nomeCompleto.getText().toString());
        boolean emailValido = emailValido(email.getText().toString());
        boolean cpfCnpjValido = CpfCnpjMaks.verificarCpfCnpj(getApplicationContext(),
                CpfCnpjMaks.unmask(cpfCnpj.getText().toString()), cpfCnpj.getEditText(), null);
        if(nomeValido && emailValido && cpfCnpjValido){
            ProgressDialogAsyncTask task = new ProgressDialogAsyncTask(this, layoutProgress, this);
            task.execute();
            Toast.makeText(this, "Pedir Convite", Toast.LENGTH_SHORT).show();
        }
    }

    public void executaProgressoDialog(){
        Convite conviteNovo = new Convite(
                nomeCompleto.getText().toString(),
                email.getText().toString(),
                CpfCnpjMaks.unmask(cpfCnpj.getText().toString()));
        try{
            if(ConexaoUtils.isConexao(getApplicationContext())){

                try {
                    Convite conviteResponse = ExecutorMetodoService.invoke(new ConviteService(this), "solicitarConvite", conviteNovo);
                    ToastUtils.show(NaoTenhoConviteActivity.this, "Chave: " + conviteResponse.getChave(), ToastUtils.INFORMATION);
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
