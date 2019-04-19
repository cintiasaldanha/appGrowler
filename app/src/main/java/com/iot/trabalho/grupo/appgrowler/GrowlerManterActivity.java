package com.iot.trabalho.grupo.appgrowler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.iot.trabalho.grupo.appgrowler.Modelo.EstruturaRaiz;
import com.iot.trabalho.grupo.appgrowler.Modelo.GrowlerApp;
import com.iot.trabalho.grupo.appgrowler.Modelo.GrowlerBD;
import com.iot.trabalho.grupo.appgrowler.Negocio.AlarmManagerBroadcastReceiver;
import com.iot.trabalho.grupo.appgrowler.Negocio.GrowlerNegocio;
import com.iot.trabalho.grupo.appgrowler.Util.Global;

public class GrowlerManterActivity extends AppCompatActivity {
    private final Context context = this;
    private GrowlerBD growlerBD;
    String strIdtGrowler;
    private AlarmManagerBroadcastReceiver alarm;
    private String acao;
    private Button btnLerQrCode;
//   private Button btnHistorico;
    private EditText edtIdentificadorGrowler;
    private EditText edtDescricaoGrowler;
    private EditText edtDescricaoCerveja;
    private EditText edtVlrTemperaturaIdeal;
    private CheckBox chkAlarmeTemperatura;
    private TextView txtDescricaoCerveja;
    private TextView txtTemperaturaIdeal;
    private final String TAG = ">>>>POST MONITORACAO: ";
    private final String TAGEsvaziar="EsvaziarGrowler>>:";
    EstruturaRaiz estruturaRaizIniciarMonitoracao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_growler_manter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnLerQrCode = (Button) findViewById(R.id.btnLerQrCode);

        growlerBD = new GrowlerBD(context);
        alarm = new AlarmManagerBroadcastReceiver();

        edtIdentificadorGrowler = (EditText) findViewById(R.id.edtIdentificadorGrowler);
        edtDescricaoGrowler = (EditText) findViewById(R.id.edtDescricaoGrowler);
        edtDescricaoCerveja = (EditText) findViewById(R.id.edtDescricaoCerveja);
        edtVlrTemperaturaIdeal = (EditText) findViewById(R.id.edtVlrTemperaturaIdeal);
        chkAlarmeTemperatura = (CheckBox) findViewById(R.id.chkAlarmeTemperatura);
        txtDescricaoCerveja = (TextView) findViewById(R.id.txtDescricaoCerveja);
        txtTemperaturaIdeal = (TextView) findViewById(R.id.txtTemperaturaIdeal);
        btnLerQrCode = (Button) findViewById(R.id.btnLerQrCode);
        //btnHistorico = (Button) findViewById(R.id.btnHistorico);


        //Recebe a ação a ser realizada na interface
        Bundle extras = getIntent().getExtras();
        acao = extras.getString("ACAO");
        TratarVisibilidadeCampos(acao);

        if (acao.equals("A"))
        {
            strIdtGrowler = extras.getString("CHAVE").trim();
            RecuperarDadosInterface(strIdtGrowler);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setVisibility(View.VISIBLE);
        fab.setVisibility(View.INVISIBLE);
 /*       fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
*/
        final Activity activity = this;
        btnLerQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IntentIntegrator integrator =new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Posicione a câmera sobre a imagem");
                integrator.setCameraId(0);
                integrator.setOrientationLocked(false);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

        configuraMenu();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result != null) {
            if (result.getContents()!=null){
                edtIdentificadorGrowler.setText(result.getContents());
            }
            else {
                Toast.makeText(this,"Leitura cancelada",Toast.LENGTH_SHORT).show();
            }

        } else {
            super.onActivityResult(requestCode,resultCode,data);

        }

    }

    Menu _menu;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.content_growler_manter_menu, menu);
        _menu=menu;
        configuraMenu();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_salvar_item) {
            if (ValidarCamposObrigatorios()){
                    /*Inserir novo Growler*/
                if(acao.equals("I")) {
                    if (InserirGrowler()) {
                        Toast.makeText(getApplicationContext(), "Inserido com sucesso", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Erro na inserção", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (acao.equals("A")) {
                            /*Alterar growler existente*/
                    //Obtém os dados da interface:
                    GrowlerApp estGrowlerApp = new GrowlerApp(Integer.parseInt(edtIdentificadorGrowler.getText().toString()),
                            edtDescricaoGrowler.getText().toString(),
                            (edtDescricaoCerveja.getText().toString().equals("")?null:edtDescricaoCerveja.getText().toString()),
                            (edtVlrTemperaturaIdeal.getText().toString().equals("")?0:Double.parseDouble(edtVlrTemperaturaIdeal.getText().toString())),
                            (chkAlarmeTemperatura.isChecked()?1:0),
                            ((!edtDescricaoCerveja.getText().toString().equals("") && !edtVlrTemperaturaIdeal.getText().toString().equals(""))?1:0),0);


                    if (!AlterarGrowler(estGrowlerApp)){
//                                ((Activity)context).finish();
//                                Intent intent = new Intent(context, MainActivity.class);
//                                context.startActivity(intent);

                        Toast.makeText(getApplicationContext(), "Erro na alteração", Toast.LENGTH_SHORT).show();
                    }
                    else if (chkAlarmeTemperatura.isChecked()) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setIcon(android.R.drawable.ic_dialog_alert);
                        builder.setTitle("Atenção");
                        builder.setMessage("Confirma a monitoração do Growler?");
                        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //to do cintia
                                new AsyncCallerIniciarMonitoracao().execute();
                                //IniciarMonitoracaoGrowler();
                                //IniciarMonitoracaoGrowlerRetrofit();
                                ///
                                //((Activity)context).finish();
                                //Intent intent = new Intent(context, MainActivity.class);
                                //context.startActivity(intent);
                                //finish();
                            }
                        });

                        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "Alteração realizada. Notificação de temperatura não será enviada.", Toast.LENGTH_SHORT).show();
                                //((Activity)context).finish();
                                //Intent intent = new Intent(context, MainActivity.class);
                                //context.startActivity(intent);
                                finish();
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Alteração realizada com sucesso, sem notificaçao de temperatura.", Toast.LENGTH_SHORT).show();
                        //((Activity)context).finish();
                        //Intent intent = new Intent(context, MainActivity.class);
                        //context.startActivity(intent);
                        finish();
                    }

                    //finish();
                }
            }


        }
        else if (id == R.id.action_esvaziar){

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setTitle("Atenção");
                builder.setMessage("Deseja esvaziar o Growler?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EsvaziarGrowler(strIdtGrowler);
                    //((Activity)context).finish();
                    //Intent intent = new Intent(context, MainActivity.class);
                    //context.startActivity(intent);
                    finish();
                    }
               });

                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                 @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "O Growler permanece cheio", Toast.LENGTH_SHORT).show();
                        //((Activity)context).finish();
                        //Intent intent = new Intent(context, MainActivity.class);
                        //context.startActivity(intent);
                        finish();
                    }
                 });

                AlertDialog dialog = builder.create();
                dialog.show();

        }
        else if (id == R.id.action_historico) {

            Intent it = new Intent(context, GrowlerHistoricoActivity.class);
            //Intent it = new Intent(context, GrowlerEstatisticaActivity.class);
            it.putExtra("ACAO", "H");
            it.putExtra("CHAVE", strIdtGrowler);
            startActivity(it);

        }

        return super.onOptionsItemSelected(item);
    }

    private void RecuperarDadosInterface(String chave) {

        GrowlerApp growler = growlerBD.ObterGrowlerApp(Integer.parseInt(chave));
        edtIdentificadorGrowler.setText(Integer.toString(growler.getIdentificadorGrowler()));
        edtDescricaoGrowler.setText(growler.getDescricaoGrowler());
        edtDescricaoCerveja.setText(growler.getDescricaoCervejaGrowler());
        if (!edtDescricaoCerveja.getText().toString().equals(""))
            edtVlrTemperaturaIdeal.setText(Double.toString(growler.getVlrTemperaturaIdeal()));
        chkAlarmeTemperatura.setChecked(growler.getIndicadorAlarmeTemperatura()==1?true:false);
    }

    private void TratarVisibilidadeCampos(String acao) {

        if (acao.equals("I")){
            edtIdentificadorGrowler.setEnabled(true);
            btnLerQrCode.setVisibility(View.VISIBLE);
            //btnHistorico.setVisibility(View.GONE);
            txtDescricaoCerveja.setVisibility(View.GONE);
            edtDescricaoCerveja.setVisibility(View.GONE);
            txtTemperaturaIdeal.setVisibility(View.GONE);
            edtVlrTemperaturaIdeal.setVisibility(View.GONE);
            chkAlarmeTemperatura.setVisibility(View.GONE);
        }
        else {
            edtIdentificadorGrowler.setEnabled(false);
            btnLerQrCode.setVisibility(View.GONE);
            //btnHistorico.setVisibility(View.GONE);
//            btnHistorico.setVisibility(View.VISIBLE);
            txtTemperaturaIdeal.setVisibility(View.VISIBLE);
            edtDescricaoCerveja.setVisibility(View.VISIBLE);
            txtTemperaturaIdeal.setVisibility(View.VISIBLE);
            edtVlrTemperaturaIdeal.setVisibility(View.VISIBLE);
            chkAlarmeTemperatura.setVisibility(View.VISIBLE);
        }
    }

    private boolean InserirGrowler() {

        boolean ok = growlerBD.inserirGrowlerBD(Integer.parseInt(edtIdentificadorGrowler.getText().toString()),
                edtDescricaoGrowler.getText().toString(),null,0,0,0);
/*
        if (ok) {
            Toast.makeText(getApplicationContext(), "Inserido com sucesso", Toast.LENGTH_SHORT).show();
            //finish();

            //((Activity)context).finish();
            //Intent intent = new Intent(context, MainActivity.class);
            //context.startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(), "Erro na inserção", Toast.LENGTH_SHORT).show();
        }
*/
        return ok;

    }

    private Boolean AlterarGrowler(GrowlerApp growlerApp) {
        Boolean retorno = false;

        boolean ok = growlerBD.alterarGrowlerBD(growlerApp);
        if (ok) {
            retorno = true;
        }
        else {
            retorno = false;
        }
        return  retorno;
    }

    private boolean ValidarCamposObrigatorios() {
        
        boolean retorno = true;

        EditText edtIdentificadorGrowler = (EditText) findViewById(R.id.edtIdentificadorGrowler);
        EditText edtDescricaoGrowler = (EditText) findViewById(R.id.edtDescricaoGrowler);
 

        String strMensagem = "";

        if (edtIdentificadorGrowler.getText().toString().equals("")) {
            strMensagem = strMensagem + "Identificador do Growler é obrigatório\n";
        }

        if (edtDescricaoGrowler.getText().toString().equals("")) {
            strMensagem = strMensagem + "Descrição/Nome do Growler é obrigatório\n";
        }

        if(acao.equals("A")){

            if (edtDescricaoCerveja.getText().toString().equals("") && !edtVlrTemperaturaIdeal.getText().toString().equals("") ) {
                strMensagem = strMensagem + "Descrição da cerveja é obrigatória\n";
            }

            if (!edtDescricaoCerveja.getText().toString().equals("") && edtVlrTemperaturaIdeal.getText().toString().equals("")) {
                strMensagem = strMensagem + "Temperatura da cerveja é obrigatória\n";
            }

        }

        if (edtDescricaoCerveja.getText().toString().equals("") && edtVlrTemperaturaIdeal.getText().toString().equals("")) {
            chkAlarmeTemperatura.setChecked(false);
        }

        if (!strMensagem.equals("")) {
            retorno=false;
            Toast.makeText(context,strMensagem,Toast.LENGTH_SHORT).show();

        }

        return retorno;
    }

    private void IniciarMonitoracaoGrowler() {
        Integer IdGrowler = Integer.parseInt(strIdtGrowler);
        Double TmpIdeal = Double.parseDouble(edtVlrTemperaturaIdeal.getText().toString());
        Boolean bAlarmar = (chkAlarmeTemperatura.isChecked());
        //EstruturaRaiz estruturaRaizIniciarMonitoracao;

        try {
            if (Global.getBooleanPrefsByKey(this, Global.PREF_MONITORAR_LIMPAR_HISTORICO)) {
                estruturaRaizIniciarMonitoracao = GrowlerNegocio.Iniciargrowler(IdGrowler, TmpIdeal, bAlarmar);

            }
        }
        catch (Exception e) {
            Log.e(TAG, "Erro ao executar GrowlerNegocio.IniciarGrowler", e);

        }

    }

    private void AvaliarRetornoSolicitacaoMonitoracaoGrowler(){
        //Execução da solicitação de monitoração realizada com sucesso
        try {
            if (estruturaRaizIniciarMonitoracao.IdcErr == 0) {
                Context context = getApplicationContext();
                CharSequence text = "Alteração realizada com sucesso. Iniciando monitoração do Growler " + strIdtGrowler + " !";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                    /*Alarme substituído pela monitoração na nuvem, com recebimento de push notification
                    if (alarm != null) {
                        alarm.SetAlarm(context, strIdtGrowler, TmpIdeal);
                    } else {
                        Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
                    }
                    */
            } else {
                String mensagem = "Erro retornado pelo método GrowlerNegocio.IniciarGrowler"
                        + estruturaRaizIniciarMonitoracao.CodErr + " " + estruturaRaizIniciarMonitoracao.ExceptionMsg;
                Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
                Log.e(TAG, mensagem);
            }
        }
        catch (Exception e) {
            String mensagem = "Erro ao executar  método GrowlerNegocio.IniciarGrowler";
            Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
            Log.e(TAG, mensagem);

        }
    }
    private void EsvaziarGrowler(final String chave) {
        //Integer IdGrowler = Integer.parseInt(chave);

        Context context = getApplicationContext();
        CharSequence text = "Esvaziando Growler " + chave + " !";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        GrowlerApp estGrowlerApp = new GrowlerApp(Integer.parseInt(edtIdentificadorGrowler.getText().toString()),
                edtDescricaoGrowler.getText().toString(),"", 0, 0,0,0);

        if (AlterarGrowler(estGrowlerApp)){
            //Libera execução do serviço para limpar histórico de temperaturas
            if (Global.getBooleanPrefsByKey(this,Global.PREF_ESVAZIAR_LIMPAR_HISTORICO)) {
                EstruturaRaiz estruturaRaiz = GrowlerNegocio.EsvaziarGrowler(Integer.parseInt(edtIdentificadorGrowler.getText().toString()));
                String mensagem;
                if (estruturaRaiz.IdcErr==0){
                    mensagem = "Sucesso ao esvaziar o growler " + chave;
                    Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
                    Log.e(TAGEsvaziar,mensagem );
                }
                else {
                    mensagem = "Erro retornado pelo método GrowlerNegocio.EsvaziarGrowler"
                            + estruturaRaiz.CodErr + " " + estruturaRaiz.ExceptionMsg;
                    Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
                    Log.e(TAGEsvaziar, mensagem);
                }

            }
        }

    }
    private void configuraMenu()
    {
        Bundle extras = getIntent().getExtras();
        acao = extras.getString("ACAO");
        if  (_menu!=null) {

            if (acao.equals("I"))
                {
                    _menu.findItem(R.id.action_esvaziar).setVisible(false);
                    _menu.findItem(R.id.action_historico).setVisible(false);
                }
            else if (edtDescricaoCerveja.getText().toString().equals("")){
                    _menu.findItem(R.id.action_esvaziar).setVisible(false);
                    _menu.findItem(R.id.action_historico).setVisible(false);
            }

        }

    }

    private class AsyncCallerIniciarMonitoracao extends AsyncTask<Void, Void, Void>
    {
        ProgressDialog pdLoading = new ProgressDialog(GrowlerManterActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tSolicitando monitoração...");
            pdLoading.show();
        }
        @Override
        protected Void doInBackground(Void... params) {

            //this method will be running on background thread so don't update UI frome here
            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here

            IniciarMonitoracaoGrowler();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //this method will be running on UI thread
            AvaliarRetornoSolicitacaoMonitoracaoGrowler();
            pdLoading.dismiss();
            finish();
        }

    }
/*Teste para verificação de consumo assincrono:
    private void IniciarMonitoracaoGrowlerRetrofit() {

        //Utilização de Retrofit para executar o post correspondente
        //ao início da monitoração do growler
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        GrowlerMonitoracaoInclusao growlerMonitoracaoInclusao = new GrowlerMonitoracaoInclusao();

        growlerMonitoracaoInclusao.setIdGrowler(strIdtGrowler);
        growlerMonitoracaoInclusao.setIndNotficacaoTemp((chkAlarmeTemperatura.isChecked()? 1: 0));
        growlerMonitoracaoInclusao.setTempIdeal(Double.parseDouble(edtVlrTemperaturaIdeal.getText().toString()));
        growlerMonitoracaoInclusao.setIdNotificacao("dHjXf2hZ0rI:APA91bGJCNtMPugyVM8iqw06ZT-CV8MFk7WDIykM1iSgVvSXX2dIazjxajvKWYzIVnDib3pqcviPReTcmlfC0ikNgySgFCYKlsdqlpCmjWxilKeGO4NTJ8mzc_jIYn3zyXBGj0F_DsjL");

        //if (Global.getBooleanPrefsByKey(this,Global.PREF_MONITORAR_LIMPAR_HISTORICO))
        //    GrowlerNegocio.Iniciargrowler(IdGrowler,TmpIdeal,bAlarmar);

        try {
            Call<EstruturaRaiz> call = service.incluirMonitoracaoGrowler(growlerMonitoracaoInclusao);
            call.enqueue(new Callback<EstruturaRaiz>() {
                @Override
                public void onResponse(Call<EstruturaRaiz> call, Response<EstruturaRaiz> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "\"Alteração realizada com sucesso. Iniciando monitoração do Growler \" + strIdtGrowler + \" !\"", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Growler cadastrado com sucesso");

                        Log.e(TAG, "IndicadorErro: " + response.body().IdcErr.toString());


                    } else {
                        Toast.makeText(context, "Erro: Growler não cadastrado", Toast.LENGTH_LONG).show();
                        Log.e(TAG, "Erro: Growler não cadastrado");
                    }
                }

                @Override
                public void onFailure(Call<EstruturaRaiz> call, Throwable t) {
                    Log.e(TAG, "falha na inclusão do growler");
                }
            });
            //finish();


            //((Activity)context).finish();
            //Intent intent = new Intent(context, MainActivity.class);
            //context.startActivity(intent);
        }
        catch (Exception e) {
            Log.e(TAG, "Erro ao realizar o post para inclusão da monitoração do growler", e);
        }
    }
*/
}
