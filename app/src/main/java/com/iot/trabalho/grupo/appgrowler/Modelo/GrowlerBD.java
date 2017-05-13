package com.iot.trabalho.grupo.appgrowler.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.iot.trabalho.grupo.appgrowler.Negocio.GrowlerNegocio;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cintia on 19/10/2016.
 */
public class GrowlerBD extends SQLiteOpenHelper {
    public static String NOME_BD = "bd_growler";
    public static int VERSAO = 1;

    public GrowlerBD(Context context) {
        super(context, NOME_BD, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS growler (" +
                " idtGrowler integer PRIMARY KEY NOT NULL ," + " dscGrowler varchar(45) NOT NULL," +
                " dscCerveja varchar(45) NULL," +
                " vlrTemperaturaIdeal real NULL," +
                " idcAlarmeTemperatura integer NULL," +
                " idcGrowlerCheio integer NULL," +
                " vlrTemperaturaAtual real NULL" +");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean inserirGrowlerBD(int intIdentificadorGrowler, String strNomeGrowler,
                                    String strNomeCerveja, double vlrTemperaturaIdeal ,
                                    int intIdcAlarmeTemperatura, int intIdcGrowlerCheio ){

        SQLiteDatabase sqlDB = getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("idtGrowler ", intIdentificadorGrowler);
        valores.put("dscGrowler", strNomeGrowler);
        valores.put("dscCerveja", strNomeCerveja);
        valores.put("vlrTemperaturaIdeal", vlrTemperaturaIdeal);
        valores.put("idcAlarmeTemperatura",intIdcAlarmeTemperatura);
        valores.put("idcGrowlerCheio",intIdcGrowlerCheio);
        long result = sqlDB.insert("growler", null, valores);
        sqlDB.close();
        if (result!=-1) {
            return true;
        }
        return false;
    }

    public List<GrowlerApp> getListaGrowlerBD() {
        SQLiteDatabase sqlDB = getReadableDatabase();
        //List<String> growlers = new ArrayList<String>();
        List<GrowlerApp> growlers = new ArrayList<GrowlerApp>();
        Cursor cursor = sqlDB.query("growler", new String[]{"idtGrowler", "dscGrowler", "dscCerveja",
                                     "vlrTemperaturaIdeal", "idcAlarmeTemperatura", "idcGrowlerCheio",
                                      "vlrTemperaturaAtual"},
                                      null, null, null, null, null);

        while (cursor.moveToNext()) {

            growlers.add(new GrowlerApp(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),
                         Double.parseDouble(cursor.getString(3)),Integer.parseInt(cursor.getString(4)),
                         Integer.parseInt(cursor.getString(5)),(cursor.getString(6)!=null?Double.parseDouble(cursor.getString(6)):0)));

        }
        //Fecha o cursor
        cursor.close();
        sqlDB.close();

        return growlers;
    }

    public GrowlerApp ObterGrowlerApp(int idtGrowler) {
        SQLiteDatabase sqlDB = getReadableDatabase();

        String selectQuery = "SELECT idtGrowler, dscGrowler, dscCerveja, vlrTemperaturaIdeal, idcAlarmeTemperatura, idcGrowlerCheio, vlrTemperaturaAtual "+
                             "FROM growler WHERE idtGrowler="+ Integer.toString(idtGrowler);
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        GrowlerApp obj = null;

        if (cursor.moveToFirst()) {
            obj = new GrowlerApp(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),
                    Double.parseDouble(cursor.getString(3)),Integer.parseInt(cursor.getString(4)),
                    Integer.parseInt(cursor.getString(5)),(cursor.getString(6)!=null?Double.parseDouble(cursor.getString(6)):0));
        }

        cursor.close();
        sqlDB.close();

        return obj;
    }

    public boolean alterarGrowlerBD(GrowlerApp growlerApp){

       // msg = validar(pessoa,"A");
       // if (!msg.isEmpty())
       //     return false;

        SQLiteDatabase sqlDB = getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put("idtGrowler ", growlerApp.getIdentificadorGrowler());
        valores.put("dscGrowler", growlerApp.getDescricaoGrowler());
        valores.put("dscCerveja", growlerApp.getDescricaoCervejaGrowler());
        valores.put("vlrTemperaturaIdeal", growlerApp.getVlrTemperaturaIdeal());
        valores.put("idcAlarmeTemperatura",growlerApp.getIndicadorAlarmeTemperatura());
        valores.put("idcGrowlerCheio", growlerApp.getIndicadorGrowlerCheio());

        int result = sqlDB.update("growler", valores, "idtGrowler=?", new String[]{Integer.toString(growlerApp.getIdentificadorGrowler())});
        sqlDB.close();
        if (result>0) {
            //msg= ctx.getString(R.string.Pessoa_msg_Alteracao_Sucesso);
            return true;
        }
        return false;
    }
    public boolean excluirGrowlerBDPorId(int idtGrowler){

        SQLiteDatabase sqlDB = getWritableDatabase();
        int result = sqlDB.delete("growler", "idtGrowler=?", new String[]{Integer.toString(idtGrowler)});
        sqlDB.close();
        if (result > 0) {
            //msg=ctx.getString(R.string.Pessoa_msg_Exclusao_Sucesso);
            return true;
        }else {
            return false;
        }
    }
    public boolean atualizarTemperaturas(){
        EstruturaRaizGrowlers Growlers;

        try {
            //Busca lista de growlers cadastrados e temperaturas atuais
             Growlers = GrowlerNegocio.ConsultarListaGrowlers();
        }
        catch (Exception e){
            return false;
        }

        //Prepara base para escrita
        SQLiteDatabase sqlDB = getWritableDatabase();
        List<Growler> lst=null;

        if (Growlers != null){
            if (Growlers.IdcErr==0){
                if (Growlers.Dados != null) {
                    lst = Growlers.Dados.ListaGrowlers;
                    if (lst.size()>0) {
                        //Para cada growler da lista, atualiza sua temperatura atual na base de dados
                        for (int i = (lst.size() - 1); i >= 0; i--) {
                            ContentValues valores = new ContentValues();
                            valores.put("vlrTemperaturaAtual", lst.get(i).Temperatura);

                            int result = sqlDB.update("growler", valores, "idtGrowler=?", new String[]{lst.get(i).Id});
                            //sqlDB.close();
                            if (result>0) {
                                Log.i("atualizarTemperaturas:","Temperatura do Growler " + lst.get(i).Id + " atualizada" );
                            }

                        }
                    }
                }
            }

        }

        sqlDB.close();

        return true;
    }

}

/*
*
  * public String msg="";
    private Context ctx;
  *
  *   private String validar(Pessoa pessoa, String Acao)
    {
        if (pessoa.cpf.isEmpty())
            return ctx.getString(R.string.Pessoa_msg_CPF_Obrigatorio);
        else if (!utilitarios.CPF.isCPF(pessoa.cpf))
            return ctx.getString(R.string.Pessoa_msg_CPF_Invalido);//CPF invalido
        else if (pessoa.Nome.isEmpty())
            return ctx.getString(R.string.Pessoa_msg_Nome_Obrigatorio);
        else if (pessoa.Email.isEmpty())
            return ctx.getString(R.string.Pessoa_msg_email_Obrigatorio);

        if (Acao.equals("I")) {
            if (existe(pessoa.cpf))
                return ctx.getString(R.string.Pessoa_msg_CPF_Ja_Cadastrado);
        }

        return  "";

    }

    public boolean existe(String strCPF)
    {
        SQLiteDatabase sqlDB = getReadableDatabase();

        String selectQuery = "SELECT DISTINCT 1 FROM pessoa_tbl WHERE cpf='"+strCPF+"'";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        Integer ret = null;

        if (cursor.moveToFirst()) {
            ret = cursor.getInt(0);
        }

        cursor.close();
        sqlDB.close();

        return (ret!=null && ret.equals(1));
    }

*
* */