package com.iot.trabalho.grupo.appgrowler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


public class LoginIntegradoActivity extends AppCompatActivity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    private Button btnEntrarNoGrau;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_integrado);

        btnEntrarNoGrau = (Button) findViewById(R.id.btnEntrarNoGrau);

        btnEntrarNoGrau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apresentarActitivyPrincipal();
            }
        });

/**/
        if(AccessToken.getCurrentAccessToken() == null){
            System.out.println("not logged in yet");
        } else {
            System.out.println("Logged in");
        }

        if (Profile.getCurrentProfile()==null) {
            apresentarLoginUsuario();
        }
        else {
                /*todo: Retirar linha antes de gerar versão definitiva*/
                /////LoginManager.getInstance().logOut();
                //
                apresentarActitivyPrincipal();
        }

/**/

        //apresentarLoginUsuario();
        //apresentarActitivyPrincipal();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    private void apresentarActitivyPrincipal() {
        Intent i = new Intent(LoginIntegradoActivity.this,MainActivity.class);
        startActivity(i);
        //Fechar activity para evitar retorno ao login utilizando o botão Voltar do dispositivo
        finish();
    }

    private void apresentarLoginUsuario() {

        ///Código para realização de login via Facebook, conforme instruções registradas em
        //https://developers.facebook.com/docs/facebook-login/android
        loginButton = (LoginButton) findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();

        loginButton.setReadPermissions("email");

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Toast.makeText(getApplicationContext(), "Login via Facebook realizado com Sucesso", Toast.LENGTH_SHORT).show();
                apresentarActitivyPrincipal();

            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(getApplicationContext(), "Login via Facebook cancelado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(getApplicationContext(), "Erro ao realizar login via Facebook", Toast.LENGTH_SHORT).show();
            }
        });
        //////
    }

}
