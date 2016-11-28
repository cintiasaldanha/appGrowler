package com.iot.trabalho.grupo.appgrowler.Negocio;

import android.os.StrictMode;

import com.google.gson.Gson;
import com.iot.trabalho.grupo.appgrowler.Modelo.EstruturaRaiz;
import com.iot.trabalho.grupo.appgrowler.Modelo.EstruturaRaizGrowler;
import com.iot.trabalho.grupo.appgrowler.Modelo.EstruturaRaizGrowlers;


/**
 * Created by ruben on 01/10/2016.
 */
public class GrowlerNegocio {

    public static EstruturaRaizGrowlers RecuperarGrowlers()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Requester2 req = new Requester2();
        String strjson = req.execmdDet("lergarrafas");

        Gson gson = new Gson();
        EstruturaRaizGrowlers ret = gson.fromJson(strjson, EstruturaRaizGrowlers.class);

        return ret;

    }


    public static EstruturaRaizGrowler RecuperarGrowler(Integer Id)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Requester2 req = new Requester2();//[metodo]->par1;par2;pasN
        String strjson = req.execmdDet("ConsultarGrowlerAtual->"+Id.toString());

        Gson gson = new Gson();
        EstruturaRaizGrowler ret = gson.fromJson(strjson, EstruturaRaizGrowler.class);

        return ret;
    }

    public static EstruturaRaiz Iniciargrowler(Integer idGrowler, Double ValorTempIdeal, boolean indNotficacaoTemp)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String strVlr = String.format("%4.2f" , ValorTempIdeal).replace(',','.') ;

        String strind = "0";
        if(indNotficacaoTemp)strind="1";else strind="0";

        Requester2 req = new Requester2();
        String strjson = req.execmdDet("iniciargrowler->"+idGrowler.toString()+";"+strVlr+";"+strind);

        Gson gson = new Gson();
        EstruturaRaiz ret = gson.fromJson(strjson, EstruturaRaiz.class);

        return ret;
    }



    public static EstruturaRaizGrowlers ConsultarHistoricoGrowler(String IdGrowler)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Requester2 req = new Requester2();
        String strjson = req.execmdDet("ConsultarHistoricoGrowler->"+IdGrowler);

        Gson gson = new Gson();
        EstruturaRaizGrowlers ret = gson.fromJson(strjson, EstruturaRaizGrowlers.class);

        return ret;

    }

    public static EstruturaRaizGrowlers ConsultarListaGrowlers()
    {
        EstruturaRaizGrowlers ret;

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Requester2 req = new Requester2();
            String strjson = req.execmdDet("lergarrafas");

            Gson gson = new Gson();
            ret = gson.fromJson(strjson, EstruturaRaizGrowlers.class);


        }
        catch (Exception e){

            throw e;

        }

        return ret;
    }

    public static EstruturaRaizGrowler ConsultarGrowlerAtual(String IdGrowler)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Requester2 req = new Requester2();
        String strjson = req.execmdDet("ConsultarGrowlerAtual->"+IdGrowler);

        Gson gson = new Gson();
        EstruturaRaizGrowler ret = gson.fromJson(strjson, EstruturaRaizGrowler.class);

        return ret;

    }

    public static EstruturaRaiz EsvaziarGrowler(Integer idGrowler)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Requester2 req = new Requester2();
        String strjson = req.execmdDet("EsvaziarGrowler->"+idGrowler.toString());

        Gson gson = new Gson();
        EstruturaRaiz ret = gson.fromJson(strjson, EstruturaRaiz.class);

        return ret;
    }


}
