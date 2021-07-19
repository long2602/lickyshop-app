package com.tlong.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tlong.test.R;

import java.util.ArrayList;

public class hotkeyAdapter extends BaseAdapter {
    ArrayList<String> list;
    Context context;

    public hotkeyAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        TextView txt;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.items_hotkey,null);
            viewHolder.txt=view.findViewById(R.id.itemhotkey_txt);
            view.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)view.getTag();
        }
        String temp=list.get(i);
        viewHolder.txt.setText(temp);
        return view;
    }
}
