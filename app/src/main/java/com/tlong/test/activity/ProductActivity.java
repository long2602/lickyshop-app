package com.tlong.test.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tlong.test.R;
import com.tlong.test.model.*;

import java.text.DecimalFormat;

public class ProductActivity extends AppCompatActivity {

    ImageButton imgBtnBack;
    TextView txtName,txtPrice,txtDes;
    ImageView imgProduct;
    Button btnMua,btnThem,btnAmount;
    Dialog dialog;
    LinearLayout linearSize;
    int id=0;
    String name="";
    String imei="";
    String des="";
    int idtype=0;
    double price=0;
    String hinh="";
    int idimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        AnhXa();
        //back về;
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        GetInformation();
        EventButton();
        ThemGioHang();

    }

    private void ThemGioHang() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opennewDialog();
            }
        });
    }
    private void opennewDialog() {
        dialog.setContentView(R.layout.dialog_addcart);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView NameDialog,priceDialog,size;
        Button btnMua,btnTieptuc;

        ImageView imageView=dialog.findViewById(R.id.dialog_imgView);
        btnMua=dialog.findViewById(R.id.dialog_btn_thanhtoan);
        btnTieptuc=dialog.findViewById(R.id.dialog_btn_tieptuc);
        NameDialog=dialog.findViewById(R.id.dialog_txt_name);
        priceDialog=dialog.findViewById(R.id.dialog_txt_price);
        size=dialog.findViewById(R.id.dialog_txt_size);

        NameDialog.setText(name);
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        priceDialog.setText(decimalFormat.format(price)+" VNĐ");
        Picasso.get().load(hinh).placeholder(R.drawable.longng).error(R.drawable.vu).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        btnMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.ArrayCart.add(new cart(id,name,price,hinh,1,price));
                Intent intent =new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });
        btnTieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.ArrayCart.add(new cart(id,name,price,hinh,1,price));
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    //xử lý ở lại hay thanh toán ngay
    private void EventButton() {
        btnMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.ArrayCart.size()>0){
                    boolean exist=false;
                    for (int i=0;i<MainActivity.ArrayCart.size();i++){
                        if(MainActivity.ArrayCart.get(i).getIdPro()==id){
                            MainActivity.ArrayCart.get(i).setAmount(MainActivity.ArrayCart.get(i).getAmount()+1);
                            MainActivity.ArrayCart.get(i).setTotal(price*MainActivity.ArrayCart.get(i).getAmount());
                            exist=true;
                        }
                    }
                    if(exist==false){
                        int soluong=1;
                        double Giamoi=soluong*price;
                        MainActivity.ArrayCart.add(new cart(id,name,price,hinh,soluong,Giamoi));
                    }
                }
                else {
                    int soluong=1;
                    double Giamoi=soluong*price;
                    MainActivity.ArrayCart.add(new cart(id,name,price,hinh,soluong,Giamoi));
                }
                Intent intent=new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });
    }

    //load dữ liệu sản phẩm
    private void GetInformation() {
        product product= (product) getIntent().getSerializableExtra("productDetail");
        image img=(image)getIntent().getSerializableExtra("productImage");
        id=product.getIdProduct();
        name=product.getName();
        des=product.getDescription();
        imei=product.getImei();
        idtype=product.getIdType();
        linearSize.setVisibility(View.GONE);
        price=product.getPrice();
        hinh=img.getImage();
        idimg=img.getIdImage();
        txtName.setText(name);
        txtDes.setText(des);
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        txtPrice.setText(decimalFormat.format(price)+" VNĐ");
        Picasso.get().load(hinh).placeholder(R.drawable.longng).error(R.drawable.vu).into(imgProduct);
        imgProduct.setScaleType(ImageView.ScaleType.FIT_XY);

    }

    private void  AnhXa(){
        linearSize=(LinearLayout)findViewById(R.id.product_linear_size);
        btnMua=(Button)findViewById(R.id.product_btn_muangay);
        btnThem=(Button)findViewById(R.id.product_btn_themgio);
        btnAmount=(Button)findViewById(R.id.product_btn_amount);
        btnAmount.setText(String.valueOf(MainActivity.mainAmount));
        imgBtnBack=(ImageButton)findViewById(R.id.product_ImgButton_back);
        txtName=(TextView)findViewById(R.id.product_txtView_NameShirt);
        txtPrice=(TextView)findViewById(R.id.product_txtView_gia);
        txtDes=(TextView)findViewById(R.id.product_txtView_Descript);
        imgProduct =(ImageView)findViewById(R.id.product_imgView_item);
        dialog=new Dialog(this);
    }
}