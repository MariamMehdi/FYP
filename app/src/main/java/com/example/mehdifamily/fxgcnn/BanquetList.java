package com.example.mehdifamily.fxgcnn;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BanquetList extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Upload> banquetArrayList;

    public BanquetList(Context con,ArrayList<Upload> banquetArrayList)
    {
        context=con;
        layoutInflater = LayoutInflater.from(context);
        this.banquetArrayList=banquetArrayList;
    }

    @Override
    public int getCount() {
        return banquetArrayList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_banquet_list, null, false);
            holder = new ViewHolder();

            holder.textViewName = (TextView) convertView.findViewById(R.id.banquetName);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Upload  banquet = banquetArrayList.get(position);


        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return banquetArrayList.get(position);
    }

    public class ViewHolder {
        TextView textViewName;

    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
