package br.com.aio;

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
import android.widget.Toast;

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

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                switch(tv.getText().charAt(0)) {
                    case 'S':
                        Intent newActivity0 = new Intent(LoginActivity.this, ListagemActivity.class);
                        startActivity(newActivity0);
                        break;
                }
                Toast.makeText(this, tv.getText(), Toast.LENGTH_SHORT).show();
            }
        }
    }
