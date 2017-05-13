package com.iot.trabalho.grupo.appgrowler;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.iot.trabalho.grupo.appgrowler.Modelo.EstruturaRaizGrowlers;
import com.iot.trabalho.grupo.appgrowler.Modelo.Growler;
import com.iot.trabalho.grupo.appgrowler.Modelo.TemperaturaAdapter;
import com.iot.trabalho.grupo.appgrowler.Negocio.GrowlerNegocio;

import java.util.List;

public class GrowlerHistoricoActivity extends AppCompatActivity {
    private final Context context = this;
    //private GrowlerBD growlerBD;
    private String strChaveGrowlerAtual;
    public TemperaturaAdapter temperaturaAdapter;
    private ListView lvwTemperaturas;
    private EstruturaRaizGrowlers Growlers;
    private final String TAG = ">>>GROWLER-->HISTORICO:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_growler_historico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvwTemperaturas = (ListView) findViewById(R.id.lvwTemperaturas);

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

        //showHistoricoGrowler();
        new AsyncCallerConsultarHistoricoGrowler().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.content_growler_historico_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            //Toast.makeText(context, "Atualizando lista...", Toast.LENGTH_SHORT).show();
            //showHistoricoGrowler();
            new AsyncCallerConsultarHistoricoGrowler().execute();
        }

        return super.onOptionsItemSelected(item);
    }
    private void consultarHistoricoGrowler(){

        try {
            Growlers = GrowlerNegocio.ConsultarHistoricoGrowler(strChaveGrowlerAtual);
        }
        catch (Exception e) {
            Log.e(TAG, "Erro ao executar GrowlerNegocio.IniciarGrowler", e);
        }
    }

    private void showHistoricoGrowler() {
        //EstruturaRaizGrowlers Growlers  = GrowlerNegocio.ConsultarHistoricoGrowler(strChaveGrowlerAtual);
        List<Growler> lst=null;

        if (Growlers.IdcErr==0){
            if (Growlers.Dados != null) {
                lst = Growlers.Dados.ListaGrowlers;
                if (lst.size()>0) {
                    TemperaturaAdapter adp = new TemperaturaAdapter(this, lst);
                    lvwTemperaturas.setAdapter(adp);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Histórico vazio",Toast.LENGTH_LONG).show();
                }

            }
            else {
                Toast.makeText(getApplicationContext(),"Growlers.Dados nulo",Toast.LENGTH_LONG).show();
            }
        }
        else{
             CharSequence text = "Erro ao consultar histórico: " + Growlers.CodErr.toString() + "-" + Growlers.ExceptionMsg;
             Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();
        }


    }
/*
    private void showListaGrowlers() {
        String strMsg;
        Growler growler = GrowlerNegocio.ConsultarGrowlerAtual("1").Dados;

        EstruturaRaizGrowler estruturaRaizGrowler = GrowlerNegocio.ConsultarGrowlerAtual("1");

        if (estruturaRaizGrowler.IdcErr==0){
            strMsg = "ConsultarGrowlerAtual: " + growler.Id + " - " + growler.Temperatura + " graus";

        }
        else {
            strMsg = "Erro " + estruturaRaizGrowler.CodErr.toString() + "-" + estruturaRaizGrowler.ExceptionMsg;
        }

        Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();

        //List<Growler> lst = GrowlerNegocio.RecuperarGrowlers().Dados.ListaGrowlers;
        //GrowlerAdapter adp = new GrowlerAdapter(this, R.layout.content_lista_growlers_item, lst);
        //lvwGrowlers.setAdapter(adp);

        //strMsg = lst.get(0).Id + " " + lst.get(0).Temperatura;
    }
*/

    private class AsyncCallerConsultarHistoricoGrowler extends AsyncTask<Void, Void, Void>
    {
        ProgressDialog pdLoading = new ProgressDialog(GrowlerHistoricoActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tObtendo dados...");
            pdLoading.show();
        }
        @Override
        protected Void doInBackground(Void... params) {

            //this method will be running on background thread so don't update UI frome here
            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here

            consultarHistoricoGrowler();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //this method will be running on UI thread
            showHistoricoGrowler();
            pdLoading.dismiss();
        }

    }
}
