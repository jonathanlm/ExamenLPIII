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

public class Reg extends AppCompatActivity {

    EditText editNam, editAp, editUssnew, editPassnew;
    Button btnCancelNew, btnSave;

    String url="";
    String parametros="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        editNam=(EditText)findViewById(R.id.editNam);
        editAp=(EditText)findViewById(R.id.editAp);
        editUssnew =(EditText)findViewById(R.id.editUssnew);
        editPassnew=(EditText)findViewById(R.id.editPassnew);
        btnCancelNew=(Button)findViewById(R.id.btnCancelNew);
        btnSave=(Button)findViewById(R.id.btnSave);


        btnCancelNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
          btnSave.setOnClickListener(new View.OnClickListener(){
              @Override
              public void onClick(View v){


                  ConnectivityManager connMgr=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                  NetworkInfo networkInfo=connMgr.getActiveNetworkInfo();
                  if (networkInfo!=null && networkInfo.isConnected()){
                      String nombre = editNam.getText().toString();
                      String apellidos = editAp.getText().toString();
                      String usuario = editUssnew.getText().toString();
                      String clave = editPassnew.getText().toString();

                      if(nombre.isEmpty()||apellidos.isEmpty()||usuario.isEmpty()||clave.isEmpty()){
                          Toast.makeText(getApplicationContext(),"Ningún campo puede estar vacío", Toast.LENGTH_LONG).show();

                      }else {
                          url="http://10.148.27.115:3306/login.php";
                          parametros="nombre="+nombre + "&apellidos" +apellidos + "&usuario" +usuario + "&clave" +clave;

                          new SolicitData().execute(url);
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


            editNam.setText(result);










            //    textView.setText(result);

            //editUsser.setText(result);//en el hint del layout, error o logeado...
    /*        if(result.contains("login_ok")) {   //equals //contains para traer el cont del php

                Intent openIndex = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(openIndex);//para traer el contenido del layout

            }else{

                Toast.makeText(getApplicationContext(),"El usuario o clave son incorrectos", Toast.LENGTH_LONG).show();
            }
            */
        }

    }



}
