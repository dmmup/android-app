package com.example.phpmysql;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class displayOneAct extends AppCompatActivity {
    private TextView lblTitre,lblLieu, lblPrix, lblNote;
    private ArrayList<Users> users;
    private EditText txtNote;
    private ListView lstUsers;
    private UserAdapter adp;
    private Button btnNote, btnSubscribe ;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_one);
        this.lstUsers = (ListView)findViewById(R.id.lstUsers);
        this.users = new ArrayList<>();

        txtNote = (EditText) findViewById(R.id.txtNote);
        btnSubscribe = (Button) findViewById(R.id.btnSub);
        lblTitre = (TextView) findViewById(R.id.lblTitre);
        lblLieu = (TextView) findViewById(R.id.lblLieu);
        lblPrix = (TextView) findViewById(R.id.lblPrix);
        lblNote = (TextView) findViewById(R.id.lblNote);
        Intent intent = getIntent();
        String nom =intent.getStringExtra("nom");
        String lieu =intent.getStringExtra("lieu");
        String prix =intent.getStringExtra("prix");
        String id = intent.getStringExtra("id");


        lblTitre.setText("Titre : " + nom.toUpperCase());
        lblLieu.setText("Lieu " + lieu.toUpperCase());
        lblPrix.setText(("Prix : " + prix + "$"));

        try{
            dbWorkerUser dbwU = new dbWorkerUser(this);
            dbwU.execute(id);
        }catch (Exception ex){
            String m = ex.toString();
            int d = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(getApplicationContext(),m,d);
        }



    }
    public void onRegisterToAct(View v){
        try{
            Intent intent = getIntent();
            String id_act = intent.getStringExtra("id");

            SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

            String s1 = sh.getString("id_user", "");
            dbWorker2 dbwU = new dbWorker2(this);
            dbwU.execute(s1,id_act,"0");

        }catch (Exception ex){
            String m = ex.toString();
            int d = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(getApplicationContext(),m,d);
        }

    }
    public void onNoter(View v){
        try{
            Intent intent = getIntent();
            String id_act = intent.getStringExtra("id");

            SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

            String s1 = sh.getString("id_user", "");
            String n = txtNote.getText().toString();
            dbWorker2 dbwU = new dbWorker2(this);
            dbwU.execute(s1,id_act,n);

        }catch (Exception ex){
            String m = ex.toString();
            int d = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(getApplicationContext(),m,d);
        }
    }

    public void onBack(View view){
        Intent intent = new Intent(displayOneAct.this, displayActs.class);
        startActivity(intent);
    }
// **********************************************************************************************************
    public class dbWorkerUser extends AsyncTask {
        private Context c;
        private AlertDialog ad;
        public dbWorkerUser(Context c){
            this.c = c;
        }
        @Override
        protected void onPreExecute() {
            this.ad = new AlertDialog.Builder(this.c).create();
            this.ad.setTitle("");
        }
        @Override
        protected Object doInBackground(Object[] param) {
            String cible = "http://192.168.32.50:80/Android2/displayUsers.php";
            try {
                URL url = new URL(cible);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setRequestMethod("POST");

                OutputStream outs = con.getOutputStream();
                BufferedWriter bufw = new BufferedWriter(new OutputStreamWriter(outs, "utf-8"));

                String msg = URLEncoder.encode("act_id", "utf-8") + "=" +
                        URLEncoder.encode((String) param[0], "utf8") ;

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


        @Override
        protected void onPostExecute(Object o) {
//            this.ad.setMessage((String)o);
//            this.ad.show();

            String[] tab = ((String)o).split("/");
            int i = 0;
            users = new ArrayList<>();

            while (i < tab.length){
                Users unUser = new Users();
                unUser.setNom(tab[i]);
                i++;
                unUser.setPrenom(tab[i]);
                i++;
                unUser.setId_User(tab[i]);
                i++;
                unUser.setEmail(tab[i]);
                i++;
                unUser.setNote(tab[i]);
                i++;
                users.add(unUser);

            }


            adp = new UserAdapter(getApplicationContext(),R.layout.user, users);
            lstUsers.setAdapter(adp);




        }

    }

    // **********************************************************************************************************
    public class dbWorker2 extends AsyncTask {
        private Context c;
        private android.app.AlertDialog ad;

        @Override
        protected void onPreExecute() {
            this.ad = new android.app.AlertDialog.Builder(this.c).create();
            this.ad.setTitle("Subscribe Status");
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
            String cible = "http://192.168.32.50:80/Android2/subscribeToAct.php";
            try {
                URL url = new URL(cible);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setRequestMethod("POST");

                OutputStream outs = con.getOutputStream();
                BufferedWriter bufw = new BufferedWriter(new OutputStreamWriter(outs, "utf-8"));

                String msg = URLEncoder.encode("id_user", "utf-8") + "=" +
                        URLEncoder.encode((String) param[0], "utf8") + "&" +
                        URLEncoder.encode("id_act", "utf-8") + "=" +
                        URLEncoder.encode((String) param[1], "utf-8") + "&" +
                        URLEncoder.encode("note", "utf-8") + "=" +
                        URLEncoder.encode((String) param[2], "utf-8");

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