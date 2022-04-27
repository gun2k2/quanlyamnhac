package com.example.quanlyamnhac.baihat;

import static android.R.*;
import static android.R.layout.*;

import android.R.layout;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.quanlyamnhac.Database;
import com.example.quanlyamnhac.MainActivity;
import com.example.quanlyamnhac.R;
import com.example.quanlyamnhac.R.id;
import com.example.quanlyamnhac.adapter.CustomAdapterNhacSi;
import com.example.quanlyamnhac.model.NhacSiModel;

import java.util.ArrayList;

public class AddBaiHatFragment extends Fragment implements OnClickListener {

    Database database;
    Spinner add_mans;
    EditText  add_mbh, add_tbh, add_nst,tenns;
    String mans;
    Button btn_add_bh;

    //ArrayList<NhacSiModel> arrayNhacSi;
    ArrayList sp_manhacsi;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.baihat_fragment_insert, container, false);

        add_mans = (Spinner) root.findViewById(id.add_mans);
        tenns = root.findViewById(id.add_tenns);
        add_mbh = root.findViewById(id.add_mbh);
        add_tbh = root.findViewById(id.add_tbh);
        add_nst = root.findViewById(id.add_nst);

        btn_add_bh = root.findViewById(id.btn_add_bh);
        sp_manhacsi = new ArrayList<>();
        database = new Database(getContext(), "QuanLyAmNhac.sqlite", null, 1);
        Cursor nhacsi = database.GetData("SELECT * FROM NhacSi");
        sp_manhacsi.clear();
        while (nhacsi.moveToNext()) {

            String ma = nhacsi.getString(0);

            sp_manhacsi.add(ma);
        }


        ArrayAdapter adapter = new ArrayAdapter(root.getContext(), android.R.layout.simple_spinner_item, sp_manhacsi);
        add_mans.setAdapter(adapter);

        add_mans.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                System.out.println("test id  ne =" + add_mans.getSelectedItem().toString());

                mans = add_mans.getSelectedItem().toString();
                tenns.setText(selectName(mans));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /*Cursor macasi = database.GetData("SELECT MaCaSi FROM BieuDien WHERE '" + textView.getText().toString() + "' = TenCaSi");
        while (macasi.moveToNext()) {

            add_mcs.setText(macasi.getString(0));
        }*/
        btn_add_bh.setOnClickListener(this);
        return root;
    }
    public String selectName(String id) {
        Cursor datans = database.GetData("SELECT * FROM NhacSi where MaNhacSi = '"+ id +"'");
        while (datans.moveToNext()) {
            Log.d("abcdef", datans.getString(1));
            return datans.getString(1);
        }
        return "";
    }
    @Override
    public void onClick(View v) {

        Cursor mbh = database.GetData("SELECT MaBaiHat FROM BaiHat WHERE '" + add_mbh.getText().toString() + "' = MaBaiHat");

        if (add_mbh.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Mã bài hát không được trống", Toast.LENGTH_LONG).show();
        } else if (add_tbh.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Tên bài hát không được trống", Toast.LENGTH_LONG).show();
        } else if (add_nst.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Năm sáng tác không được trống", Toast.LENGTH_LONG).show();
        } else if (mbh.moveToNext()) {
            Toast.makeText(getContext(), "Mã bài hát đã tồn tại", Toast.LENGTH_LONG).show();
        } else {

            database.QueryData("INSERT INTO BaiHat (MaBaiHat,TenBaiHat, NamSangTac, MaNhacSi)" +
                    "VALUES ('" + add_mbh.getText().toString() +
                    "', '" + add_tbh.getText().toString() +
                    "', '" + add_nst.getText().toString() +
                    "', '" + mans +
                    "')");

            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_LONG).show();
            Intent baihat = new Intent(getContext(), BaiHatActivity.class);
            baihat.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(baihat);

        }

    }

}