package com.iot.trabalho.grupo.appgrowler.Modelo;

/**
 * Created by Cintia on 21/10/2016.
 */
public class GrowlerApp {

    private int identificadorGrowler;
    private String descricaoGrowler;
    private String descricaoCervejaGrowler;
    private double vlrTemperaturaIdeal;
    private int indicadorAlarmeTemperatura;
    private int indicadorGrowlerCheio;
    private double vlrTemperaturaAtual;

    public GrowlerApp(int idtGrowler, String dscGrowler, String dscCerveja,
                      double vlrTemperaturaId, int idcAlarme, int idcGrowlerCheio,
                      double vlrTemperaturaAt){
        identificadorGrowler = idtGrowler;
        descricaoGrowler = dscGrowler;
        descricaoCervejaGrowler = dscCerveja;
        vlrTemperaturaIdeal = vlrTemperaturaId;
        vlrTemperaturaAtual = vlrTemperaturaAt;
        indicadorAlarmeTemperatura = idcAlarme;
        indicadorGrowlerCheio = idcGrowlerCheio;
    }

    public int getIdentificadorGrowler() {
        return identificadorGrowler;
    }


    public void setIdentificadorGrowler(int idtGrowler) {
        this.identificadorGrowler = idtGrowler;
    }


    public String getDescricaoGrowler() {
        return descricaoGrowler;
    }


    public void setDescricaoGrowler(String dscGrowler) {
        this.descricaoGrowler = dscGrowler;
    }


    public String getDescricaoCervejaGrowler() {
        return descricaoCervejaGrowler;
    }


    public void setIdentificadorCervejaGrowler(String dscCervejaGrowler) {
        this.descricaoCervejaGrowler = dscCervejaGrowler;
    }


    public double getVlrTemperaturaAtual() {
        return vlrTemperaturaAtual;
    }

    public void setVlrTemperaturaAtual(double valor) {
        this.vlrTemperaturaAtual = valor;
    }


    public double getVlrTemperaturaIdeal() {
        return vlrTemperaturaIdeal;
    }


    public void setVlrTemperaturaIdeal(double valor) {
        this.vlrTemperaturaIdeal = valor;
    }

    public int getIndicadorAlarmeTemperatura() {
        return indicadorAlarmeTemperatura;
    }


    public void setIndicadorAlarmeTemperatura(int idcAlarmeTemperatura) {
        this.indicadorAlarmeTemperatura = idcAlarmeTemperatura;
    }

    public int getIndicadorGrowlerCheio(){ return indicadorGrowlerCheio;}

    public void setIndicadorGrowlerCheio(int indicadorGrowlerCheio) {
        this.indicadorGrowlerCheio = indicadorGrowlerCheio;
    }
}
