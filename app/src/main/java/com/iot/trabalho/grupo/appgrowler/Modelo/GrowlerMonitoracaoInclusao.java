package com.iot.trabalho.grupo.appgrowler.Modelo;

/**
 * Created by Cintia on 04/12/2016.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GrowlerMonitoracaoInclusao {

    @SerializedName("IdGrowler")
    @Expose
    private String idGrowler;
    @SerializedName("TempIdeal")
    @Expose
    private Double tempIdeal;
    @SerializedName("IndNotficacaoTemp")
    @Expose
    private Integer indNotficacaoTemp;
    @SerializedName("IdNotificacao")
    @Expose
    private String idNotificacao;

    /**
     *
     * @return
     * The idGrowler
     */
    public String getIdGrowler() {
        return idGrowler;
    }

    /**
     *
     * @param idGrowler
     * The IdGrowler
     */
    public void setIdGrowler(String idGrowler) {
        this.idGrowler = idGrowler;
    }

    /**
     *
     * @return
     * The tempIdeal
     */
    public Double getTempIdeal() {
        return tempIdeal;
    }

    /**
     *
     * @param tempIdeal
     * The TempIdeal
     */
    public void setTempIdeal(Double tempIdeal) {
        this.tempIdeal = tempIdeal;
    }

    /**
     *
     * @return
     * The indNotficacaoTemp
     */
    public Integer getIndNotficacaoTemp() {
        return indNotficacaoTemp;
    }

    /**
     *
     * @param indNotficacaoTemp
     * The IndNotficacaoTemp
     */
    public void setIndNotficacaoTemp(Integer indNotficacaoTemp) {
        this.indNotficacaoTemp = indNotficacaoTemp;
    }

    /**
     *
     * @return
     * The idNotificacao
     */
    public String getIdNotificacao() {
        return idNotificacao;
    }

    /**
     *
     * @param idNotificacao
     * The IdNotificacao
     */
    public void setIdNotificacao(String idNotificacao) {
        this.idNotificacao = idNotificacao;
    }

}