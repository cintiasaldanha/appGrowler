package com.iot.trabalho.grupo.appgrowler.Modelo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iot.trabalho.grupo.appgrowler.R;
import com.iot.trabalho.grupo.appgrowler.Util.Global;

import java.util.List;

/**
 * Created by Cintia on 19/10/2016.
 */
public class TemperaturaAdapter extends BaseAdapter {

    private final Context context;
    private final List<Growler> listaGrowlers;
    static final String TAG = "http";

    public TemperaturaAdapter(Context context, List<Growler> growlers) {

        this.context = context;
        this.listaGrowlers = growlers;
    }


    @Override
    public int getCount() {

        return listaGrowlers != null ? listaGrowlers.size() : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Infla a view da linha/item
        final View view = LayoutInflater.from(context).inflate(R.layout.content_historico_listar_item, parent, false);

        // findViewById das views que precisa atualizar
        TextView txtIdentificadorGrowler = (TextView) view.findViewById(R.id.txtIdentificadorGrowler);
        TextView txtTemperaturaGrowler = (TextView) view.findViewById(R.id.txtTemperaturaGrowler);
        TextView txtPercentualBateria = (TextView) view.findViewById(R.id.txtPercentualBateria);
        TextView txtDataHoraTemperatura = (TextView) view.findViewById(R.id.txtDataHoraTemperatura);

        final Growler growler = listaGrowlers.get(position);
        txtIdentificadorGrowler.setText(growler.Id);
        txtTemperaturaGrowler.setText(growler.Temperatura);
        txtPercentualBateria.setText(growler.Bateria);
        //txtDataHoraTemperatura.setText(growler.DatahoraAtualizacao);

        //txtDataHoraTemperatura.setText(Global.formattedDateFromString("","",growler.DatahoraAtualizacao));

        txtDataHoraTemperatura.setText(Global.trocaFormatoData(growler.DatahoraAtualizacao,"yyyy-dd-MM hh:mm:ss","dd/MM/yyyy - hh:mm:ss"));

        return view;

    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public Object getItem(int position) {
        return listaGrowlers.get(position);
    }
}
