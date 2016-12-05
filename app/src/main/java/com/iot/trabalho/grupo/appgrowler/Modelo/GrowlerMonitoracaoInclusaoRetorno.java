package com.iot.trabalho.grupo.appgrowler.Modelo;

/**
 * Created by Cintia on 04/12/2016.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GrowlerMonitoracaoInclusaoRetorno {

    @SerializedName("IdcErr")
    @Expose
    private Integer idcErr;
    @SerializedName("CodErr")
    @Expose
    private Integer codErr;
    @SerializedName("ExceptionMsg")
    @Expose
    private String exceptionMsg;
    @SerializedName("msg")
    @Expose
    private String msg;

    /**
     *
     * @return
     * The idcErr
     */
    public Integer getIdcErr() {
        return idcErr;
    }

    /**
     *
     * @param idcErr
     * The IdcErr
     */
    public void setIdcErr(Integer idcErr) {
        this.idcErr = idcErr;
    }

    /**
     *
     * @return
     * The codErr
     */
    public Integer getCodErr() {
        return codErr;
    }

    /**
     *
     * @param codErr
     * The CodErr
     */
    public void setCodErr(Integer codErr) {
        this.codErr = codErr;
    }

    /**
     *
     * @return
     * The exceptionMsg
     */
    public String getExceptionMsg() {
        return exceptionMsg;
    }

    /**
     *
     * @param exceptionMsg
     * The ExceptionMsg
     */
    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    /**
     *
     * @return
     * The msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     *
     * @param msg
     * The msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

}
