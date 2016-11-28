package com.iot.trabalho.grupo.appgrowler;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.iot.trabalho.grupo.appgrowler.Util.Global;

public class GrowlerConfigActivity extends AppCompatActivity {
    private EditText edtPeriodoAtualizacao;
    private Switch swNotVoz;
    private Switch swPrefMonitorarLimparHistorico;
    private Switch swPrefEsvaziarLimparHistorico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_growler_config);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtPeriodoAtualizacao = (EditText) findViewById(R.id.edtPeriodoAtualizacao);
        swNotVoz = (Switch) findViewById(R.id.swPrefNotificacoesVoz);
        swPrefMonitorarLimparHistorico = (Switch) findViewById(R.id.swPrefMonitorarLimparHistorico);
        swPrefEsvaziarLimparHistorico  = (Switch) findViewById(R.id.swPrefEsvaziarLimparHistorico);

        RecuperarConfiguracoes();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setVisibility(View.VISIBLE);
        fab.setVisibility(View.INVISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // if (ValidarCampos()) {
               //     SalvarConfiguracoes();
               //     finish();
               // }

            }
        });
    }

    private boolean ValidarCampos() {
        boolean retorno = true;

        if (edtPeriodoAtualizacao.getText().toString().equals("")){
            Toast.makeText(this, "Por favor, informe o período de atualização dos dados", Toast.LENGTH_SHORT).show();
            retorno = false;
        }
        else {
                long vlrPeriodo = Long.parseLong(edtPeriodoAtualizacao.getText().toString());
                if(vlrPeriodo<1 || vlrPeriodo>60){
                    Toast.makeText(this, "Por favor, informe período entre 1 e 60 minutos", Toast.LENGTH_SHORT).show();
                    retorno = false;
                }
        }

        return retorno;
    }

    private void RecuperarConfiguracoes() {

        edtPeriodoAtualizacao.setText(Long.toString(Global.getLongPrefsByKey(this,Global.PREF_PERIODO_ATUALIZACAO)));
        swNotVoz.setChecked(Global.getBooleanPrefsByKey(this,Global.PREF_NOTIFICAR_VOZ));
        swPrefMonitorarLimparHistorico.setChecked(Global.getBooleanPrefsByKey(this,Global.PREF_MONITORAR_LIMPAR_HISTORICO));
        swPrefEsvaziarLimparHistorico.setChecked(Global.getBooleanPrefsByKey(this,Global.PREF_ESVAZIAR_LIMPAR_HISTORICO));
    }

    private void SalvarConfiguracoes() {

        Global.putLongPrefs(this,Global.PREF_PERIODO_ATUALIZACAO,Long.parseLong(edtPeriodoAtualizacao.getText().toString()));
        Global.putBooleanPrefs(this,Global.PREF_NOTIFICAR_VOZ, swNotVoz.isChecked());
        Global.putBooleanPrefs(this,Global.PREF_MONITORAR_LIMPAR_HISTORICO, swPrefMonitorarLimparHistorico.isChecked());
        Global.putBooleanPrefs(this,Global.PREF_ESVAZIAR_LIMPAR_HISTORICO, swPrefEsvaziarLimparHistorico.isChecked());
        Toast.makeText(this, "Dados gravados com sucesso.", Toast.LENGTH_SHORT).show();
    }


    /*
        private void putStringPrefs(String chave, String valor) {
            SharedPreferences prefs = getSharedPreferences(Global.PREF_FILE_NAME, 0);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(chave, valor);
            editor.commit();
        }

        private void putBooleanPrefs(String chave, boolean valor){
            SharedPreferences prefs = getSharedPreferences(Global.PREF_FILE_NAME, 0);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(chave, valor);
            editor.commit();
        }

        private boolean getBooleanPrefsByKey(String chave){
            SharedPreferences prefs = getSharedPreferences(Global.PREF_FILE_NAME, 0);
            return prefs.getBoolean(chave, false);

        }

        private String getStringPrefsByKey(String chave){
            SharedPreferences prefs = getSharedPreferences(Global.PREF_FILE_NAME, 0);
            return prefs.getString(chave, "");
        }
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.content_growler_config_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_salvar_item) {
            if (ValidarCampos()){
                SalvarConfiguracoes();
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
