package com.iot.trabalho.grupo.appgrowler.Negocio;

import com.microsoft.windowsazure.notifications.NotificationsHandler;

/**
 * Created by Cintia on 28/11/2016.
 */
public class NotificationSettings extends NotificationsHandler {

    /*Id do aplicativo cadastrado no Google Developers Console*/
    public static String SenderId = "753012259939";
    public static String HubName = "hbPedidos";
    //public static String HubName = "hbscr13";
    //public static String HubListenConnectionString = "Endpoint=sb://hbscr13.servicebus.windows.net/;SharedAccessKeyName=DefaultListenSharedAccessSignature;SharedAccessKey=7IqJqBmOsvb37ksHdiC9J/s6hdJUh5SHCofJZ1+Voi0=";

    public static String HubListenConnectionString ="Endpoint=sb://hbscr13.servicebus.windows.net/;SharedAccessKeyName=DefaultListenSharedAccessSignature;SharedAccessKey=7IqJqBmOsvb37ksHdiC9J/s6hdJUh5SHCofJZ1+Voi0=";
}
