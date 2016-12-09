package com.iot.trabalho.grupo.appgrowler.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Cintia on 28/10/2016.
 */
public class Global {

    public static String PREF_FILE_NAME = "meuGrowlerConfig";
    public static String PREF_PERIODO_ATUALIZACAO = "PERIODO_ATUALIZACAO";
    public static String PREF_NOTIFICAR_VOZ = "NOTIFICAR_VOZ";
    public static String PREF_MONITORAR_LIMPAR_HISTORICO = "ESVAZIAR_LIMPAR_HISTORICO";
    public static String PREF_ESVAZIAR_LIMPAR_HISTORICO = "MONITORAR_LIMPAR_HISTORICO";
    public static String PREF_TOKEN_PUSH = "TOKEN_PUSH";
    public static String PREF_REGISTRATION_ID = "registrationID";

    public static Context activitAnterior;
    public static String mensagemGenerica = "";
    public static String TOKEN_PUSH;

    public static void putStringPrefs(Context ctx, String chave, String valor) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREF_FILE_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(chave, valor);
        editor.commit();
    }

    public static void putBooleanPrefs(Context ctx, String chave, boolean valor){
        SharedPreferences prefs = ctx.getSharedPreferences(PREF_FILE_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(chave, valor);
        editor.commit();
    }

    public static boolean getBooleanPrefsByKey(Context ctx, String chave){
        SharedPreferences prefs = ctx.getSharedPreferences(PREF_FILE_NAME, 0);
        return prefs.getBoolean(chave, true);

    }

    public static String getStringPrefsByKey(Context ctx, String chave){
        SharedPreferences prefs = ctx.getSharedPreferences(PREF_FILE_NAME, 0);
        return prefs.getString(chave, "");
    }

    public static void putLongPrefs(Context ctx, String chave, long valor){
        SharedPreferences prefs = ctx.getSharedPreferences(PREF_FILE_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(chave, valor);
        editor.commit();
    }

    public static long getLongPrefsByKey(Context ctx, String chave){
        SharedPreferences prefs = ctx.getSharedPreferences(PREF_FILE_NAME, 0);
        return prefs.getLong(chave,1);

    }
    public static String formattedDateFromString(String inputFormat, String outputFormat, String inputDate) {
        if (inputFormat.equals("")) { // if inputFormat = "", set a default input format.
            inputFormat = "yyyy-MM-dd hh:mm:ss";
        }
        if (outputFormat.equals("")) {
            //outputFormat = "EEEE d 'de' MMMM 'del' yyyy"; // if inputFormat = "", set a default output format.
            outputFormat = "dd/MM/yyyy - HH:mm:ss";
        }
        Date parsed = null;
        String outputDate = "";

        //SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        //SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        // You can set a different Locale, This example set a locale of Country Mexico.
        //SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, new Locale("es", "MX"));
        //SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, new Locale("es", "MX"));

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, new Locale("es", "BR"));
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, new Locale("es", "BR"));

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);
        } catch (Exception e) {
            Log.e("formattedDateFromString", "Exception in formateDateFromstring(): " + e.getMessage());
        }
        return outputDate;
    }
}



