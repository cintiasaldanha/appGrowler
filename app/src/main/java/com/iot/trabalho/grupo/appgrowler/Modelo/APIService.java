package com.iot.trabalho.grupo.appgrowler.Modelo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Cintia on 04/12/2016.
 */
public interface APIService {
    //Interface para utilização do RETROFIT para consumo de API

    /*URL do primeiro ambiente configurado*/
    //String BASE_URL = "http://40.124.9.111/api/Growler/";

    /*URL do segundo ambiente configurado*/
    //Serviço - Growler
    //url raiz WebApi: http://growlermonitor.cloudapp.net/api
    //IP do Servidor WebAPI: 13.85.84.69
    //url raiz WebApi com IP: http://13.85.84.69/api
    String BASE_URL = "http://growlermonitor.cloudapp.net/api/Growler/";

    //Obter lista de growlers cadastrados
    @GET("LerGarrafas/")
    Call<EstruturaRaizGrowlers> getListaGrowlers();

    //Obter dados de um determinado growler
    @GET("ConsultarGrowlerAtual/")
    Call<EstruturaRaizGrowler> getGrowlerAtual(@Path("id") String idGrowler);

    //Obter histórico de temperatura de um determinado growler
    @GET("ConsultarHistoricoGrowler/")
    Call<EstruturaRaizGrowlers> getHistoricoGrowler(@Path("id") String idGrowler);

    //Solicitar início da monitoração do growler
    @POST("Iniciargrowler/")
    Call<EstruturaRaiz> incluirMonitoracaoGrowler(@Body GrowlerMonitoracaoInclusao growlerMonitoracaoInclusao);

    @DELETE("EsvaziarGrowler/{id}")
    Call<EstruturaRaiz> excluirMonitoracaoGrowler(@Path("id") String idGrowler);

}
