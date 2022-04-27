package com.example.quanlyamnhac.bieudien;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.quanlyamnhac.Database;
import com.example.quanlyamnhac.R;

import java.util.ArrayList;

public class AddBieuDienFragment extends Fragment {

    Context context;
    BieuDienFragment home;
    Database database;
    ArrayList sp_mabh;
    ArrayList sp_macs;
    Button btnInsert;
    EditText edt_ins_tenbh, edt_ins_diadiem, edt_ins_ngaybd,edt_ins_tencs;
    Spinner sp_ins_mabh,sp_ins_macs;
    String macs,mabh;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.bieudien_fragment_insert, container, false);

        btnInsert = root.findViewById(R.id.insert_thongtin);

        sp_ins_macs = (Spinner) root.findViewById(R.id.insert_macs);
        edt_ins_tencs = root.findViewById(R.id.insert_tencs);

        sp_ins_mabh = (Spinner) root.findViewById(R.id.insert_mabh);
        edt_ins_tenbh = root.findViewById(R.id.insert_tenbh);

        edt_ins_diadiem = root.findViewById(R.id.insert_diadiem);
        edt_ins_ngaybd = root.findViewById(R.id.insert_ngaybd);
        khoitao();

        ArrayAdapter adaptercs = new ArrayAdapter(root.getContext(), android.R.layout.simple_spinner_item, sp_macs);
        sp_ins_macs.setAdapter(adaptercs);

        sp_ins_macs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                macs = sp_ins_macs.getSelectedItem().toString();
                edt_ins_tencs.setText(selectNameCS(macs));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter adapterbh = new ArrayAdapter(root.getContext(), android.R.layout.simple_spinner_item, sp_mabh);
        sp_ins_mabh.setAdapter(adapterbh);

        sp_ins_mabh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mabh = sp_ins_mabh.getSelectedItem().toString();
                edt_ins_tenbh.setText(selectNameBH(mabh));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ngay = edt_ins_ngaybd.getText().toString();
                String diadiem = edt_ins_diadiem.getText().toString();

                if (ngay.equals("") || diadiem.equals("") || edt_ins_tenbh.equals("") || edt_ins_tencs.equals("")) {
                    Toast.makeText(getContext(), "Vui long nhap thong tin day du!", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("ma bh = "+ mabh);
                    System.out.println("ma cs = "+ macs);
                    database.QueryData("INSERT INTO BieuDien (MaBaiHat,MaCaSi,NgayBieuDien,DiaDiem) VALUES ('" + mabh + "', '" + macs + "', '" + ngay + "', '" + diadiem + "');");
                    Toast.makeText(getContext(), "Them thanh cong!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    public String selectNameBH(String id) {
        Cursor databh = database.GetData("SELECT * FROM BaiHat where MaBaiHat = '"+ id +"'");
        while (databh.moveToNext()) {
                Log.d("abcdef", databh.getString(1));
                return databh.getString(1);
        }
        return "";
    }

    public String selectNameCS(String id) {
        Cursor datacs = database.GetData("SELECT * FROM CaSi where MaCaSi = '"+ id +"'");
        while (datacs.moveToNext()) {
            Log.d("abcdef", datacs.getString(1));
            return datacs.getString(1);
        }
        return "";
    }


    private void khoitao() {
        sp_mabh = new ArrayList();
        database = new Database(getContext(), "QuanLyAmNhac.sqlite", null, 1);

        Cursor databh = database.GetData("SELECT * FROM BaiHat");
        sp_mabh.clear();
        while (databh.moveToNext()) {
            String mabh = databh.getString(0);
            sp_mabh.add(mabh);
        }

        sp_macs = new ArrayList();
        Cursor datacs = database.GetData("SELECT * FROM CaSi");
        sp_macs.clear();
        while (datacs.moveToNext()) {
            String macs = datacs.getString(0);
            sp_macs.add(macs);
        }

    }

}