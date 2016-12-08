package com.iot.trabalho.grupo.appgrowler.Negocio;

import android.os.StrictMode;
import android.util.Log;

import com.iot.trabalho.grupo.appgrowler.Modelo.APIService;
import com.iot.trabalho.grupo.appgrowler.Modelo.EstruturaRaiz;
import com.iot.trabalho.grupo.appgrowler.Modelo.EstruturaRaizGrowler;
import com.iot.trabalho.grupo.appgrowler.Modelo.EstruturaRaizGrowlers;
import com.iot.trabalho.grupo.appgrowler.Modelo.GrowlerMonitoracaoInclusao;
import com.iot.trabalho.grupo.appgrowler.Util.Global;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Alterado por Cintia em 04/12/2016
 */
public class GrowlerNegocio {

    public static EstruturaRaiz Iniciargrowler(Integer idGrowler, Double ValorTempIdeal, boolean indNotficacaoTemp)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final String TAG=".IniciarGrowler:>>>>>";
        EstruturaRaiz estruturaRaiz = new EstruturaRaiz();

        //Utilização de Retrofit para executar o POST correspondente
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
        growlerMonitoracaoInclusao.setIdNotificacao(Global.TOKEN_PUSH);
        //growlerMonitoracaoInclusao.setIdNotificacao(Global.getStringPrefsByKey(,Global.PREF_TOKEN_PUSH));
        try {
            Call<EstruturaRaiz> call = service.incluirMonitoracaoGrowler(growlerMonitoracaoInclusao);
            Response<EstruturaRaiz> response = call.execute();

            if (response.isSuccessful()) {
                Log.e(TAG, "Growler cadastrado com sucesso");

            } else {
                Log.e(TAG, "Erro: Monitoração growler não cadastrada");
                Log.e(TAG, "Erro: " + response.body().ExceptionMsg);
            }

            estruturaRaiz = response.body();

        }
        catch (Exception e) {
            Log.e(TAG, "Erro ao executar GrowlerNegocio.IniciarGrowler", e);
            estruturaRaiz.IdcErr=1;
            estruturaRaiz.CodErr=999;
            estruturaRaiz.ExceptionMsg="Erro ao executar GrowlerNegocio.IniciarGrowler";
            estruturaRaiz.msg=estruturaRaiz.ExceptionMsg;
        }

        return estruturaRaiz ;

        /*Versão para consumo via socket:

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
    }


    public static EstruturaRaizGrowlers ConsultarHistoricoGrowler(String IdGrowler)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final String TAG=".CnsHistoricoGrowler:>";
        EstruturaRaizGrowlers estruturaRaizGrowlers= new EstruturaRaizGrowlers(0,0,null,"");

        //Utilização de Retrofit para executar o GET do histórico
        //de temperaturas de determinado growler

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        try {
            Call<EstruturaRaizGrowlers> call = service.getHistoricoGrowler(IdGrowler);
            Response<EstruturaRaizGrowlers> response = call.execute();

            if (response.isSuccessful()) {
                Log.e(TAG, "Historico do growler recuperado com sucesso");

            } else {
                Log.e(TAG, "Erro: Histórico do growler não recuperado");
                Log.e(TAG, "Erro: " + response.body().ExceptionMsg);
            }

            estruturaRaizGrowlers = response.body();

        }
        catch (Exception e) {
            Log.e(TAG, "Erro ao executar GrowlerNegocio.ConsultarHistoricoGrowler", e);
            estruturaRaizGrowlers.IdcErr=1;
            estruturaRaizGrowlers.CodErr=999;
            estruturaRaizGrowlers.ExceptionMsg="Erro ao executar GrowlerNegocio.ConsultarHistoricoGrowler";
            estruturaRaizGrowlers.msg=estruturaRaizGrowlers.ExceptionMsg;
        }

        return estruturaRaizGrowlers;

        /*Versão para consumo via socket:
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Requester2 req = new Requester2();
        String strjson = req.execmdDet("ConsultarHistoricoGrowler->"+IdGrowler);

        Gson gson = new Gson();
        EstruturaRaizGrowlers ret = gson.fromJson(strjson, EstruturaRaizGrowlers.class);

        return ret;
        */
    }

    public static EstruturaRaizGrowlers ConsultarListaGrowlers()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final String TAG=".CnsListaGrowlers:>>>>";
        EstruturaRaizGrowlers estruturaRaizGrowlers= new EstruturaRaizGrowlers(0,0,null,"");

        //Utilização de Retrofit para executar o GET correspondente
        //à obtenção da lista de growlers monitorados

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        try {
            Call<EstruturaRaizGrowlers> call = service.getListaGrowlers();
            Response<EstruturaRaizGrowlers> response = call.execute();

            if (response.isSuccessful()) {
                Log.e(TAG, "Lista de growlers recuperada com sucesso");

            } else {
                Log.e(TAG, "Erro: Lista de growlers não recuperada");
                Log.e(TAG, "IndicadorErro: " + response.body().IdcErr.toString());
            }

            estruturaRaizGrowlers = response.body();

        }
        catch (Exception e) {
            Log.e(TAG, "Erro ao executar GrowlerNegocio.ConsultarListaGrowlers", e);
            estruturaRaizGrowlers.IdcErr=1;
            estruturaRaizGrowlers.CodErr=999;
            estruturaRaizGrowlers.ExceptionMsg="Erro ao executar GrowlerNegocio.IniciarGrowler";
            estruturaRaizGrowlers.msg=estruturaRaizGrowlers.ExceptionMsg;
        }

        return estruturaRaizGrowlers;

        /*Versão para consumo via socket:
        EstruturaRaizGrowlers ret;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            Requester2 req = new Requester2();
            String strjson = req.execmdDet("lergarrafas");

            Gson gson = new Gson();
            ret = gson.fromJson(strjson, EstruturaRaizGrowlers.class);
        }
        catch (Exception e){
            throw e;
        }
        return ret;
        */
    }

    public static EstruturaRaizGrowler ConsultarGrowlerAtual(String IdGrowler)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final String TAG=".ConsultarGrowlerAtual>";
        EstruturaRaizGrowler estruturaRaizGrowler = new EstruturaRaizGrowler(0,0,null,"");

        //Utilização de Retrofit para executar o post correspondente
        //ao início da monitoração do growler

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);


        try {
            Call<EstruturaRaizGrowler> call = service.getGrowlerAtual(IdGrowler);
            Response<EstruturaRaizGrowler> response = call.execute();

            if (response.isSuccessful()) {
                Log.e(TAG, "Growler recuperado com sucesso");

            } else {
                Log.e(TAG, "Erro: Growler não recuperado");
                Log.e(TAG, "IndicadorErro: " + response.body().IdcErr.toString());
            }

            //estruturaRaiz = response.body();

        }
        catch (Exception e) {
            Log.e(TAG, "Erro ao executar GrowlerNegocio.ConsultarGrowlerAtual", e);
            estruturaRaizGrowler.IdcErr=1;
            estruturaRaizGrowler.CodErr=999;
            estruturaRaizGrowler.ExceptionMsg="Erro ao executar GrowlerNegocio.ConsultarGrowlerAtual";
            estruturaRaizGrowler.msg=estruturaRaizGrowler.ExceptionMsg;
        }

        return estruturaRaizGrowler;
        /*Versão para consumo via socket:
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Requester2 req = new Requester2();
        String strjson = req.execmdDet("ConsultarGrowlerAtual->"+IdGrowler);

        Gson gson = new Gson();
        EstruturaRaizGrowler ret = gson.fromJson(strjson, EstruturaRaizGrowler.class);

        return ret;
        */

    }

    public static EstruturaRaiz EsvaziarGrowler(Integer idGrowler)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final String TAG=".EsvaziarGrowler:>>>>>";
        EstruturaRaiz estruturaRaiz = new EstruturaRaiz();

        //Utilização de Retrofit para executar o DELETE correspondente
        //à exclusão da monitoração do growler

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        try {
            Call<EstruturaRaiz> call = service.excluirMonitoracaoGrowler(idGrowler.toString());
            Response<EstruturaRaiz> response = call.execute();

            if (response.isSuccessful()) {
                Log.e(TAG, "Growler esvaziado com sucesso");

            } else {
                Log.e(TAG, "Erro: Growler não foi esvaziado");
                Log.e(TAG, "IndicadorErro: " + response.body().IdcErr.toString());
            }

            estruturaRaiz = response.body();

        }
        catch (Exception e) {
            Log.e(TAG, "Erro ao executar GrowlerNegocio.EsvaziarGrowler", e);
            estruturaRaiz.IdcErr=1;
            estruturaRaiz.CodErr=999;
            estruturaRaiz.ExceptionMsg="Erro ao executar GrowlerNegocio.EsvaziarGrowler";
            estruturaRaiz.msg=estruturaRaiz.ExceptionMsg;
        }
        return estruturaRaiz;

        /*Versão para consumo via socket:
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Requester2 req = new Requester2();
        String strjson = req.execmdDet("EsvaziarGrowler->"+idGrowler.toString());

        Gson gson = new Gson();
        EstruturaRaiz ret = gson.fromJson(strjson, EstruturaRaiz.class);

        return ret;
        */
    }


}
