package com.example.quanlyamnhac.baihat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.quanlyamnhac.Database;

import com.example.quanlyamnhac.R;
import com.example.quanlyamnhac.adapter.CustomAdapterBaiHat;

import com.example.quanlyamnhac.model.BaiHatModel;
import com.example.quanlyamnhac.model.NhacSiModel;


import java.util.ArrayList;

public class BaiHatFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    Database database;

    ArrayList<BaiHatModel> baiHatArrayList;

    ListView lv_tableBaiHat;

    SwipeRefreshLayout swiperefresh_bh;
    SearchView search_bh;
    String mans;
    ArrayList<NhacSiModel> arrayNhacSi;
    ArrayList sp_manhacsi;
    CustomAdapterBaiHat adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.baihat_fragment, container, false);
        lv_tableBaiHat = root.findViewById(R.id.lv_tableBaiHat);
        lv_tableBaiHat.setLongClickable(true);
        search_bh = root.findViewById(R.id.search_bh);
        arrayNhacSi = new ArrayList<>();
        setupSearchView();

        swiperefresh_bh = (SwipeRefreshLayout) root.findViewById(R.id.swiperefresh_bh);
        database = new Database(getContext(), "QuanLyAmNhac.sqlite", null, 1);

        baiHatArrayList = new ArrayList<>();
        adapter = new CustomAdapterBaiHat(getContext(), R.layout.baihat_item_list_view, baiHatArrayList);
        lv_tableBaiHat.setAdapter(adapter);

        //database.QueryData("CREATE TABLE IF NOT EXISTS BaiHat(Id INTEGER PRIMARY KEY AUTOINCREMENT, MaBaiHat VARCHAR(200), TenBaiHat VARCHAR(200), NamSangTac VARCHAR(200), MaNhacSi VARCHAR(200), foreign key (MaNhacSi) references NhacSi)");

        Cursor dataBaiHat = database.GetData("SELECT * FROM BaiHat ");
        baiHatArrayList.clear();

        while (dataBaiHat.moveToNext()) {
            String mabaihat = dataBaiHat.getString(0);
            String tenbaihat = dataBaiHat.getString(1);
            String namsangtac = dataBaiHat.getString(2);
            String mans = dataBaiHat.getString(3);
            baiHatArrayList.add(new BaiHatModel(mabaihat, tenbaihat, namsangtac, mans));
        }

        adapter.notifyDataSetChanged();
        swiperefresh_bh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                baiHatArrayList = new ArrayList<>();
                adapter = new CustomAdapterBaiHat(getContext(), R.layout.baihat_item_list_view, baiHatArrayList);
                lv_tableBaiHat.setAdapter(adapter);

                Cursor dataBaiHat = database.GetData("SELECT * FROM BaiHat ");
                baiHatArrayList.clear();
                while (dataBaiHat.moveToNext()) {
                    String mabaihat = dataBaiHat.getString(0);
                    String tenbaihat = dataBaiHat.getString(1);
                    String namsangtac = dataBaiHat.getString(2);
                    String mans = dataBaiHat.getString(3);
                    baiHatArrayList.add(new BaiHatModel(mabaihat, tenbaihat, namsangtac, mans));
                }

                adapter.notifyDataSetChanged();
                swiperefresh_bh.setRefreshing(false);
            }
        });


        lv_tableBaiHat.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final View update_layout = inflater.inflate(R.layout.baihat_fragment_update, null);
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setMessage("Thực hiện thao tác");
                alert.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder fix = new AlertDialog.Builder(getContext());
                        fix.setView(update_layout);

                        Spinner up_mns = update_layout.findViewById(R.id.up_mns);
                        EditText up_mbh = (EditText) update_layout.findViewById(R.id.up_mbh);
                        EditText up_tns = (EditText) update_layout.findViewById(R.id.up_tns);
                        EditText up_tbh = (EditText) update_layout.findViewById(R.id.up_tbh);
                        EditText up_nst = (EditText) update_layout.findViewById(R.id.up_nst);
                        Button btn_up_bh = (Button) update_layout.findViewById(R.id.btn_up_bh);

                        Button btn_up_back = (Button) update_layout.findViewById(R.id.btn_up_back);

                        btn_up_back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent baihat = new Intent(getContext(), BaiHatActivity.class);
                                baihat.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(baihat);
                            }
                        });
                        up_mbh.setText(baiHatArrayList.get(position).getMaBaiHat());
                        up_tbh.setText(baiHatArrayList.get(position).getTenBaiHat());
                        up_nst.setText(baiHatArrayList.get(position).getNamSangTac());
                      //  up_tns.setText(baiHatArrayList.get(position).getMaBaiHat());

                        sp_manhacsi = new ArrayList<>();
                        database = new Database(getContext(), "QuanLyAmNhac.sqlite", null, 1);
                        Cursor nhacsi = database.GetData("SELECT * FROM NhacSi");
                        sp_manhacsi.clear();
                        while (nhacsi.moveToNext()) {

                            String ma = nhacsi.getString(0);

                            sp_manhacsi.add(ma);
                        }
                        ArrayAdapter adapter = new ArrayAdapter(root.getContext(), android.R.layout.simple_spinner_item, sp_manhacsi);

                        up_mns.setAdapter(adapter);
                        up_mns.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                mans = up_mns.getSelectedItem().toString();
                                up_tns.setText(selectName(mans));
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });



                        AlertDialog alertDialog = fix.create();
                        alertDialog.setCancelable(false);
                        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                        alertDialog.show();


                        btn_up_bh.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                database.QueryData("UPDATE BaiHat SET " +
                                        " TenBaiHat = '" + up_tbh.getText().toString() + "'" +
                                        ", NamSangTac = '" + up_nst.getText().toString() + "'" +
                                        ", MaNhacSi = '" + mans + "'" +
                                        " WHERE MaBaiHat = '" + up_mbh.getText().toString() + "'");
                                baiHatArrayList.set(position,new BaiHatModel(baiHatArrayList.get(position).getMaBaiHat(), up_tbh.getText().toString(),up_nst.getText().toString(),mans));

                                alertDialog.dismiss();

                            }
                        });

                        adapter.notifyDataSetChanged();


                    }
                });
                alert.setNegativeButton("Xoá", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        database.QueryData("DELETE FROM BaiHat WHERE MaBaiHat = '" + baiHatArrayList.get(position).getMaBaiHat() + "'");
                        baiHatArrayList.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                alert.show();
                return true;

            }
        });


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
    private void setupSearchView() {
        search_bh.setIconifiedByDefault(false);
        search_bh.setOnQueryTextListener(this);
        search_bh.setSubmitButtonEnabled(true);
        search_bh.setQueryHint("Search Here");
    }

    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            lv_tableBaiHat.clearTextFilter();
        } else {
            lv_tableBaiHat.setFilterText(newText.toLowerCase());
        }
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public void onRefresh() {

    }

}