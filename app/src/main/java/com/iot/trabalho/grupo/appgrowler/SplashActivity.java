package com.iot.trabalho.grupo.appgrowler;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    // Timer da splash screen
    private static int SPLASH_TIME_OUT = 2000;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //ProgressDialog para loading enquanto baixa os dados
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Carregando...");
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();

        new Handler().postDelayed(new Runnable() {
            /*
             * Exibindo splash com um timer.
             */
            @Override
            public void run() {


                // Esse método será executado sempre que o timer acabar
                // E inicia a activity principal
                Intent i = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(i);

                pDialog.dismiss();

                // Fecha esta activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
