package com.example.phpmysql;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ActivitesAdapter extends ArrayAdapter<Activites> {



    private Context ctx;
    private int res;
    private ArrayList<Activites> mesAct;

    public ActivitesAdapter(Context context, int resource, ArrayList<Activites> list){
        super(context, resource, list);

        this.ctx = context;
        this.res = resource;
      //  this.mesAct = new ArrayList<>();
        this.mesAct = (ArrayList<Activites>) list;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Activites act = this.mesAct.get(position);
        LayoutInflater inflater= LayoutInflater.from(ctx);

        convertView = inflater.inflate(this.res,parent,false);
        TextView nom =(TextView)convertView.findViewById(R.id.lblNom);
        TextView lieu =(TextView)convertView.findViewById(R.id.lblLieu);
        TextView desc =(TextView)convertView.findViewById(R.id.lblDesc);
        TextView prix =(TextView)convertView.findViewById(R.id.lblPrix);
        TextView dd = (TextView)convertView.findViewById(R.id.lblDate);

        nom.setText("Titre: "+act.getNom());
        lieu.setText("Lieu: " + act.getLieu());
        desc.setText("Description: " + act.getDesc());
        prix.setText("Prix: " + act.getPrix()+"$");
        dd.setText("Date: Du " + act.getDd() + " au " + act.getDf());



        return convertView;

    }


}