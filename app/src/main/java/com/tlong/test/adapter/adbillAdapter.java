package com.tlong.test.adapter;

import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tlong.test.R;
import com.tlong.test.model.*;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class adbillAdapter extends BaseAdapter {
    Context context;
    ArrayList<bill> billArrayList;
    ArrayList<customer> customerArrayList;

    public adbillAdapter(Context context, ArrayList<bill> billArrayList, ArrayList<customer> customerArrayList) {
        this.context = context;
        this.billArrayList = billArrayList;
        this.customerArrayList = customerArrayList;
    }

    @Override
    public int getCount() {
        if(customerArrayList!=null)
            return customerArrayList.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return customerArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        public TextView txtName, txtdate,txttotal;
        public ImageView img;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.items_adbill,null);
            viewHolder.txtName=view.findViewById(R.id.itemadbill_txt_user);
            viewHolder.txtdate=view.findViewById(R.id.itemadbill_txt_date);
            viewHolder.txttotal=view.findViewById(R.id.itemadbill_txt_gia);
            viewHolder.img=view.findViewById(R.id.itemadbill_img);
            view.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder) view.getTag();
        }
        customer cus=customerArrayList.get(i);
        bill bill=billArrayList.get(i);
        viewHolder.txtName.setText(cus.getName());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.txttotal.setText(decimalFormat.format(bill.getTotal())+" VNĐ");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(bill.getBDateTime());
        viewHolder.txtdate.setText(strDate);
        viewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }
}
