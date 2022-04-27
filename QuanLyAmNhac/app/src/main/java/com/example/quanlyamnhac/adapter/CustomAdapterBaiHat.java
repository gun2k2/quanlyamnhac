package com.example.quanlyamnhac.adapter;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlyamnhac.Database;
import com.example.quanlyamnhac.R;
import com.example.quanlyamnhac.model.BaiHatModel;


import java.util.ArrayList;

public class CustomAdapterBaiHat extends ArrayAdapter<BaiHatModel> implements Filterable {
    Context context;
    int resource;
    ArrayList<BaiHatModel> data;
    ArrayList<BaiHatModel> dataFilter;
    Database database;

    @Override
    public int getCount() {
        return data.size();
    }

    public class ViewHolder {
        TextView mbh;
        TextView tbh;
        TextView nst;
        TextView tns;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(resource, null);
            // Locate the TextViews in listview_item.xml
            holder.mbh = (TextView) convertView.findViewById(R.id.bh_mabaihat);
            holder.tbh = (TextView) convertView.findViewById(R.id.bh_tenbaihat);
            holder.nst = (TextView) convertView.findViewById(R.id.bh_namsangtac);
            holder.tns =  convertView.findViewById(R.id.bh_tennhacsi);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mbh.setText(data.get(position).getMaBaiHat());
        holder.tbh.setText(data.get(position).getTenBaiHat());
        holder.nst.setText(data.get(position).getNamSangTac());
        System.out.println("ma nhac si = " + data.get(position).getManhacsi());
        database = new Database(getContext(), "QuanLyAmNhac.sqlite", null, 1);
        if(data.get(position).getManhacsi()!= null){
            Cursor datatenns = database.GetData("SELECT * FROM NhacSi WHERE MaNhacSi = '" + data.get(position).getManhacsi() + "' ");
            while (datatenns.moveToNext()) {

                holder.tns.setText(datatenns.getString(1));
            }
        }




        return convertView;
    }

    public CustomAdapterBaiHat(Context context, int resource, ArrayList data) {
        super(context, resource);
        this.context = context;
        this.data = data;
        this.resource = resource;
    }

    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<BaiHatModel> results = new ArrayList<BaiHatModel>();
                if (dataFilter == null)
                    dataFilter = data;
                if (constraint != null) {
                    if (dataFilter != null && dataFilter.size() > 0) {
                        for (final BaiHatModel g : dataFilter) {
                            if (g.getTenBaiHat().toLowerCase().contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                data = (ArrayList<BaiHatModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
