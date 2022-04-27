package com.example.quanlyamnhac.adapter;


import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlyamnhac.Database;
import com.example.quanlyamnhac.R;
import com.example.quanlyamnhac.model.BieuDienModel;

import java.util.ArrayList;

public class CustomAdapterBieuDien extends ArrayAdapter<BieuDienModel> {
    Context context;
    int resource;
    ArrayList data;
    Database database;
    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int i, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(resource, null);


        TextView tvMaBD = convertView.findViewById(R.id.tv_mabieudien);
        TextView tvTenCaSi = convertView.findViewById(R.id.tv_tencasi);
        TextView tvBaiHat = convertView.findViewById(R.id.tv_tenbaihat);
        TextView tvNgayBieuDien = convertView.findViewById(R.id.tv_ngaybieudien);
        TextView tvDiaDiem = convertView.findViewById(R.id.tv_diadiem);

        BieuDienModel bieuDienModel = (BieuDienModel) data.get(i);

       tvMaBD.setText(Integer.toString(bieuDienModel.getMaBieuDien()));
        System.out.println("ma bieu dien la = " + Integer.toString(bieuDienModel.getMaBieuDien()));
        database = new Database(getContext(), "QuanLyAmNhac.sqlite", null, 1);
        tvTenCaSi.setText(selectNameCS(bieuDienModel.getMaCaSi()));
        tvBaiHat.setText(selectNameBH(bieuDienModel.getMaBaiHat()));


        tvNgayBieuDien.setText(bieuDienModel.getNgayBieuDien());
        tvDiaDiem.setText(bieuDienModel.getDiaDiem());
        return convertView;
    }
    public String selectNameCS(String id) {
        Cursor datacs = database.GetData("SELECT * FROM CaSi where MaCaSi = '"+ id +"'");
        while (datacs.moveToNext()) {
            Log.d("abcdef", datacs.getString(1));
            return datacs.getString(1);
        }
        return "";
    }
    public String selectNameBH(String id) {
        Cursor databh = database.GetData("SELECT * FROM BaiHat where MaBaiHat = '"+ id +"'");
        while (databh.moveToNext()) {
            Log.d("abcdef", databh.getString(1));
            return databh.getString(1);
        }
        return "";
    }

    public CustomAdapterBieuDien(Context context, int resource, ArrayList data) {
        super(context, resource);
        this.context = context;
        this.data = data;
        this.resource = resource;
    }
}

