package com.iot.trabalho.grupo.appgrowler.Negocio;

/**
 * Created by ruben on 01/10/2016.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Requester{
    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;
    //Requester(){}
    void run()
    {
        try{
            String cmd = "";
            BufferedReader inr = new BufferedReader(new InputStreamReader(System.in));
            String retorno = "";

            do{
                System.out.println("");
                System.out.print("cmd>");
                cmd = inr.readLine();
                retorno = execmdDet(cmd);
                //retorno = execmd(retorno);
                System.out.println("retorno>"+retorno);

            }
            while(!message.equals("sair"));
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }

    }

    public String execmd(String cmd)
    {
        String retorno = "";


        try{
            String str = cmd;

            //1. Criando Socket
            String endereco = "191.185.105.218";
            Integer porta = 2004;
            requestSocket = new Socket(endereco, porta);

            //2. Recuperar Input e Output streams
            out = new ObjectOutputStream(requestSocket.getOutputStream());

            out.flush();

            in = new ObjectInputStream(requestSocket.getInputStream());

            //3: Comunicando com o servidor
            try{
                message = (String)in.readObject();
                System.out.println("server>" + message);
                message = str;
                sendMessage(message);
                retorno = (String)in.readObject();
            }catch(ClassNotFoundException classNot){
                System.err.println("data received in unknown format");
            }
        }
        catch(UnknownHostException unknownHost){
            retorno = "Servidor nao conhecido";
        }
        catch(IOException ioException){
            //ioException.printStackTrace();
        }
        finally{
            //4: Closing connection
            try{

                in.close();
                in = null;
                out.close();
                out = null;
                requestSocket.close();
                requestSocket=null;
                System.out.println("Conexao fechada.");

            }
            catch(IOException ioException){
                //ioException.printStackTrace();
            }
        }
        return retorno;
    }

    public String execmdDet(String cmd)
    {
        String retorno = "";


        try{
            String str = cmd;

            //1. Criando Socket
            String endereco = "191.185.105.218";
            Integer porta = 2004;
            requestSocket = new Socket(endereco, porta);

            //System.out.println("Conectando a "+endereco + " na porta " + porta.toString() + "...");

            //2. get Input and Output streams
            out = new ObjectOutputStream(requestSocket.getOutputStream());

            out.flush();

            in = new ObjectInputStream(requestSocket.getInputStream());

            //3: Communicating with the server
            try{
                message = (String)in.readObject();
                System.out.println("server>" + message);
                message = str;
                sendMessage(message);
                retorno = (String)in.readObject();
            }catch(ClassNotFoundException classNot){
                System.err.println("data received in unknown format");
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        finally{
            //4: Closing connection
            try{

                in.close();
                in = null;
                out.close();
                out = null;
                requestSocket.close();
                requestSocket=null;
                System.out.println("Conexao fechada.");

            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return retorno;
    }

    void sendMessage(String msg)
    {
        try{
            System.out.println("enviando...>" + msg);
            out.writeObject(msg);
            out.flush();
            System.out.println("enviado>" + msg);
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
    }

    void sendMessageS(String msg)
    {
        try{
            out.writeObject(msg);
            out.flush();
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
    }



    public static void main(String args[])
    {
        Requester client = new Requester();
        client.run();
    }
}