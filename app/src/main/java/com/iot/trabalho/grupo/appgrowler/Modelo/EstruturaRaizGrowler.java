package com.iot.trabalho.grupo.appgrowler.Modelo;

/**
 * Created by ruben on 01/10/2016.
 */
public class EstruturaRaizGrowler extends EstruturaRaiz {

    public Growler Dados;

    public EstruturaRaizGrowler(int idcErr, int codErr, Growler dados, String exceptionMsg) {
        this.IdcErr = idcErr;
        this.CodErr = codErr;
        this.Dados = dados;
        this.ExceptionMsg = exceptionMsg;
    }

}



