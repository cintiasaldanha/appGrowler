package com.iot.trabalho.grupo.appgrowler.Negocio;

/**
 * Created by Cintia on 24/10/2016.
 */

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.iot.trabalho.grupo.appgrowler.GrowlerMensagemActivity;
import com.iot.trabalho.grupo.appgrowler.Modelo.EstruturaRaizGrowler;
import com.iot.trabalho.grupo.appgrowler.R;
import com.iot.trabalho.grupo.appgrowler.Util.Global;

import java.text.SimpleDateFormat;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

    final public static String ONE_TIME = "onetime";
    private String strIdtGrowler = "";
    private Double dblTemperaturaAtualGrowler = 0.0;
    private Double dblTemperaturaIdealGrowler;

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
        //Acquire the lock
        wl.acquire();

        //You can do the processing here.

        Bundle extras = intent.getExtras();
        if (extras !=null){
            strIdtGrowler= extras.getString("IDT_GROWLER");
            dblTemperaturaIdealGrowler = extras.getDouble("VLR_TEMP_IDEAL");
        }

        if (AtingiuTemperaturaIdealGrowler()) {

            NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

            Intent it = new Intent(context, GrowlerMensagemActivity.class);
            //it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            //it.putExtra("msg", "Cerveja no ponto!");
            it.putExtra("CHAVE",strIdtGrowler);
            it.putExtra("TEMP_ATUAL",dblTemperaturaAtualGrowler.toString());
            //PendingIntent p = PendingIntent.getActivity(context, 0, it, PendingIntent.FLAG_UPDATE_CURRENT);
            //PendingIntent p = PendingIntent.getActivity(context, Integer.parseInt(strIdtGrowler), it, PendingIntent.FLAG_ONE_SHOT);
            //PendingIntent p = PendingIntent.getBroadcast(context, Integer.parseInt(strIdtGrowler),it, 0);
            //PendingIntent p = PendingIntent.getActivity(context, Integer.parseInt(strIdtGrowler), it, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent p = PendingIntent.getActivity(context, Integer.parseInt(strIdtGrowler), it, PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle("Meu Growler");
            builder.setContentText("Sua cerveja está na temperatura ideal!");
            builder.setSmallIcon(R.mipmap.ic_growler);
            builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_growler));
            builder.setDefaults(Notification.DEFAULT_ALL);
            builder.setAutoCancel(true);
            builder.setContentIntent(p);

            //nm.notify(1, builder.build());

            //Defini o código de notificação por growler para que cada alarme possa ser acompanhado na interface
            nm.notify(Integer.parseInt(strIdtGrowler),builder.build());

            CancelAlarm(context);
            //
        }

        //Release the lock
        wl.release();
    }

    public void SetAlarm(Context context, String strIdtGrowlerMonitorado, Double dblTemperaturaIdeal)
    {
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra("IDT_GROWLER", strIdtGrowlerMonitorado);
        intent.putExtra("VLR_TEMP_IDEAL", dblTemperaturaIdeal);
        //PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        PendingIntent pi = PendingIntent.getBroadcast(context, Integer.parseInt(strIdtGrowlerMonitorado), intent, 0);
        //After after 5 seconds
        //am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 5 , pi);
        /*Repete a cada n minutos, conforme arquivo de configuração*/
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), RecuperarPeriodoAtualizacao(context) , pi);
    }

    private long RecuperarPeriodoAtualizacao(Context context) {
        long retorno;

        retorno = Global.getLongPrefsByKey(context,Global.PREF_PERIODO_ATUALIZACAO);
        //Transforma o valor recuperado em minutos em segundos
        retorno = retorno * 60000;

        return retorno;
    }

    public void CancelAlarm(Context context)
    {
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        //PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        PendingIntent sender = PendingIntent.getBroadcast(context, Integer.parseInt(strIdtGrowler), intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public void setOnetimeTimer(Context context){
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(ONE_TIME, Boolean.TRUE);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi);
    }

    private Boolean AtingiuTemperaturaIdealGrowler() {
        Boolean retorno = false;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
        long date;
        String dateString;
        String strMensagem;

        EstruturaRaizGrowler estruturaRaizGrowler = GrowlerNegocio.ConsultarGrowlerAtual(strIdtGrowler);

        if (estruturaRaizGrowler!=null){
            if (estruturaRaizGrowler.IdcErr == 0) {
                if (estruturaRaizGrowler.Dados != null) {
                    dblTemperaturaAtualGrowler = Double.parseDouble(estruturaRaizGrowler.Dados.Temperatura);
                    if (dblTemperaturaAtualGrowler<= dblTemperaturaIdealGrowler){

                        date = System.currentTimeMillis();
                        dateString = sdf.format(date);

                        strMensagem = dateString + ">>>>>Growler " + strIdtGrowler + " atingiu a temperatura ideal!!!!!";
                        Log.i("AlarmManager:",strMensagem );
                        strMensagem = "Temp atual = " + dblTemperaturaAtualGrowler.toString() + " - Temp ideal = " + dblTemperaturaIdealGrowler.toString();
                        Log.i("AlarmManager:",strMensagem );

                        retorno=true;
                    }
                    else {

                        date = System.currentTimeMillis();
                        dateString = sdf.format(date);

                        strMensagem = dateString + ">>>>>Growler " + strIdtGrowler + " não atingiu a temperatura ideal";
                        Log.i("AlarmManager:",strMensagem );
                        strMensagem = "Temp atual = " + dblTemperaturaAtualGrowler.toString() + " - Temp ideal = " + dblTemperaturaIdealGrowler.toString();
                        Log.i("AlarmManager:",strMensagem );

                    }
                }
                else {

                    date = System.currentTimeMillis();
                    dateString = sdf.format(date);

                    strMensagem = dateString + ">>>>>Growler " + strIdtGrowler + " - estruturaRaizGrowler.Dados nula";
                    Log.i("AlarmManager:",strMensagem );

                }
            }
        }
        else {
            date = System.currentTimeMillis();
            dateString = sdf.format(date);

            strMensagem = dateString + ">>>>>Growler " + strIdtGrowler + " - estruturaRaizGrowler nula";
            Log.i("AlarmManager:",strMensagem );

        }

        return retorno;

    }
}

