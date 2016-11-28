package com.iot.trabalho.grupo.appgrowler.Modelo;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iot.trabalho.grupo.appgrowler.Negocio.GrowlerNegocio;
import com.iot.trabalho.grupo.appgrowler.R;

import java.util.List;

/**
 * Created by Cintia on 19/10/2016.
 */
public class GrowlerAdapter extends BaseAdapter {

    private final Context context;
    private final List<GrowlerApp> listaGrowlersApp;
    static final String TAG = "http";
    private SparseBooleanArray mSelectedItemsIds;

    public GrowlerAdapter(Context context, List<GrowlerApp> growlersApp) {

        this.context = context;
        this.listaGrowlersApp = growlersApp;
        this.mSelectedItemsIds = new SparseBooleanArray();
    }


    @Override
    public int getCount() {

        return listaGrowlersApp != null ? listaGrowlersApp.size() : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Infla a view da linha/item
        final View view = LayoutInflater.from(context).inflate(R.layout.content_growler_listar_item, parent, false);

        // findViewById das views que precisa atualizar
        //ImageView imgGrowler = (ImageView) view.findViewById(R.id.imgGrowler);
        TextView txtIdentificadorGrowler = (TextView) view.findViewById(R.id.txtIdentificadorGrowler);
        TextView txtDescricaoGrowler = (TextView) view.findViewById(R.id.txtDescricaoGrowler);
        TextView txtIndicadorGrowlerCheio = (TextView) view.findViewById(R.id.txtIndicadorGrowlerCheio);
        TextView txtLabelDescricaoCervejaGrowler = (TextView) view.findViewById(R.id.txtLabelDescricaoCervejaGrowler);
        TextView txtDescricaoCervejaGrowler = (TextView) view.findViewById(R.id.txtDescricaoCervejaGrowler);
        TextView txtLabelTemperaturaIdeal = (TextView) view.findViewById(R.id.txtLabelTemperaturaIdeal);
        TextView txtTemperaturaIdealGrowler = (TextView) view.findViewById(R.id.txtTemperaturaIdealGrowler);
        TextView txtLabelTemperaturaAtual = (TextView) view.findViewById(R.id.txtLabelTemperaturaAtual);
        TextView txtTemperaturaAtualGrowler = (TextView) view.findViewById(R.id.txtTemperaturaAtualGrowler);
        ImageView imgGrowler = (ImageView) view.findViewById(R.id.imgGrowler);


        final GrowlerApp growlerApp = listaGrowlersApp.get(position);
        txtIdentificadorGrowler.setText(Integer.toString(growlerApp.getIdentificadorGrowler()));
        txtDescricaoGrowler.setText(growlerApp.getDescricaoGrowler());
        txtIndicadorGrowlerCheio.setText((growlerApp.getIndicadorGrowlerCheio()==1?"Cheio":"Vazio"));
        txtDescricaoCervejaGrowler.setText(growlerApp.getDescricaoCervejaGrowler());
        txtTemperaturaIdealGrowler.setText(Double.toString(growlerApp.getVlrTemperaturaIdeal()));
        //txtTemperaturaAtualGrowler.setText(ConsultarTemperaturaAtualGrowler(Integer.toString(growlerApp.getIdentificadorGrowler())));
        txtTemperaturaAtualGrowler.setText(Double.toString(growlerApp.getVlrTemperaturaAtual()));
        imgGrowler.setImageResource(txtDescricaoCervejaGrowler.getText().toString().equals("")? R.drawable.ic_growler_vazio:R.drawable.ic_growler_cheio);

        //Tratar visibilidade dos campos que não aparecem quando o growler está vazio
        txtDescricaoCervejaGrowler.setVisibility(txtDescricaoCervejaGrowler.getText().toString().equals("")? View.GONE: View.VISIBLE);
        txtTemperaturaIdealGrowler.setVisibility(txtDescricaoCervejaGrowler.getText().toString().equals("")? View.GONE: View.VISIBLE);
        txtLabelDescricaoCervejaGrowler.setVisibility(txtDescricaoCervejaGrowler.getText().toString().equals("")? View.GONE: View.VISIBLE);
        txtLabelTemperaturaIdeal.setVisibility(txtDescricaoCervejaGrowler.getText().toString().equals("")? View.GONE: View.VISIBLE);
        txtLabelTemperaturaAtual.setVisibility(txtDescricaoCervejaGrowler.getText().toString().equals("")? View.GONE: View.VISIBLE);
        txtTemperaturaAtualGrowler.setVisibility(txtDescricaoCervejaGrowler.getText().toString().equals("")? View.GONE: View.VISIBLE);

        return view;

    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public Object getItem(int position) {
        return listaGrowlersApp.get(position);
    }

    public  List<GrowlerApp> getListaGrowlerApp(){
        return listaGrowlersApp;
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value) {
            mSelectedItemsIds.put(position, value);
        }
        else
            mSelectedItemsIds.delete(position);
        //notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

    private String ConsultarTemperaturaAtualGrowler(String strIdGrowler) {
        String retorno = "";

        EstruturaRaizGrowler estruturaRaizGrowler = GrowlerNegocio.ConsultarGrowlerAtual(strIdGrowler);

        if (estruturaRaizGrowler.IdcErr == 0) {
            if (estruturaRaizGrowler.Dados != null) {
                retorno = estruturaRaizGrowler.Dados.Temperatura;
            }
        }

        return retorno;

    }
}
