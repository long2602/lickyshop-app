package com.tlong.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tlong.test.R;
import com.tlong.test.model.*;

import java.util.ArrayList;

public class typeAdapter  extends BaseAdapter {
    ArrayList<type> typeArrayList;
    Context context;

    public typeAdapter(ArrayList<type> typeArrayList, Context context) {
        this.typeArrayList = typeArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return typeArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return typeArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        TextView txtName;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView =inflater.inflate(R.layout.items_typeproduct,null);
            viewHolder.txtName=convertView.findViewById(R.id.itemtype_txt_name);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        type loaisp=(type)getItem(position);
        viewHolder.txtName.setText(loaisp.getTitle());
        return convertView;
    }
}
