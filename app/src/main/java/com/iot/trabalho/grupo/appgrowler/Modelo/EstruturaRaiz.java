package com.iot.trabalho.grupo.appgrowler.Modelo;

/**
 * Created by ruben on 01/10/2016.
 */
public class EstruturaRaiz {

    public Integer IdcErr;
    public Integer CodErr;
    public String ExceptionMsg;
    public String msg;

    public EstruturaRaiz() {
        this.IdcErr = 0;
        this.CodErr = 0;
        this.ExceptionMsg = "";
        this.msg = "";

    }

    public EstruturaRaiz(int idcErr, int codErr, String exceptionMsg) {
        this.IdcErr = idcErr;
        this.CodErr = codErr;
        this.ExceptionMsg = exceptionMsg;
        //this.msg = msg;
    }

    @Override
    public String toString() {
        return("IdcErr: " + IdcErr.toString() + ", " + "CodErr: " + CodErr.toString() +
                ", ExceptionMsg: " + ExceptionMsg +", "+ "msg: " + msg);
    }
}



