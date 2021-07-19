package com.tlong.test.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.tlong.test.R;
import com.tlong.test.adapter.*;

import java.text.DecimalFormat;

public class CartActivity extends AppCompatActivity {
    ImageButton btnBack;
    SwipeMenuListView cartListview;
    static TextView txtAmount;
    static TextView txtTotal;
    TextView txtNotice;
    Button btnDathang;
    ImageView imgNotice;
    giohangAdapter giohangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        MainActivity.CheckCart=1;
        AnhXa();
        EventClick();
        CheckData();
        EventUltil();
        Dathang();

    }

    private void EventClick() {
        //trở về
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Dathang() {
        btnDathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), PaymentActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        });
    }


    //hàm cập nhật
    public static void EventUltil() {
        double tongtien=0;
        int soluong=0;
        for(int i=0;i<MainActivity.ArrayCart.size();i++){
            tongtien+=MainActivity.ArrayCart.get(i).getTotal();
            soluong+=MainActivity.ArrayCart.get(i).getAmount();
        }
        MainActivity.mainAmount=soluong;
        MainActivity.mainTotal=tongtien;
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
       txtTotal.setText(decimalFormat.format(tongtien)+" VNĐ");
       txtAmount.setText(String.valueOf(soluong));
    }
    //hàm kiểm tra giỏ hàng trống hay không
    private void CheckData(){
        if(MainActivity.ArrayCart.size()<=0){
            giohangAdapter.notifyDataSetChanged();
            txtNotice.setVisibility((View.VISIBLE));
            imgNotice.setVisibility((View.VISIBLE));
            cartListview.setVisibility(View.INVISIBLE);
        }
        else{
            txtNotice.setVisibility((View.INVISIBLE));
            imgNotice.setVisibility((View.INVISIBLE));
            cartListview.setVisibility(View.VISIBLE);
        }
    }

    public void AnhXa(){
        btnBack=(ImageButton)findViewById(R.id.img_btn_back);
        cartListview=(SwipeMenuListView) findViewById(R.id.cart_listView);
        txtAmount=(TextView) findViewById(R.id.cart_txtview_soluong);
        txtTotal=(TextView)findViewById(R.id.cart_txt_price);
        btnDathang=(Button)findViewById(R.id.cart_btn_Dathang);
        txtNotice=(TextView)findViewById(R.id.cart_txt_Chuacosanpham);
        imgNotice=(ImageView)findViewById(R.id.cart_img_chuacosp);
        giohangAdapter=new giohangAdapter(CartActivity.this,MainActivity.ArrayCart);
        cartListview.setAdapter(giohangAdapter);

        //test
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(255, 51,
                        51)));
                // set item width
                deleteItem.setWidth(400);
//                // set item title
//                deleteItem.setTitle("Xóa");
//                // set item title fontsize
//                deleteItem.setTitleSize(18);
//                // set item title font color
//                deleteItem.setTitleColor(Color.WHITE);
                // set a icon
                deleteItem.setIcon(R.drawable.icon_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

// set creator
        cartListview.setMenuCreator(creator);

        cartListview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open
                        MainActivity.ArrayCart.remove(position);
                        giohangAdapter.notifyDataSetChanged();
                        EventUltil();
                        if(MainActivity.ArrayCart.size()<=0){
                            txtNotice.setVisibility((View.VISIBLE));
                            imgNotice.setVisibility((View.VISIBLE));
                            cartListview.setVisibility(View.INVISIBLE);
                        }
                        else{
                            txtNotice.setVisibility((View.INVISIBLE));
                            imgNotice.setVisibility((View.INVISIBLE));
                            cartListview.setVisibility(View.VISIBLE);
                        }
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }
}