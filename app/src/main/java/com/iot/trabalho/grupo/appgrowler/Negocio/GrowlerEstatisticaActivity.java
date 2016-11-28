package com.iot.trabalho.grupo.appgrowler.Negocio;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.iot.trabalho.grupo.appgrowler.Modelo.EstruturaRaizGrowlers;
import com.iot.trabalho.grupo.appgrowler.Modelo.Growler;
import com.iot.trabalho.grupo.appgrowler.Modelo.TemperaturaAdapter;
import com.iot.trabalho.grupo.appgrowler.R;

import java.util.List;

public class GrowlerEstatisticaActivity extends AppCompatActivity {
    private final Context context = this;
    //private GrowlerBD growlerBD;
    private String strChaveGrowlerAtual;
    public TemperaturaAdapter temperaturaAdapter;
    private ListView lvwTemperaturas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_growler_estatistica);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        lvwTemperaturas = (ListView) findViewById(R.id.lvwEstatisticaTemperaturas);

        Bundle extras = getIntent().getExtras();
        strChaveGrowlerAtual = extras.getString("CHAVE");


        showHistoricoGrowler();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void showHistoricoGrowler() {
        EstruturaRaizGrowlers Growlers = GrowlerNegocio.ConsultarHistoricoGrowler(strChaveGrowlerAtual);
        List<Growler> lst = null;

        if (Growlers.IdcErr == 0) {
            if (Growlers.Dados != null) {
                lst = Growlers.Dados.ListaGrowlers;
                if (lst.size() > 0) {
                    TemperaturaAdapter adp = new TemperaturaAdapter(this, lst);
                    lvwTemperaturas.setAdapter(adp);
                } else {
                    Toast.makeText(getApplicationContext(), "Histórico vazio", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(getApplicationContext(), "Growlers.Dados nulo", Toast.LENGTH_LONG).show();
            }
        } else {
            CharSequence text = "Erro ao consultar histórico: " + Growlers.CodErr.toString() + "-" + Growlers.ExceptionMsg;
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
        }

    }

}
