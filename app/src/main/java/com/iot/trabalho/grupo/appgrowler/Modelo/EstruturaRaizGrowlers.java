package com.iot.trabalho.grupo.appgrowler.Modelo;

/**
 * Created by ruben on 01/10/2016.
 */
public class EstruturaRaizGrowlers extends EstruturaRaiz {

    public Growlers Dados;

    public EstruturaRaizGrowlers(int idcErr, int codErr, Growlers dados, String exceptionMsg) {
        this.IdcErr = idcErr;
        this.CodErr = codErr;
        this.Dados = dados;
        this.ExceptionMsg = exceptionMsg;
    }

}




