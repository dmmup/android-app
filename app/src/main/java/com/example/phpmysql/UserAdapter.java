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

public class UserAdapter extends ArrayAdapter<Users> {

    private Context ctx;
    private int res;
    private ArrayList<Users> lesUsers;

    public UserAdapter(@NonNull Context context, int resource, @NonNull List<Users> objects, Context ctx, int res, ArrayList<Users> lesUsers) {
        super(context, resource, objects);
        this.ctx = ctx;
        this.res = res;
        this.lesUsers = lesUsers;
    }
    public UserAdapter(Context context, int resource, ArrayList<Users> list){
        super(context,resource,list);
        this.ctx = context;
        this.res = resource;
        this.lesUsers = (ArrayList<Users>) list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Users us = this.lesUsers.get(position);
        LayoutInflater inflater= LayoutInflater.from(ctx);

        convertView = inflater.inflate(this.res,parent,false);
        TextView nom = (TextView) convertView.findViewById(R.id.lblNom);
        TextView prenom = (TextView) convertView.findViewById(R.id.lblPrenom);
        TextView note = (TextView) convertView.findViewById(R.id.lblNote);

        nom.setText("Prenom: "+us.getPrenom());
        prenom.setText("Nom: "+us.getNom());
        note.setText("Note: " + us.getNote()+" /10");

        return convertView;
    }
}
