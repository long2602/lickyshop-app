package com.tlong.test.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.tlong.test.R;
import com.tlong.test.activity.Cart2Activity;
import com.tlong.test.activity.CartActivity;
import com.tlong.test.activity.EditInfoActivity;
import com.tlong.test.activity.InfoActivity;
import com.tlong.test.activity.MainActivity;
import com.tlong.test.model.*;
import com.tlong.test.ultil.Server;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class adProductAdapter extends BaseAdapter {
    Context context;
    ArrayList<product> productArrayList;
    ArrayList<image>imageArrayList;
    ArrayList<type>typeArrayList;
    ArrayList<size>sizeArrayList;

    public adProductAdapter(Context context, ArrayList<product> productArrayList, ArrayList<image> imageArrayList, ArrayList<type> typeArrayList, ArrayList<size> sizeArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
        this.imageArrayList = imageArrayList;
        this.typeArrayList = typeArrayList;
        this.sizeArrayList = sizeArrayList;
    }

    @Override
    public int getCount() {
        if(productArrayList!=null)
            return productArrayList.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return productArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        public TextView txtName, txtdes,txtprice,txtsl,txtId;
        public ImageView imgedit,imgdelete,img;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.items_adminproduct,null);
            viewHolder.txtName=view.findViewById(R.id.itemadminproduct_txt_name);
            viewHolder.txtdes=view.findViewById(R.id.itemadminproduct_txt_des);
            viewHolder.txtId=view.findViewById(R.id.itemadminproduct_txt_id);
            viewHolder.txtprice=view.findViewById(R.id.itemadminproduct_txt_gia);
            viewHolder.txtsl=view.findViewById(R.id.itemadminproduct_txt_sl);
            viewHolder.img=view.findViewById(R.id.itemadminproduct_img);
            viewHolder.imgedit=view.findViewById(R.id.itemaccount_imgview_edit);
            viewHolder.imgdelete=view.findViewById(R.id.itemaccount_imgview_delete);
            view.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder) view.getTag();
        }
        product product=productArrayList.get(i);
        size size=sizeArrayList.get(i);
        image img=imageArrayList.get(i);
        type type=typeArrayList.get(i);
        viewHolder.txtId.setText(String.valueOf(product.getIdProduct()));
        viewHolder.txtName.setText(product.getName());
        viewHolder.txtdes.setText(product.getDescription());
        viewHolder.txtdes.setMaxLines(1);
        viewHolder.txtsl.setText("Số lượng: "+String.valueOf(size.getAmount()));
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.txtprice.setText(decimalFormat.format(product.getPrice())+" VNĐ");
        Picasso.get().load(img.getImage()).placeholder(R.drawable.longng).error(R.drawable.vu).into(viewHolder.img);
        viewHolder.img.setScaleType(ImageView.ScaleType.FIT_XY);

        viewHolder.imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        viewHolder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này không?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        XoaSanpham(product.IdProduct);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {

                    }
                });
                AlertDialog alert=builder.create();
                alert.show();
            }
        });
        return view;
    }

    private void XoaSanpham(int i) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.LinkDeleteProduct, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("1")){
                    Toast.makeText(context.getApplicationContext(), "Xóa sản phẩm thành công",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(context.getApplicationContext(), "Xóa sản phẩm không thành công",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params=new HashMap<>();
                params.put("idproduct",String.valueOf(i));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

}
