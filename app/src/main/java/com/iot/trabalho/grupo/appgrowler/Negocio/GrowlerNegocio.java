package com.iot.trabalho.grupo.appgrowler.Negocio;

import android.os.StrictMode;
import android.util.Log;

import com.google.gson.Gson;
import com.iot.trabalho.grupo.appgrowler.Modelo.APIService;
import com.iot.trabalho.grupo.appgrowler.Modelo.EstruturaRaiz;
import com.iot.trabalho.grupo.appgrowler.Modelo.EstruturaRaizGrowler;
import com.iot.trabalho.grupo.appgrowler.Modelo.EstruturaRaizGrowlers;
import com.iot.trabalho.grupo.appgrowler.Modelo.GrowlerMonitoracaoInclusao;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


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
/*
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
*/
        EstruturaRaiz estruturaRaiz = new EstruturaRaiz();

        //Utilização de Retrofit para executar o post correspondente
        //ao início da monitoração do growler

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        GrowlerMonitoracaoInclusao growlerMonitoracaoInclusao = new GrowlerMonitoracaoInclusao();

        growlerMonitoracaoInclusao.setIdGrowler(idGrowler.toString());
        growlerMonitoracaoInclusao.setIndNotficacaoTemp(indNotficacaoTemp? 1: 0);
        growlerMonitoracaoInclusao.setTempIdeal(Double.parseDouble(ValorTempIdeal.toString()));
        growlerMonitoracaoInclusao.setIdNotificacao("dHjXf2hZ0rI:APA91bGJCNtMPugyVM8iqw06ZT-CV8MFk7WDIykM1iSgVvSXX2dIazjxajvKWYzIVnDib3pqcviPReTcmlfC0ikNgySgFCYKlsdqlpCmjWxilKeGO4NTJ8mzc_jIYn3zyXBGj0F_DsjL");
        try {
            Call<EstruturaRaiz> call = service.incluirMonitoracaoGrowler(growlerMonitoracaoInclusao);
            Response<EstruturaRaiz> response = call.execute();

            if (response.isSuccessful()) {
                //Toast.makeText(context, "\"Alteração realizada com sucesso. Iniciando monitoração do Growler \" + strIdtGrowler + \" !\"", Toast.LENGTH_SHORT).show();
                Log.e(".IniciarGrowler:>>>>>", "Growler cadastrado com sucesso");
                Log.e(".IniciarGrowler:>>>>>", "IndicadorErro: " + response.body().IdcErr.toString());


            } else {
                //Toast.makeText(context, "Erro: Growler não cadastrado", Toast.LENGTH_LONG).show();
                Log.e(".IniciarGrowler:>>>>>", "Erro: Monitoração growler não cadastrada");
            }

            estruturaRaiz = response.body();

        }
        catch (Exception e) {
            Log.e(".IniciarGrowler:>>>>>", "Erro ao executar GrowlerNegocio.IniciarGrowler", e);
            estruturaRaiz.IdcErr=1;
            estruturaRaiz.CodErr=999;
            estruturaRaiz.ExceptionMsg="Erro ao executar GrowlerNegocio.IniciarGrowler";
            estruturaRaiz.msg=estruturaRaiz.ExceptionMsg;
        }


        return estruturaRaiz ;
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
