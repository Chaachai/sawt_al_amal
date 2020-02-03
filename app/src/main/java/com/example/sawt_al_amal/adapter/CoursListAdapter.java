package com.example.sawt_al_amal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sawt_al_amal.R;
import com.example.sawt_al_amal.bean.Cours;

import java.util.ArrayList;
import java.util.List;

public class CoursListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Cours> coursList;

    public CoursListAdapter(Context context, int layout, List<Cours> coursList) {
        this.context = context;
        this.layout = layout;
        this.coursList = coursList;
    }

    @Override
    public int getCount() {
        return coursList.size();
    }

    @Override
    public Object getItem(int position) {
        return coursList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView txtNom;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {


        View row = view;
        ViewHolder holder = new ViewHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtNom = row.findViewById(R.id.nomCours);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Cours cours = coursList.get(position);

        holder.txtNom.setText(cours.getNom());

        return row;
    }
}
