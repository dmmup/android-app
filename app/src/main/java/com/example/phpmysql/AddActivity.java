package com.example.phpmysql;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    DatePickerDialog picker;
    EditText txtDd;
    EditText txtDf;
    Button btnADD;
    EditText txtLieu, txtTitre, txtPrix, txtDesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        txtDd=(EditText) findViewById(R.id.txtDd);
        txtDd.setInputType(InputType.TYPE_NULL);
        txtDf=(EditText) findViewById(R.id.txtDf);
        txtDf.setInputType(InputType.TYPE_NULL);
        txtTitre = (EditText) findViewById(R.id.txtTitre);
        txtLieu = (EditText) findViewById(R.id.txtLieu);
        txtPrix = (EditText) findViewById(R.id.txtPrix);
        txtDesc = (EditText) findViewById(R.id.txtDesc);
        // Affiche du calendar date debut ########################################
        txtDd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                txtDd.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
// Affiche du calendar date debut ########################################
        txtDf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                txtDf.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });


    }
    public void onAddNewActivity(View v){
        String titre = this.txtTitre.getText().toString();
        String lieu = this.txtLieu.getText().toString();
        String descrip = this.txtDesc.getText().toString();
        String prix = this.txtPrix.getText().toString();
        String dateD = this.txtDd.getText().toString();
        String dateF = this.txtDf.getText().toString();
        if(!titre.isEmpty() && !lieu.isEmpty() && !descrip.isEmpty()){
            AddActivity.dbWorker2 dbw = new AddActivity.dbWorker2(this);
            dbw.execute(titre,lieu,prix,dateD,dateF,descrip);
            txtTitre.setText("");
            txtLieu.setText("");
            txtDesc.setText("");
            txtPrix.setText("");
            txtDd.setText("");
            txtDf.setText("");
        }
        else{
            AlertDialog ad = new AlertDialog.Builder(this).create();

            ad.setMessage("Remplissez tout");
        }
    }

    public void onBackToDisplay(View v){
        Intent intent = new Intent(AddActivity.this, displayActs.class);
        startActivity(intent);
    }

    // *********************************CLASS ASCYNTASK ***************************************************************
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
            String cible = "http://192.168.32.50:80/Android2/addActivity.php";
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
                        URLEncoder.encode("lieu", "utf-8") + "=" +
                        URLEncoder.encode((String) param[1], "utf-8") + "&" +
                        URLEncoder.encode("prix", "utf-8") + "=" +
                        URLEncoder.encode((String) param[2], "utf-8") + "&" +
                        URLEncoder.encode("dd", "utf-8") + "=" +
                        URLEncoder.encode((String) param[3], "utf-8") + "&" +
                        URLEncoder.encode("df", "utf-8") + "=" +
                        URLEncoder.encode((String) param[4], "utf-8") + "&" +
                        URLEncoder.encode("desc", "utf-8") + "=" +
                        URLEncoder.encode((String) param[5], "utf-8");;

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