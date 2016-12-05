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

    String BASE_URL = "http://40.124.9.111/api/Growler/";

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


    /*
    String BASE_URL = "https://parseapi.back4app.com/classes/";

    @Headers({"X-Parse-Application-Id:FWmmldOSRF8GE7jR8424Ex9Tu2ZHLTrggQHLJvjY",
            "X-Parse-REST-API-Key:RegHHKDEd3qf260q0mGUM7Z7GMsWry79eKsv3Jic"})
    @GET("Aluno")
    Call<Alunos> getAlunos();

    @Headers({"X-Parse-Application-Id:FWmmldOSRF8GE7jR8424Ex9Tu2ZHLTrggQHLJvjY",
            "X-Parse-REST-API-Key:RegHHKDEd3qf260q0mGUM7Z7GMsWry79eKsv3Jic"})
    @POST("Aluno")
    Call<AlunoInclusao> incluirAluno(@Body AlunoInclusao alunoInclusao);

    @Headers({"X-Parse-Application-Id:FWmmldOSRF8GE7jR8424Ex9Tu2ZHLTrggQHLJvjY",
            "X-Parse-REST-API-Key:RegHHKDEd3qf260q0mGUM7Z7GMsWry79eKsv3Jic"})
    @DELETE("Aluno/{id}")
    Call<ResponseBody> excluirAluno(@Path("id") String idAluno);
    */
}
