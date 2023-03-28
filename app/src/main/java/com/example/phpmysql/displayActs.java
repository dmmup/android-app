package com.example.phpmysql;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class displayActs extends AppCompatActivity {
    private TextView lbl, lblPro;
    private ArrayList<Activites> acts;
    private ListView lstActivite;
    private ActivitesAdapter adp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_acts);

        this.lstActivite = (ListView) findViewById(R.id.lstAct);
        this.acts = new ArrayList<>();
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        String s1 = sh.getString("Nom", "");
        lblPro = (TextView) findViewById(R.id.lblProfil);
        lblPro.setText("Mr, Mme " + s1);



        try{
            dbWorkerDisplay dbw = new dbWorkerDisplay(this);
            dbw.execute();

//
        this.adp = new ActivitesAdapter(getApplicationContext(),R.layout.mon, acts);
//
        this.lstActivite.setAdapter(adp);

        }catch (Exception ex){
            String m = ex.toString();
            int d = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(getApplicationContext(),m,d);
        }
        lstActivite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(displayActs.this, displayOneAct.class);
//                int index = parent.getSelectedItemPosition();
 //               String m = (String) parent.getItemAtPosition(position);
//                String message = "abc";
                Activites a = adp.getItem(position);
                String item_id = a.getId_Act();
                String item_nom = a.getNom();
                String item_lieu = a.getLieu();
                String item_desc = a.getDesc();
                String item_prix = a.getPrix();
                String item_dd = a.getDd();
                String item_df = a.getDf();
                intent.putExtra("id", item_id);
                intent.putExtra("nom", item_nom);
                intent.putExtra("lieu", item_lieu);
                intent.putExtra("desc", item_desc);
                intent.putExtra("prix", item_prix);
                intent.putExtra("dd", item_dd);
                intent.putExtra("df", item_df);



                startActivity(intent);
            }
        });

    }
    public void onAddActivity(View v){
        Intent intent = new Intent(displayActs.this, AddActivity.class);
        startActivity(intent);
    }


    public void onSignOut(View v){
        Intent intent = new Intent(displayActs.this, login.class);
        startActivity(intent);
    }

    // **************************************************************************
    public class dbWorkerDisplay extends AsyncTask {
        private Context c;
        private AlertDialog ad;



        public dbWorkerDisplay(Context c){
            this.c = c;
        }
        @Override
        protected void onPreExecute() {
            this.ad = new AlertDialog.Builder(this.c).create();
            this.ad.setTitle("Login Status");
        }
        @Override
        protected Object doInBackground(Object[] param) {
            String cible = "http://192.168.32.50:80/Android2/displayAct.php";
            try {
                URL url = new URL(cible);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setRequestMethod("POST");

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
            try{
                String[] tab = ((String)o).split("/");
                int i = 0;
                acts  = new ArrayList<>();
                while (i < tab.length-1) {
                    Activites uneAct = new Activites();
                    uneAct.setId_Act(tab[i]);
                    i++;
                    uneAct.setNom(tab[i]);
                    i++;
                    uneAct.setLieu(tab[i]);
                    i++;
                    uneAct.setDesc(tab[i]);
                    i++;
                    uneAct.setPrix(tab[i]);
                    i++;
                    uneAct.setDd(tab[i]);
                    i++;
                    uneAct.setDf(tab[i]);
                    i++;
                    acts.add(uneAct);


                }
                adp = new ActivitesAdapter(getApplicationContext(),R.layout.mon, acts);
                lstActivite.setAdapter(adp);
            }catch (Exception ex){
                ex.getMessage();
            }
        }

    }

    // **************************************************************************************



}