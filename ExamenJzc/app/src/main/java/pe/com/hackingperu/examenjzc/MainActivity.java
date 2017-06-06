package pe.com.hackingperu.examenjzc;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {



    EditText editUsser, editPass;
    Button btnLogin, btnRegister;
    String url="";
    String parametros="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUsser = (EditText)findViewById(R.id.editUsser);
        editPass = (EditText)findViewById(R.id.editPass);

        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnRegister=(Button)findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openRegister=new Intent(MainActivity.this, Reg.class);
                startActivity(openRegister);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connMgr=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo=connMgr.getActiveNetworkInfo();
                if (networkInfo!=null && networkInfo.isConnected()){
                    //Trayendo data =)
                    String usser=editUsser.getText().toString();
                    String pass=editPass.getText().toString();

                    if(usser.isEmpty()|| pass.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Ningún campo puede estar vacío", Toast.LENGTH_LONG).show();

                    }else {
                        url="http://10.148.27.115:3306/login.php";
                        parametros="usser=" + usser + "&pass=" + pass;
                        new SolicitData().execute(url);//inicialmente dentro de este ()estaba (stringUrl)
                    }



                }else {
                    //Error en el dispositivo...
                    Toast.makeText(getApplicationContext(),"Ninguna conexión fue detectada", Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    private class SolicitData extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... urls){
            //Parámetros que vienen de execute() el stringUrl de arriba dentro del IF
            //try{
                return Conn.connData(urls[0],parametros);
            //}catch (IOException e){
            //    return "Incapaz de recuperar la página web. La URL podría ser inválida =( ";
            //}
        }
        @Override
        protected void onPostExecute(String result){
        //    textView.setText(result);

            //editUsser.setText(result);//en el hint del layout, error o logeado...
            if(result.contains("login_ok")) {   //equals //contains para traer el cont del php

                Intent openIndex = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(openIndex);//para traer el contenido del layout

            }else{

                Toast.makeText(getApplicationContext(),"El usuario o clave son incorrectos", Toast.LENGTH_LONG).show();


            }
            }

        }
    @Override
    protected  void onPause(){
        super.onPause();
        finish();//al momento de colocar atras cierre la app


    }

    }


