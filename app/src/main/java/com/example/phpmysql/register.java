package com.example.phpmysql;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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

public class register extends AppCompatActivity {
    private EditText txtPw;
    private EditText txtEmail;
    private EditText txtNom;
    private EditText txtPrenom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtPw = (EditText)findViewById(R.id.txtPwd);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtNom = (EditText) findViewById(R.id.txtLn);
        txtPrenom = (EditText) findViewById(R.id.txtFn);


    }
    public void sendToLogin(View v){
        Intent intent = new Intent(register.this, login.class);
        startActivity(intent);
    }

    public void signUserIn(View v){
        String userL = this.txtNom.getText().toString();
        String userF = this.txtPrenom.getText().toString();
        String userE = this.txtEmail.getText().toString();
        String userP = this.txtPw.getText().toString();
        if(!userE.isEmpty()){
            register.dbWorker2 dbw = new register.dbWorker2(this);
            dbw.execute(userL,userF,userE,userP);
        }
        else{
            AlertDialog ad = new AlertDialog.Builder(this).create();

            ad.setMessage("Remplissez tout");
        }

    }

    public class dbWorker2 extends AsyncTask {
        private Context c;
        private AlertDialog ad;

        @Override
        protected void onPreExecute() {
            this.ad = new AlertDialog.Builder(this.c).create();
            this.ad.setTitle("Register Status");
        }

        @Override
        protected void onPostExecute(Object o) {
            this.ad.setMessage((String)o);
            this.ad.show();
        }

        public dbWorker2(Context c){
            this.c = c;
        }
        @Override
        protected Object doInBackground(Object[] param) {
            String cible = "http://192.168.32.50:80/Android2/signup.php";
            try {
                URL url = new URL(cible);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setRequestMethod("POST");

                OutputStream outs = con.getOutputStream();
                BufferedWriter bufw = new BufferedWriter(new OutputStreamWriter(outs, "utf-8"));

                String msg = URLEncoder.encode("nom", "utf-8") + "=" +
                        URLEncoder.encode((String) param[0], "utf8") + "&" +
                        URLEncoder.encode("prenom", "utf-8") + "=" +
                        URLEncoder.encode((String) param[1], "utf-8") + "&" +
                        URLEncoder.encode("email", "utf-8") + "=" +
                        URLEncoder.encode((String) param[2], "utf-8") + "&" +
                        URLEncoder.encode("pw", "utf-8") + "=" +
                        URLEncoder.encode((String) param[3], "utf-8");;

                bufw.write(msg);
                bufw.flush();
                bufw.close();
                outs.close();

                InputStream ins = con.getInputStream();
                BufferedReader bufr = new BufferedReader(new InputStreamReader(ins, "iso-8859-1"));
                String line;
                StringBuffer sbuff = new StringBuffer();

                while ((line = bufr.readLine()) != null) {
                    sbuff.append(line + "\n");
                }
                return sbuff.toString();
            } catch (Exception ex) {
                return ex.getMessage();
            }


        }
    }
}