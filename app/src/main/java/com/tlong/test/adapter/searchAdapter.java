package com.tlong.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tlong.test.R;
import com.tlong.test.model.*;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class searchAdapter extends BaseAdapter implements Filterable {
    Context context;
    private ArrayList<search> list;
    private ArrayList<search> listold;

    public searchAdapter(Context context, ArrayList<search> list, ArrayList<search> listold) {
        this.context = context;
        this.list = list;
        this.listold = listold;
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
        ImageView img;
        TextView txtgia,txtname;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.items_search,null);
            viewHolder.txtname=view.findViewById(R.id.itemsearch_txt_name);
            viewHolder.txtgia=view.findViewById(R.id.itemsearch_txt_price);
            viewHolder.img=view.findViewById(R.id.itemsearch_imgView);
            view.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder) view.getTag();
        }
        search item=list.get(i);
        viewHolder.txtname.setText(item.getName());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.txtgia.setText(decimalFormat.format(item.getPrice())+" VNĐ");
        Picasso.get().load(item.getImage()).placeholder(R.drawable.longng).error(R.drawable.vu).into(viewHolder.img);
        viewHolder.img.setScaleType(ImageView.ScaleType.FIT_XY);
        return view;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String key=constraint.toString();
                if(key.isEmpty()){
                    list=listold;
                }else {
                    ArrayList<search> mlist=new ArrayList<>();
                    for (search product:listold){
                        if(product.getName().toLowerCase().contains(key.toLowerCase())){
                            mlist.add(product);
                        }
                    }
                    list=mlist;
                }
                FilterResults filterResults=new FilterResults();
                filterResults.values=list;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list=(ArrayList<search>)results.values;
                notifyDataSetChanged();
            }
        };
    }
}
