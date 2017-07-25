package br.com.aio.activity;

/**
 * Created by elton on 16/07/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import br.com.aio.R;

public class LoginActivity extends Activity implements OnClickListener {

        public static final String MEDIA = "Media";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getWindow().requestFeature(Window.FEATURE_NO_TITLE); // Removing
            // ActionBar
            String category = MEDIA;
            setContentView(category);
        }

        private void setContentView(String category) {
            EditText loginText;
            EditText passText;
            setContentView(R.layout.activity_login_page_media);
            loginText = (EditText) findViewById(R.id.login_page_media_login_text);
            passText = (EditText) findViewById(R.id.login_page_media_login_password);
            Typeface sRobotoThin = Typeface.createFromAsset(getAssets(),
                    "fonts/Roboto-Thin.ttf");
            ;
            loginText.setTypeface(sRobotoThin);
            passText.setTypeface(sRobotoThin);

            TextView login, register, skip;
            login = (TextView) findViewById(R.id.login);
            register = (TextView) findViewById(R.id.register);
            skip = (TextView) findViewById(R.id.skip);

            login.setOnClickListener(this);
            register.setOnClickListener(this);
            skip.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.login:
                    Intent newActivity0 = new Intent(LoginActivity.this, ListagemActivity.class);
                    startActivity(newActivity0);
                    break;
            }

        }
    }
