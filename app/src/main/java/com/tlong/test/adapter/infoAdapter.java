package com.tlong.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tlong.test.model.*;
import com.tlong.test.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class infoAdapter extends BaseAdapter {
    ArrayList<info> stringArrayList;
    Context context;

    public infoAdapter(ArrayList<info> stringArrayList, Context context) {
        this.stringArrayList = stringArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return stringArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return stringArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        TextView txtName;
        ImageView img;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView =inflater.inflate(R.layout.items_info,null);
            viewHolder.txtName=convertView.findViewById(R.id.iteminfo_txt);
            viewHolder.img=convertView.findViewById(R.id.iteminfo_img);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        info loaisp=(info) getItem(position);
        viewHolder.txtName.setText(loaisp.getName());
        viewHolder.img.setImageResource(loaisp.getHinh());
        return convertView;
    }
}
