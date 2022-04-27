package com.example.quanlyamnhac.nhacsi;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.quanlyamnhac.Database;
import com.example.quanlyamnhac.MainActivity;
import com.example.quanlyamnhac.R;
import com.example.quanlyamnhac.adapter.CustomAdapterNhacSi;
import com.example.quanlyamnhac.model.NhacSiModel;

import java.util.ArrayList;

public class NhacSiFragment extends Fragment {

    ArrayList<NhacSiModel> MusicianArrayList;
    Database database;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.nhacsi_fragment, container, false);
        final ListView lv_tablemusician = root.findViewById(R.id.lv_tablemusician);

        MusicianArrayList = new ArrayList<>();
        CustomAdapterNhacSi adapter = new CustomAdapterNhacSi(getContext(), R.layout.nhacsi_item_list_view, MusicianArrayList);
        lv_tablemusician.setAdapter(adapter);

        database = new Database(getContext(), "QuanLyAmNhac.sqlite", null, 1);

        database.QueryData("CREATE TABLE IF NOT EXISTS NhacSi(MaNhacSi VARCHAR(200) PRIMARY KEY NOT NULL, TenNhacSi VARCHAR(200) NOT NULL)");
        database.QueryData("CREATE TABLE IF NOT EXISTS BaiHat(MaBaiHat VARCHAR(200) PRIMARY KEY NOT NULL, TenBaiHat VARCHAR(200) NOT NULL, NamSangTac VARCHAR(200) NOT NULL, MaNhacSi VARCHAR(200) NOT NULL, FOREIGN KEY (MaNhacSi) REFERENCES NhacSi(MaNhacSi))");
        database.QueryData("CREATE TABLE IF NOT EXISTS BieuDien(Id INTEGER PRIMARY KEY autoincrement  ,MaBaiHat VARCHAR(200) NOT NULL, MaCaSi VARCHAR(200) NOT NULL ,  NgayBieuDien VARCHAR(200) , DiaDiem VARCHAR(200) , FOREIGN KEY (MaBaiHat) REFERENCES BaiHat(MaBaiHat))");
        database.QueryData("CREATE TABLE IF NOT EXISTS CaSi(MaCaSi VARCHAR(200) PRIMARY KEY NOT NULL, TenCaSi VARCHAR(200) )"); //
        Cursor data = database.GetData("SELECT * FROM NhacSi");
        MusicianArrayList.clear();
        MusicianArrayList.clear();
        while (data.moveToNext()) {
            String id = data.getString(0);
            String name = data.getString(1);

            MusicianArrayList.add(new NhacSiModel(id, name));
        }

        lv_tablemusician.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final View update_layout = inflater.inflate(R.layout.nhacsi_fragment_update, null);
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setMessage("Thực hiện thao tác");
                alert.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder fix = new AlertDialog.Builder(getContext());
                        fix.setView(update_layout);

                        EditText up_MaNS = (EditText) update_layout.findViewById(R.id.editma);
                        EditText up_tennhacsi = (EditText) update_layout.findViewById(R.id.editname);

                       Button btn_up_ns = (Button) update_layout.findViewById(R.id.btnUpdateNS);
                       Button btnBacKNS = (Button) update_layout.findViewById(R.id.btnBacKNS);
                        up_MaNS.setText(MusicianArrayList.get(position).getId());
                        up_tennhacsi.setText(MusicianArrayList.get(position).getName());


                        btnBacKNS.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent nhacsi = new Intent(getContext(), MainActivity.class);
                                nhacsi.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(nhacsi);
                            }
                        });

                        AlertDialog alertDialog = fix.create();
                        alertDialog.setCancelable(false);
                        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                        alertDialog.show();


                        btn_up_ns.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               Log.d("abc","" + position);
                                Log.d("abc","" + up_tennhacsi.getText());
                                database.QueryData("UPDATE NhacSi SET TenNhacSi  = '" + up_tennhacsi.getText().toString() + "' where MaNhacSi='" + MusicianArrayList.get(position).getId() + "'");
                                    String name = up_tennhacsi.getText().toString();
                                MusicianArrayList.set(position,new NhacSiModel(MusicianArrayList.get(position).getId(), name));
                                adapter.notifyDataSetChanged();

                                alertDialog.dismiss();



                            }
                        });

                        adapter.notifyDataSetChanged();

                    }
                });
                alert.setNegativeButton("Xoá", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        System.out.println("xoa ne !! = " + MusicianArrayList.get(position).getId());
                        database.QueryData("DELETE FROM NhacSi WHERE MaNhacSi   = '" + MusicianArrayList.get(position).getId() +"'");
                               MusicianArrayList.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                alert.show();
                return true;

            }
        });


        return root;
    }


}