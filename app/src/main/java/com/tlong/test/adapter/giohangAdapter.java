package com.tlong.test.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tlong.test.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.tlong.test.activity.Cart2Activity;
import com.tlong.test.activity.CartActivity;
import com.tlong.test.activity.MainActivity;
import com.tlong.test.model.*;
public class giohangAdapter extends BaseAdapter {
    Context context;
    ArrayList<cart> cartArrayList;

    public giohangAdapter(Context context, ArrayList<cart> cartArrayList) {
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
        public TextView txtName, txtPrice,txtAmount;
        public ImageButton btnAdd,btnRemove;
        public ImageView img;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.items_cart,null);
            viewHolder.txtName=view.findViewById(R.id.itemcart_txt_name);
            viewHolder.txtAmount=view.findViewById(R.id.itemcart_txt_amount);
            viewHolder.txtPrice=view.findViewById(R.id.itemcart_txt_price);
            viewHolder.btnAdd=view.findViewById(R.id.itemcart_btn_add);
            viewHolder.btnRemove=view.findViewById(R.id.itemcart_btn_remove);
            viewHolder.img=view.findViewById(R.id.itemcart_imgView);
            view.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder) view.getTag();
        }
        cart giohang=(cart)getItem(i);
        viewHolder.txtName.setText(giohang.getNamePro());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.txtPrice.setText(decimalFormat.format(giohang.getPrice())+" VNĐ");
        Picasso.get().load(giohang.getImg()).placeholder(R.drawable.longng).error(R.drawable.vu).into(viewHolder.img);
        viewHolder.img.setScaleType(ImageView.ScaleType.FIT_XY);
        viewHolder.txtAmount.setText(String.valueOf(giohang.getAmount()));
//        int sl=Integer.parseInt(viewHolder.txtAmount.getText().toString());
        final ViewHolder finalViewHolder=viewHolder;
        viewHolder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slm=Integer.parseInt(finalViewHolder.txtAmount.getText().toString())+1;
                finalViewHolder.txtAmount.setText(String.valueOf(slm));
                int slht=MainActivity.ArrayCart.get(i).getAmount();
                double giaht=MainActivity.ArrayCart.get(i).getTotal();
                MainActivity.ArrayCart.get(i).setAmount(slm);
                double giamoinhat=(giaht*slm)/slht;
                MainActivity.ArrayCart.get(i).setTotal(giamoinhat);
                if(MainActivity.CheckCart==0){
                    Cart2Activity.EventUltil();
                }
                else if (MainActivity.CheckCart==1){
                    CartActivity.EventUltil();
                }
            }
        });
        viewHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slm=Integer.parseInt(finalViewHolder.txtAmount.getText().toString())-1;
                finalViewHolder.txtAmount.setText(String.valueOf(slm));
                int slht=MainActivity.ArrayCart.get(i).getAmount();
                double giaht=MainActivity.ArrayCart.get(i).getTotal();
                MainActivity.ArrayCart.get(i).setAmount(slm);
                double giamoinhat=(giaht*slm)/slht;
                MainActivity.ArrayCart.get(i).setTotal(giamoinhat);
                if(MainActivity.CheckCart==0){
                    Cart2Activity.EventUltil();
                }
                else if (MainActivity.CheckCart==1){
                    CartActivity.EventUltil();
                }
                if(slm<=0){
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setTitle("Xóa sản phẩm");
                    builder.setMessage("Bạn có chắc muốn xóa sản phẩm này không?");
                    builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            MainActivity.ArrayCart.remove(i);
                            notifyDataSetChanged();
                            if(MainActivity.CheckCart==0){
                                Cart2Activity.EventUltil();
                            }
                            else if (MainActivity.CheckCart==1){
                                CartActivity.EventUltil();
                            }
                        }
                    });
                    builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            int slm=1;
                            int slht=1;
                            double giaht=MainActivity.ArrayCart.get(i).getPrice();
                            MainActivity.ArrayCart.get(i).setAmount(slm);
                            double giamoinhat=(giaht*slm)/slht;
                            MainActivity.ArrayCart.get(i).setTotal(giamoinhat);
                            notifyDataSetChanged();
                            if(MainActivity.CheckCart==0){
                                Cart2Activity.EventUltil();
                            }
                            else if (MainActivity.CheckCart==1){
                                CartActivity.EventUltil();
                            }
                        }
                    });
                    AlertDialog alert=builder.create();
                    alert.show();
                }
            }
        });
        return view;
    }
}
