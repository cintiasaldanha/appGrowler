package com.iot.trabalho.grupo.appgrowler.Negocio;

// Telnet Client

import java.net.*;
import java.io.*;

class Requester2 {

    DataInputStream din = null;
    DataOutputStream dout = null;
    Socket soc = null;


    public void Requester2(){
    }


    public String execmdDet(String Command)  {

        String result = "";
        try {
            soc = new Socket("191.185.105.218", 2004);
            DataInputStream din = new DataInputStream(soc.getInputStream());
            DataOutputStream dout = new DataOutputStream(soc.getOutputStream());

            dout.writeUTF(Command);
            result = din.readUTF();
            return result;
        }catch (Exception ex)
        {

        }
        finally {
            try {
                if (soc!=null)
                    soc.close();
            }catch (Exception ex){}
        }
        return "";
    }

}