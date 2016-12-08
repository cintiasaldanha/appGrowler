package com.iot.trabalho.grupo.appgrowler.Negocio;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.iot.trabalho.grupo.appgrowler.GrowlerMensagemActivity;
import com.iot.trabalho.grupo.appgrowler.MainActivity;
import com.iot.trabalho.grupo.appgrowler.R;
import com.microsoft.windowsazure.notifications.NotificationsHandler;

/**
 * Created by Cintia on 28/11/2016.
 */
public class MyHandler extends NotificationsHandler {
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    Context ctx;

    @Override
    public void onReceive(Context context, Bundle bundle) {
        ctx = context;
        String nhMessage = bundle.getString("message");
        sendNotification(nhMessage);
        if (MainActivity.isVisible) {
            MainActivity.mainActivity.ToastNotify(nhMessage);
        }
    }

    private void sendNotification(String msg) {

        /*Código incluído apenas para teste unitário*/
        if (msg==null)
            msg="Sua cerveja atingiu a temperatura ideal! Aproveite!!!";

        //Intent correspondente à tela a ser exibida quando o app receber a notificação
        Intent it = new Intent(ctx, GrowlerMensagemActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        //it.putExtra("CHAVE",strIdtGrowler);
        //it.putExtra("TEMP_ATUAL",dblTemperaturaAtualGrowler.toString());
        it.putExtra("MSG",msg);
        PendingIntent p = PendingIntent.getActivity(ctx, Integer.parseInt("13"), it, PendingIntent.FLAG_ONE_SHOT);

        /*Configuração da notificação a ser apresentada*/
        mNotificationManager = (NotificationManager)
                ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,
                it, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(ctx)
                        .setLargeIcon(BitmapFactory.decodeResource(ctx.getResources(), R.mipmap.ic_launcher))
                        .setSmallIcon(R.mipmap.ic_growler)
                        .setContentTitle("Growler on the Cloud")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setSound(defaultSoundUri)
                        .setAutoCancel(true)
                        //.setFullScreenIntent(contentIntent,true)
                        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}

