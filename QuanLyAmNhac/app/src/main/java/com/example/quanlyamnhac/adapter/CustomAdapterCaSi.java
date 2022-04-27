package com.example.quanlyamnhac.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlyamnhac.R;
import com.example.quanlyamnhac.model.CaSiModel;


import java.util.ArrayList;


public class CustomAdapterCaSi extends ArrayAdapter<CaSiModel> {
    Context context;
    int resource;
    ArrayList data;

    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int i, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(resource, null);


        TextView cs_id = convertView.findViewById(R.id.cs_id);
        TextView cs_name = convertView.findViewById(R.id.cs_name);

        CaSiModel casiDatabase = (CaSiModel) data.get(i);
        cs_id.setText(casiDatabase.getMaCaSi());
        cs_name.setText(casiDatabase.getTenCaSi());
        return  convertView;



    }

    public CustomAdapterCaSi(Context context, int resource, ArrayList data) {
        super(context, resource);
        this.context = context;
        this.data = data;
        this.resource = resource;
    }
}
