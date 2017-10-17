package br.com.aio.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

import br.com.aio.R;
import br.com.aio.utils.BundleUtils;
import br.com.aio.utils.ToastUtils;

/**
 * Created by elton on 17/07/2017.
 */

public class CriarContaActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        verificarFireBase();
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE); // Removing
        setContentView();
    }

    private void setContentView() {
        setContentView(R.layout.activity_criar_conta);
        TextView criarConta, entrar;
        criarConta = (TextView) findViewById(R.id.criarConta);
        entrar = (TextView) findViewById(R.id.entrar);

        criarConta.setOnClickListener(this);
        entrar.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.criarConta:
                Intent newActivity0 = new Intent(CriarContaActivity.this, ConviteActivity.class);
                startActivity(newActivity0);
                break;
            case R.id.entrar:
                Intent newActivity = new Intent(CriarContaActivity.this, LoginActivity.class);
                startActivity(newActivity);
                break;
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
                            ToastUtils.show(CriarContaActivity.this,deepLink.toString(),ToastUtils.INFORMATION);
                            if(deepLink.toString().toUpperCase().contains(BundleUtils.DYNAMIC_LINK_MEU_PERFIL.toUpperCase())){
                                abrirActivity(new Intent(CriarContaActivity.this, MeuPerfilActivity.class));
                            }
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //ToastUtils.show(CriarContaActivity.this,"getDynamicLink:onFailure",ToastUtils.ERROR);

                    }
                });

    }

    private void abrirActivity(Intent i){
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
        startActivity(i);
    }
}
