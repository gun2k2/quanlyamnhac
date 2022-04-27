package com.example.quanlyamnhac.bieudien;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.quanlyamnhac.Database;
import com.example.quanlyamnhac.R;
import com.example.quanlyamnhac.adapter.CustomAdapterBieuDien;
import com.example.quanlyamnhac.model.BieuDienModel;

import java.util.ArrayList;

public class BieuDienFragment extends Fragment {

    Database database;
    ListView lvTableBieuDien;

    Spinner sp_edt_macasi;
    ArrayList sp_macasi;
    ArrayList<BieuDienModel> bieuDienArrayList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.bieudien_fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        lvTableBieuDien = root.findViewById(R.id.lv_tableBieuDien);

        Dialog dialogSelect = new Dialog(this.getContext());
        dialogSelect.setContentView(R.layout.bieudien_dialog_select);

        Dialog dialogEdit = new Dialog(this.getContext());
        dialogEdit.setContentView(R.layout.bieudien_dialog_edit);

        Dialog dialogDelete = new Dialog(this.getContext());
        dialogDelete.setContentView(R.layout.bieudien_dialog_confirm_delete);

        Button btnEdit = dialogEdit.findViewById(R.id.btn_edit);
        Button btnEditBack = dialogEdit.findViewById(R.id.btn_edit_back);
        Button btnSelectEdit = dialogSelect.findViewById(R.id.btn_select_edit);
        Button btnSelectDelete = dialogSelect.findViewById(R.id.btn_select_delete);
        Button btnNo = dialogDelete.findViewById(R.id.btn_delete_no);
        Button btnYes = dialogDelete.findViewById(R.id.btn_delete_yes);

        database = new Database(getContext(), "QuanLyAmNhac.sqlite", null, 1);

        getDataBieuDien();


        lvTableBieuDien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {
                dialogSelect.show();
                btnSelectEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogEdit.show();
                        EditText edtTencasi = dialogEdit.findViewById(R.id.edt_tencasi);
                        EditText edtNgaybd = dialogEdit.findViewById(R.id.edt_ngaybd);
                        EditText edtDiadiem = dialogEdit.findViewById(R.id.edt_diadiem);

                        sp_edt_macasi = (Spinner) dialogEdit.findViewById(R.id.sp_edt_macasi);

                        khoitao();

                        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, sp_macasi);
                        sp_edt_macasi.setAdapter(adapter);
                        sp_edt_macasi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String ma = sp_edt_macasi.getSelectedItem().toString();
                                edtTencasi.setText(selectName(ma));
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        BieuDienModel db = bieuDienArrayList.get(i);

                        int ma = db.getMaBieuDien();
                        edtTencasi.setText(db.getMaCaSi().toString());
                        edtDiadiem.setText(db.getDiaDiem().toString());
                        edtNgaybd.setText(db.getNgayBieuDien().toString());

                        btnEditBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogEdit.dismiss();
                            }
                        });

                        btnEdit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String ten = edtTencasi.getText().toString();
                                String diadiem = edtDiadiem.getText().toString();
                                String ngay = edtNgaybd.getText().toString();
                                if (ten.equals("") || diadiem.equals("") || ngay.equals("")) {
                                    Toast.makeText(getContext(), "Vui long dien thong tin day du!", Toast.LENGTH_SHORT).show();
                                } else {
                                    database.QueryData("UPDATE BieuDien SET TenCaSi = '" + ten + "' ,NgayBieuDien = '" + ngay + "' ,DiaDiem = '" + diadiem + "' WHERE Id = '" + ma + "'");
                                    getDataBieuDien();
                                    Toast.makeText(getContext(), "Chinh sua thanh cong!", Toast.LENGTH_SHORT).show();
                                }
                                dialogEdit.dismiss();
                                dialogSelect.dismiss();
                            }
                        });
                    }

                });

                btnSelectDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogDelete.show();
                        btnNo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogDelete.dismiss();
                            }
                        });
                        btnYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                BieuDienModel db = bieuDienArrayList.get(i);
                                int ma = db.getMaBieuDien();
                                System.out.println("ma la = " + ma);
                                database.QueryData("DELETE FROM BieuDien WHERE Id = '" + ma + "'");
                                getDataBieuDien();
                                Toast.makeText(getContext(), "Xoa thanh cong!", Toast.LENGTH_SHORT).show();
                                dialogDelete.dismiss();
                                dialogSelect.dismiss();
                            }
                        });
                    }
                });
                return true;
            }
        });


        return root;
    }

    public void getDataBieuDien() {
        bieuDienArrayList = new ArrayList<>();
        CustomAdapterBieuDien adapter = new CustomAdapterBieuDien(getContext(), R.layout.bieudien_item_list_view, bieuDienArrayList);
        lvTableBieuDien.setAdapter(adapter);
        Cursor dataBieuDien = database.GetData("SELECT * FROM BieuDien");
        bieuDienArrayList.clear();
        while (dataBieuDien.moveToNext()) {
            Integer id = Integer.parseInt(dataBieuDien.getString(0));
            String mabaihat = dataBieuDien.getString(1);
            String macasi = dataBieuDien.getString(2);
            String ngaybieudien = dataBieuDien.getString(3);
            String diadiem = dataBieuDien.getString(4);
            bieuDienArrayList.add(new BieuDienModel(id, mabaihat, macasi, ngaybieudien, diadiem));
        }
        adapter.notifyDataSetChanged();
    }

    public String selectName(String id) {
        Cursor dataBieuDien = database.GetData("SELECT * FROM BieuDien");
        while (dataBieuDien.moveToNext()) {
            if (dataBieuDien.getString(1).equals(id)) {
                return dataBieuDien.getString(2);
            }
        }
        return "";
    }

    private void khoitao() {
        sp_macasi = new ArrayList();
        database = new Database(getContext(), "QuanLyAmNhac.sqlite", null, 1);
        Cursor dataBieuDien = database.GetData("SELECT * FROM BieuDien");
        sp_macasi.clear();
        while (dataBieuDien.moveToNext()) {
            String macasi = dataBieuDien.getString(1);
            sp_macasi.add(macasi);
        }

    }
}