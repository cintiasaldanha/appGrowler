package com.iot.trabalho.grupo.appgrowler;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.microsoft.azure.mobile.MobileCenter;
import com.microsoft.azure.mobile.analytics.Analytics;
import com.microsoft.azure.mobile.crashes.Crashes;

public class SplashActivity extends AppCompatActivity {
    // Timer da splash screen
    private static int SPLASH_TIME_OUT = 2000;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        MobileCenter.start(getApplication(), "3a6fa856-5e12-476d-af5a-3075dd406173", Analytics.class, Crashes.class);

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
                /////Intent i = new Intent(SplashActivity.this,MainActivity.class);
                Intent i = new Intent(SplashActivity.this,LoginIntegradoActivity.class);
                startActivity(i);

                pDialog.dismiss();

                // Fecha esta activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
