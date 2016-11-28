package com.iot.trabalho.grupo.appgrowler;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.iot.trabalho.grupo.appgrowler.Modelo.EstruturaRaizGrowler;
import com.iot.trabalho.grupo.appgrowler.Modelo.GrowlerApp;
import com.iot.trabalho.grupo.appgrowler.Modelo.GrowlerBD;
import com.iot.trabalho.grupo.appgrowler.Negocio.AlarmManagerBroadcastReceiver;
import com.iot.trabalho.grupo.appgrowler.Negocio.GrowlerNegocio;

public class GrowlerConsultarActivity extends AppCompatActivity {
    private final Context context = this;
    private GrowlerBD growlerBD;
    private String strChaveGrowlerAtual;
    private AlarmManagerBroadcastReceiver alarm;
    private TextView txtLabelIdentificadorGrowler;
    private TextView txtIdentificadorGrowler;
    private TextView txtLabelDescricaoGrowler;
    private TextView txtDescricaoGrowler;
    private TextView txtLabelDescricaoCerveja;
    private TextView txtDescricaoCerveja;
    private TextView txtLabelTemperaturaIdeal;
    private TextView txtVlrTemperaturaIdeal;
    private TextView txtLabelAlarmeTemperatura;
    private TextView txtIdcAlarmeTemperaturaIdeal;
    private TextView txtLabelTemperaturaAtual;
    private TextView txtTemperaturaAtual;
    private Button btnAlterarGrowler;
    private Button btnIniciarMonitoracaoGrowler;
    private Button btnHistoricoGrowler;
    private Button btnEsvaziarGrowler;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_growler_consultar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        growlerBD = new GrowlerBD(context);
        alarm = new AlarmManagerBroadcastReceiver();

        btnAlterarGrowler = (Button) findViewById(R.id.btnAlterarGrowler);
        btnIniciarMonitoracaoGrowler = (Button) findViewById(R.id.btnIniciarMonitoracaoGrowler);
        btnHistoricoGrowler = (Button) findViewById(R.id.btnHistoricoGrowler);
        btnEsvaziarGrowler = (Button) findViewById(R.id.btnEsvaziarGrowler);

        //txtLabelIdentificadorGrowler = (TextView) findViewById(R.id.txtLabelIdentificadorGrowler);
        txtIdentificadorGrowler = (TextView) findViewById(R.id.txtIdentificadorGrowler);
        //txtLabelDescricaoGrowler
        txtDescricaoGrowler = (TextView) findViewById(R.id.txtDescricaoGrowler);
        txtLabelDescricaoCerveja = (TextView) findViewById(R.id.txtLabelDescricaoCerveja);
        txtDescricaoCerveja = (TextView) findViewById(R.id.txtDescricaoCerveja);
        txtLabelTemperaturaIdeal = (TextView) findViewById(R.id.txtLabelTemperaturaIdeal);
        txtVlrTemperaturaIdeal = (TextView) findViewById(R.id.txtVlrTemperaturaIdeal);
        txtLabelAlarmeTemperatura = (TextView) findViewById(R.id.txtLabelAlarmeTemperatura);
        txtIdcAlarmeTemperaturaIdeal = (TextView) findViewById(R.id.txtIdcAlarmeTemperaturaIdeal);
        txtLabelTemperaturaAtual = (TextView) findViewById(R.id.txtLabelTemperaturaAtual);
        txtTemperaturaAtual = (TextView) findViewById(R.id.txtTemperaturaAtual);


        Bundle extras = getIntent().getExtras();
        strChaveGrowlerAtual = extras.getString("CHAVE");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setVisibility(View.VISIBLE);
        fab.setVisibility(View.INVISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btnAlterarGrowler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, GrowlerManterActivity.class);
                it.putExtra("ACAO", "A");
                it.putExtra("CHAVE", strChaveGrowlerAtual);
                startActivity(it);
            }
        });

        btnIniciarMonitoracaoGrowler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IniciarMonitoracaoGrowler(strChaveGrowlerAtual);
            }
        });

        btnHistoricoGrowler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, GrowlerHistoricoActivity.class);
                it.putExtra("ACAO", "H");
                it.putExtra("CHAVE", strChaveGrowlerAtual);
                startActivity(it);
            }
        });

        btnEsvaziarGrowler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EsvaziarGrowler(strChaveGrowlerAtual);
            }
        });

        RecuperarDadosInterface(strChaveGrowlerAtual);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void EsvaziarGrowler(final String chave) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("Atenção");
        builder.setMessage("Deseja esvaziar o growler?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Integer IdGrowler = Integer.parseInt(chave);

                //GrowlerNegocio.EsvaziarGrowler(IdGrowler);

                Context context = getApplicationContext();
                CharSequence text = "Esvaziando Growler " + chave + " !";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                //To do: atualizar base de dados com idcGrowlerVazio==true


            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Monitoração do growler cancelada.", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void RecuperarDadosInterface(String chave) {

        GrowlerApp growler = growlerBD.ObterGrowlerApp(Integer.parseInt(chave));
        txtIdentificadorGrowler.setText(Integer.toString(growler.getIdentificadorGrowler()));
        txtDescricaoGrowler.setText(growler.getDescricaoGrowler());
        txtDescricaoCerveja.setText(growler.getDescricaoCervejaGrowler());
        txtVlrTemperaturaIdeal.setText(Double.toString(growler.getVlrTemperaturaIdeal()));
        txtIdcAlarmeTemperaturaIdeal.setText(growler.getIndicadorAlarmeTemperatura() == 1 ? "Sim" : "Não");
        //if (growler.getIndicadorGrowlerCheio) {
        txtTemperaturaAtual.setText(ConsultarTemperaturaAtualGrowler(chave));
        //}
    }

    private String ConsultarTemperaturaAtualGrowler(String strIdtGrowler) {
        String strTextoTemperatura;

        //Growler growler = GrowlerNegocio.ConsultarGrowlerAtual(strIdtGrowler).Dados;

        EstruturaRaizGrowler estruturaRaizGrowler = GrowlerNegocio.ConsultarGrowlerAtual(strChaveGrowlerAtual);

        if (estruturaRaizGrowler.IdcErr == 0) {
            if (estruturaRaizGrowler.Dados != null)
                strTextoTemperatura = estruturaRaizGrowler.Dados.Temperatura + " graus";
            else
                strTextoTemperatura = "Temperatura não recuperada do serviço ";

        } else {
            strTextoTemperatura = "Erro " + estruturaRaizGrowler.CodErr.toString() + "-" + estruturaRaizGrowler.ExceptionMsg;
        }

        return strTextoTemperatura;

    }

    private void IniciarMonitoracaoGrowler(final String chave) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("Atenção");
        builder.setMessage("Confirma a monitoração do Growler?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Integer IdGrowler = Integer.parseInt(chave);
                Double TmpIdeal = Double.parseDouble(txtVlrTemperaturaIdeal.getText().toString());
                Boolean bAlarmar = (txtIdcAlarmeTemperaturaIdeal.getText().toString().equals("Sim") ? true : false);

                //GrowlerNegocio.Iniciargrowler(IdGrowler,TmpIdeal,bAlarmar);

                Context context = getApplicationContext();
                CharSequence text = "Iniciando Growler " + chave + " !";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                if (bAlarmar){
                    //Context ctx = getApplicationContext();
                    if(alarm != null){
                        alarm.SetAlarm(context,chave,TmpIdeal);
                    }else{
                        Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
                    }
                }
                //AtualizarCampos();

                finish();
/*
                ((Activity)context).finish();
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
*/
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Monitoração do growler cancelada.", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "GrowlerConsultar Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.iot.trabalho.grupo.appgrowler/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "GrowlerConsultar Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.iot.trabalho.grupo.appgrowler/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
