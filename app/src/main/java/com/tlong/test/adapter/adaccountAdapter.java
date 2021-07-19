package com.tlong.test.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tlong.test.R;
import com.tlong.test.activity.*;
import com.tlong.test.activity.Cart2Activity;
import com.tlong.test.activity.CartActivity;
import com.tlong.test.activity.MainActivity;
import com.tlong.test.model.account;
import com.tlong.test.model.cart;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class adaccountAdapter extends BaseAdapter {
    Context context;
    ArrayList<account> cartArrayList;

    public adaccountAdapter(Context context, ArrayList<account> cartArrayList) {
        this.context = context;
        this.cartArrayList = cartArrayList;
    }

    @Override
    public int getCount() {
        if(cartArrayList!=null)
            return cartArrayList.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return cartArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        public TextView txtName, txtpass;
        public ImageView img,imgedit,imgdelete;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.items_account,null);
            viewHolder.txtName=view.findViewById(R.id.itemaccount_txt_user);
            viewHolder.txtpass=view.findViewById(R.id.itemaccounut_edit_pass);
            viewHolder.img=view.findViewById(R.id.itemaccount_imgview_hide);
            viewHolder.imgedit=view.findViewById(R.id.itemaccount_imgview_edit);
            viewHolder.imgdelete=view.findViewById(R.id.itemaccount_imgview_delete);
            view.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder) view.getTag();
        }
        account giohang=(account)getItem(i);
        viewHolder.txtName.setText(giohang.getUsername());
        viewHolder.txtpass.setText(giohang.getPass());
        final EditText editpass=(EditText) viewHolder.txtpass;
        viewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editpass.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        });
        viewHolder.imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        viewHolder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }
}
