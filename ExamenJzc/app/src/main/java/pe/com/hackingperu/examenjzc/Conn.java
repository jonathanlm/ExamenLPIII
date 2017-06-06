package pe.com.hackingperu.examenjzc;

import android.provider.ContactsContract;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jonathan^ on 03/06/2017.
 */

public class Conn {
    public static String connData(String urlUsuario, String parametrosUsuario){
        URL url;
        HttpURLConnection connection=null;

        try {
            url=new URL(urlUsuario);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Lenght", ""+Integer.toString(parametrosUsuario.getBytes().length));
            connection.setRequestProperty("Content-Language", "sp-ES");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);


            /*
            DataOutputStream dataOutputStream=new DataOutputStream(connection.getOutputStream());
            dataOutputStream.writeBytes(parametrosUsuario);
            dataOutputStream.flush();
            dataOutputStream.close();
            */
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            outputStreamWriter.write(parametrosUsuario);
            outputStreamWriter.flush();

            InputStream inputStream=connection.getInputStream();

            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String conn;
            StringBuffer ask=new StringBuffer();
            while ((conn=bufferedReader.readLine())!=null){
                ask.append(conn);
                ask.append('\r');

            }
            bufferedReader.close();
            return ask.toString();
        }catch (Exception error){
            return null;



        }finally {
            if (connection!=null){

                connection.disconnect();
            }
        }

    }

}
