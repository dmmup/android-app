package com.example.phpmysql;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class login extends AppCompatActivity {
    private EditText txtPw;
    private EditText txtEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtPw = (EditText)findViewById(R.id.txtPwd);
        txtEmail = (EditText) findViewById(R.id.txtEmail);

    }
    public void logUserIn(View v){
        String userE = this.txtEmail.getText().toString();
        String userP = this.txtPw.getText().toString();



        dbWorker1 dbw = new dbWorker1(this);
        dbw.execute(userE,userP);


    }

    public void sendToRegister(View v){
        Intent intent = new Intent(login.this, register.class);
        startActivity(intent);
    }

    public class dbWorker1 extends AsyncTask {
        private Context c;
        private AlertDialog ad;

        @Override
        protected void onPreExecute() {
            this.ad = new AlertDialog.Builder(this.c).create();
            this.ad.setTitle("Login Status");
        }

        @Override
        protected void onPostExecute(Object o) {
            try{
                if((String)o != "0") {
                    String[] tab = ((String) o).split("/");

                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("id_user", tab[2].toString());
                    myEdit.putString("Nom", tab[0].toString());
                    myEdit.putString("email", tab[1].toString());
                    myEdit.putString("statut", "connecter");
                    myEdit.commit();


                    Intent intent = new Intent(login.this, displayActs.class);
                    startActivity(intent);
                }
            }catch (Exception ex) {
                this.ad.setMessage("Mot de passe ou Email incorect, Veuillez Reesayer!!!");
                this.ad.show();
                }





        }

        public dbWorker1(Context c){
            this.c = c;
        }
        @Override
        protected Object doInBackground(Object[] param) {
            String cible = "http://192.168.32.50:80/Android2/login.php";
            try {
                URL url = new URL(cible);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setRequestMethod("POST");

                OutputStream outs = con.getOutputStream();
                BufferedWriter bufw = new BufferedWriter(new OutputStreamWriter(outs, "utf-8"));

                String msg = URLEncoder.encode("email", "utf-8") + "=" +
                        URLEncoder.encode((String) param[0], "utf8") + "&" +
                        URLEncoder.encode("pw", "utf-8") + "=" + URLEncoder.encode((String) param[1], "utf-8");

                bufw.write(msg);
                bufw.flush();
                bufw.close();
                outs.close();

                InputStream ins = con.getInputStream();
                BufferedReader bufr = new BufferedReader(new InputStreamReader(ins, "iso-8859-1"));
                String line;
                StringBuffer sbuff = new StringBuffer();

                while ((line = bufr.readLine()) != null) {
                    sbuff.append(line);
                }
                return sbuff.toString();
            } catch (Exception ex) {
                return ex.getMessage();
            }


        }
    }

}