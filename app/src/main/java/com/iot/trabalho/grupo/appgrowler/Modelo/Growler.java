package com.iot.trabalho.grupo.appgrowler.Modelo;

/**
 * Created by ruben on 01/10/2016.
 */
public class Growler extends EstruturaBase {
    public String Id;
    public String Temperatura;
    public String Bateria;
    public String DatahoraAtualizacao;

    public Growler() {
        Id ="null";
        Temperatura="0";
        Bateria="0";
        DatahoraAtualizacao = "null";
    }

    public Growler(String id, String temperatura, String bateria, String datahoraAtualizacao) {
        this.Id = id;
        this.Temperatura = temperatura;
        this.Bateria = bateria;
        this.DatahoraAtualizacao = datahoraAtualizacao;
    }

    @Override
    public String toString() {
        return("Id: " + Id + ", " + "Temperatura: " + Temperatura);
    }
}



