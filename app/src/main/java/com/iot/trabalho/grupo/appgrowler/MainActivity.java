package com.iot.trabalho.grupo.appgrowler;

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
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.iot.trabalho.grupo.appgrowler.Modelo.GrowlerAdapter;
import com.iot.trabalho.grupo.appgrowler.Modelo.GrowlerApp;
import com.iot.trabalho.grupo.appgrowler.Modelo.GrowlerBD;
import com.iot.trabalho.grupo.appgrowler.Negocio.MyHandler;
import com.iot.trabalho.grupo.appgrowler.Negocio.NotificationSettings;
import com.iot.trabalho.grupo.appgrowler.Negocio.RegistrationIntentService;
import com.microsoft.windowsazure.notifications.NotificationsManager;

import java.util.ArrayList;
import java.util.List;

/*
 import com.google.android.gms.gcm.*;
 import com.microsoft.windowsazure.notifications.NotificationsManager;
 import android.util.Log;
*/

public class MainActivity extends AppCompatActivity {
    private final Context context = this;
    private GrowlerBD growlerBD;
    //private ArrayAdapter<String> growlersAdapter;
    private GrowlerAdapter growlerAdapter;
    private ListView listaGrowlers;
    private String strGrowlerItemSelecionado;
    private ProgressDialog pDialog;
    //private int intPosicaoSelecionada;

    public static MainActivity mainActivity;
    public static Boolean isVisible = false;
    private GoogleCloudMessaging gcm;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicia o processo de registro quando a atividade é criada
        mainActivity = this;
        NotificationsManager.handleNotifications(this, NotificationSettings.SenderId, MyHandler.class);
        registerWithNotificationHubs();
        //

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it = new Intent(context , GrowlerManterActivity.class);
                it.putExtra("ACAO", "I");
                startActivity(it);
            }
        });

        growlerBD = new GrowlerBD(context);
        listaGrowlers = (ListView) findViewById(R.id.listViewGrowlers);

        //Para realizar a multiseleção
        listaGrowlers.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listaGrowlers.setItemsCanFocus(true);

        listaGrowlers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                strGrowlerItemSelecionado = RecuperarIdGrowler(view);
                Intent it = new Intent(context , GrowlerManterActivity.class);
                it.putExtra("ACAO", "A");
                it.putExtra("CHAVE", strGrowlerItemSelecionado);
                startActivity(it);
            }
        });

        listaGrowlers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                ImageView imgExcluirChecked = (ImageView)view.findViewById(R.id.imgExcluirChecked);
                if (imgExcluirChecked.getVisibility()== View.INVISIBLE) {
                    imgExcluirChecked.setVisibility(View.VISIBLE);
                }
                else {
                    imgExcluirChecked.setVisibility(View.INVISIBLE);
                }

                ListView lv = (ListView) findViewById(R.id.listViewGrowlers);
                registerForContextMenu(lv);
                growlerAdapter.toggleSelection(position);
                configuramenu(growlerAdapter.getSelectedCount());

                return true;

            }
        });

        //ProgressDialog para loading enquanto baixa os dados
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Aguarde...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);


        //Apresenta lista de growlers
        //growlerBD.atualizarTemperaturas();
        showLista();

    }

    private void configuramenu(int itensselecionados)
    {
        if  (_menu!=null) {
            _menu.findItem(R.id.action_edit).setVisible(itensselecionados==1);
            _menu.findItem(R.id.action_excluir).setVisible(itensselecionados>=1);
            _menu.findItem(R.id.action_historico).setVisible(itensselecionados==1);
            _menu.findItem(R.id.action_refresh).setVisible(itensselecionados==0);
            _menu.findItem(R.id.action_settings).setVisible(itensselecionados==0);
        }
    }

    Menu _menu;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        _menu=menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent it = new Intent(context , GrowlerConfigActivity.class);
            startActivity(it);
        }
        else if (id == R.id.action_refresh){
            //Toast.makeText(context, "Atualizando lista...", Toast.LENGTH_SHORT).show();
            new AsyncCallerAtualizarTemperaturas().execute();
            //growlerBD.atualizarTemperaturas();
            //showLista();
        }
        else if (id == R.id.action_edit){
            strGrowlerItemSelecionado = ObterGrowlerSelecionado();
            Intent it = new Intent(context , GrowlerManterActivity.class);
            it.putExtra("ACAO", "A");
            it.putExtra("CHAVE", strGrowlerItemSelecionado);
            startActivity(it);

        }
        else if (id == R.id.action_excluir){

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setTitle("Atenção");
            builder.setMessage("Confirma a exclusão deste growler?");
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    SparseBooleanArray selected = growlerAdapter.getSelectedIds();
                    // Percorre e Verifica os itens selecionados para a exclusão
                    for (int i = (selected.size() - 1); i >= 0; i--) {
                        if (selected.valueAt(i)) {
                            GrowlerApp selecteditem = (GrowlerApp) growlerAdapter.getItem(selected.keyAt(i));
                            //adapter.remove(selecteditem);// Remove o item se estiver selecionado (Observação: Remove da lista e executa o serviço WebAPI de exclusão

                            if (growlerBD.excluirGrowlerBDPorId(selecteditem.getIdentificadorGrowler())){
                                growlerAdapter.getListaGrowlerApp().remove(selecteditem);
                                growlerAdapter.notifyDataSetChanged();
                                Toast.makeText(context, "Growler excluído com sucesso.", Toast.LENGTH_SHORT).show();
                            }
                            else
                                Toast.makeText(context, "Erro ao excluir Growler", Toast.LENGTH_SHORT).show();
                        }
                    }

                    growlerAdapter.removeSelection();
                    configuramenu(growlerAdapter.getSelectedCount());

                }
            });

            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(context, "Exclusão do growler cancelada.", Toast.LENGTH_SHORT).show();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

        }

        else if (id == R.id.action_historico) {
            strGrowlerItemSelecionado = ObterGrowlerSelecionado();
            Intent it = new Intent(context, GrowlerHistoricoActivity.class);
            it.putExtra("ACAO", "H");
            it.putExtra("CHAVE", strGrowlerItemSelecionado);
            startActivity(it);

        }

        return super.onOptionsItemSelected(item);
    }

    private String ObterGrowlerSelecionado() {
        SparseBooleanArray selected = growlerAdapter.getSelectedIds();
        // Percorre e Verifica os itens selecionados
        for (int i = (selected.size() - 1); i >= 0; i--) {
            if (selected.valueAt(i)) {
                GrowlerApp selecteditem = (GrowlerApp) growlerAdapter.getItem(selected.keyAt(i));
                return Integer.toString(selecteditem.getIdentificadorGrowler());
            }
        }
        return "";
    }


    private String RecuperarIdGrowler(View view)
    {
        int idtGrowler= 0;
        if (view!=null) {
            TextView txtIdentificadorGrowler = (TextView) view.findViewById(R.id.txtIdentificadorGrowler);
            idtGrowler= Integer.parseInt(txtIdentificadorGrowler.getText().toString());

        }
        return Integer.toString(idtGrowler);
    }

    private void showLista(){

        //Listar growlers cadastrados na base de dados local
        if (growlerBD !=null) {

            List<GrowlerApp> lstGrowlers = new ArrayList<GrowlerApp>();
            lstGrowlers = growlerBD.getListaGrowlerBD();

            if (lstGrowlers.size()>0) {

                growlerAdapter = new GrowlerAdapter(context, growlerBD.getListaGrowlerBD());
                listaGrowlers.setAdapter(growlerAdapter);
            }
            else{
                Toast.makeText(getApplicationContext(),"Cadastre um growler para iniciar",Toast.LENGTH_SHORT).show();
            }

        }
        else {
            Toast.makeText(getApplicationContext(),"GrowlerBD nulo",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showLista();
        if (growlerAdapter!=null){
            growlerAdapter.notifyDataSetChanged();
        }
        configuramenu(0);
    }
/*
    @Override
    protected void onResume() {
        super.onResume();
        showLista();
        if (growlerAdapter!=null){
            growlerAdapter.notifyDataSetChanged();
        }
        configuramenu(0);
    }
*/

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported by Google Play Services.");
                ToastNotify("This device is not supported by Google Play Services.");
                finish();
            }
            return false;
        }
        return true;
    }
    public void registerWithNotificationHubs()
    {
        Log.i(TAG, " Registering with Notification Hubs");

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        isVisible = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isVisible = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isVisible = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isVisible = false;
    }

    public void ToastNotify(final String notificationMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, notificationMessage, Toast.LENGTH_LONG).show();
                TextView helloText = (TextView) findViewById(R.id.text_hello);
                helloText.setText(notificationMessage);
            }
        });
    }

    private class AsyncCallerAtualizarTemperaturas extends AsyncTask<Void, Void, Void>
    {
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);

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

            growlerBD.atualizarTemperaturas();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //this method will be running on UI thread
            showLista();
            pdLoading.dismiss();
        }

    }

}
