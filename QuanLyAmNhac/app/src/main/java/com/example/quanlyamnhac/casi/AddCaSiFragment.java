package com.example.quanlyamnhac.casi;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quanlyamnhac.Database;
import com.example.quanlyamnhac.MainActivity;
import com.example.quanlyamnhac.R;


public class AddCaSiFragment extends Fragment {



    Button btnReturn, btnInsert;
    EditText editname, editma;
    Database database;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ca_si_insert, container, false);

        btnReturn = root.findViewById(R.id.btnReturn);
        btnInsert = root.findViewById(R.id.btnInsert);
        editname = root.findViewById(R.id.insertname);
        editma = root.findViewById(R.id.insertma);

        database = new Database(getContext(), "QuanLyAmNhac.sqlite", null, 1);


        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent casi = new Intent(getContext(), CaSiActivity.class);
                casi.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(casi);
// back
            }
        });
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editname.getText().toString();
                String ma = editma.getText().toString();
                if (name.equals("")) {
                    Toast.makeText(getContext(), "Vui long nhap ten", Toast.LENGTH_LONG).show();
                }
                if (ma.equals("")) {
                    Toast.makeText(getContext(), "Vui long nhap ma nhac si", Toast.LENGTH_LONG).show();
                } else {
                    // database.QueryData("INSERT INTO Musician values ( null, '"+ name +" )");
                    database.QueryData("INSERT INTO CaSi VALUES('"+ma+"','" + name + " ')");


                    Toast.makeText(getContext(), "Đã Thêm", Toast.LENGTH_LONG).show();
                    Intent casi = new Intent(getContext(), CaSiActivity.class);
                    casi.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(casi);


                }

            }
        });


        return root;
    }
    }
