package com.iot.trabalho.grupo.appgrowler.Negocio;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.iot.trabalho.grupo.appgrowler.Util.Global;
import com.microsoft.windowsazure.messaging.NotificationHub;

public class RegistrationIntentService extends IntentService {
/*
    Referência para esta implementação:
    https://docs.microsoft.com/pt-br/azure/notification-hubs/notification-hubs-android-push-notification-google-gcm-get-started
*/

    private static final String TAG = "RegIntentService>>>>>";

    private NotificationHub hub;

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String resultString = null;
        String regID = null;

        try {
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(NotificationSettings.SenderId,
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE);
            Log.i(TAG, "Got GCM Registration Token: " + token);

            /*
                Armazena o token obtido para receber notificação push
                e enviar como parâmetro para a monitoração do growler
            */
            Global.putStringPrefs(this,Global.PREF_TOKEN_PUSH,token);
            Global.TOKEN_PUSH=token;

            // Storing the registration id that indicates whether the generated token has been
            // sent to your server. If it is not stored, send the token to your server,
            // otherwise your server should have already received the token.
            //if ((regID=sharedPreferences.getString(Global.PREF_REGISTRARION_ID, null)) == null) {
            if ((regID=Global.getStringPrefsByKey(this,Global.PREF_REGISTRATION_ID)).equals("")) {
                NotificationHub hub = new NotificationHub(NotificationSettings.HubName,
                        NotificationSettings.HubListenConnectionString, this);
                Log.i(TAG, "Attempting to register with NH using token : " + token);

                regID = hub.register(token).getRegistrationId();

                // If you want to use tags...
                // Refer to : https://azure.microsoft.com/en-us/documentation/articles/notification-hubs-routing-tag-expressions/
                // regID = hub.register(token, "tag1", "tag2").getRegistrationId();

                resultString = "Registered Successfully - RegId : " + regID;
                Log.i(TAG, resultString);
                //sharedPreferences.edit().putString(Global.PREF_REGISTRARION_ID, regID ).apply();
                Global.putStringPrefs(this, Global.PREF_REGISTRATION_ID,regID);
            } else {
                //Recupera o token obtido para receber a notificação push
                Global.TOKEN_PUSH=Global.getStringPrefsByKey(this,Global.PREF_TOKEN_PUSH);
                resultString = "Previously Registered Successfully - RegId : " + regID;
                Log.i(TAG, resultString);
            }
        } catch (Exception e) {
            Log.e(TAG, resultString="Failed to complete token refresh or hub register", e);
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
        }

    }
}
