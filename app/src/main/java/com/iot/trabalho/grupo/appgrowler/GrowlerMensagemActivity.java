package com.iot.trabalho.grupo.appgrowler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iot.trabalho.grupo.appgrowler.Modelo.GrowlerApp;
import com.iot.trabalho.grupo.appgrowler.Modelo.GrowlerBD;
import com.iot.trabalho.grupo.appgrowler.Util.ConverterTextoParaVoz;
import com.iot.trabalho.grupo.appgrowler.Util.Global;

public class GrowlerMensagemActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private final Context context = this;
    String strChaveGrowlerAtual="0";
    String strTemperaturaAtual;
    private GrowlerBD growlerBD;

    //No exemplo tinha mas não sei para que funciona
    private static final int REQUEST_CODE = 1234;
    // \/ Gerenciamento do app para conversar com o usuário
    private TextToSpeech mTts = null;
    //Para o APP parar de falar
    //private boolean ativaFalar = true;
    private String strMensagemFala;
    private boolean somAtivo = false;

    private ImageView imgTemperaturaGrowler;
    private ImageView imgSom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_growler_mensagem);

        somAtivo = Global.getBooleanPrefsByKey(this,Global.PREF_NOTIFICAR_VOZ);

        Bundle extras = getIntent().getExtras();

        if (extras!=null) {
            strChaveGrowlerAtual = extras.getString("CHAVE");
            strTemperaturaAtual = extras.getString("TEMP_ATUAL");
        }

        ApresentarMensagem();

        mTts = new TextToSpeech(this, this);
        final String strTexto = "Temperatura ideal atingida";
        imgTemperaturaGrowler = (ImageView) findViewById(R.id.imgTemperaturaGrowler);

        imgTemperaturaGrowler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConverterTextoParaVoz.texto(strTexto,mTts,somAtivo);
            }
        });

        imgSom = (ImageView) findViewById(R.id.imgSom);
        imgSom.setImageResource((somAtivo? R.drawable.ic_microphone: R.drawable.ic_microphone_off));

        imgSom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (somAtivo){
                    somAtivo=false;
                    mTts.stop();
                    imgSom.setImageResource(R.drawable.ic_microphone_off);
                }
                else {
                    somAtivo=true;
                    imgSom.setImageResource(R.drawable.ic_microphone);
                    ConverterTextoParaVoz.texto(strMensagemFala,mTts,somAtivo);
                }

            }
        });

    }

    private void ApresentarMensagem() {
        TextView txtMsg = (TextView) findViewById(R.id.txtMsg);
        TextView txtIdtGrowler = (TextView) findViewById(R.id.txtIdtGrowler);
        StringBuilder strGrowler = new StringBuilder();
        StringBuilder strMsg = new StringBuilder();

        growlerBD = new GrowlerBD(context);

        GrowlerApp growler = growlerBD.ObterGrowlerApp(Integer.parseInt(strChaveGrowlerAtual));

        strGrowler.append("Growler ").append(growler.getIdentificadorGrowler()).append(" - ").append(growler.getDescricaoGrowler());
        txtIdtGrowler.setText(strGrowler);
        strMsg.append("Cerveja ").append(growler.getDescricaoCervejaGrowler()).append(" atingiu a temperatura ideal. Temperatura atual: ").append(strTemperaturaAtual).append(" graus! Aproveite!!!");
        txtMsg.setText(strMsg);

        strMensagemFala = strGrowler + " " + strMsg;

    }

    @Override
    public void onInit(int status) {
        if (somAtivo)
        //if (Global.getBooleanPrefsByKey(this,Global.PREF_NOTIFICAR_VOZ))
            ConverterTextoParaVoz.texto(strMensagemFala,mTts,somAtivo);

    }

    private void showCompartilhar() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Compartilhar");
        intent.putExtra(Intent.EXTRA_TEXT, "App Meu Growler: " + strMensagemFala);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.content_growler_mensagem_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_compartilhar) {
            showCompartilhar();
        }

        return super.onOptionsItemSelected(item);
    }
}
