package com.tlong.test.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.tlong.test.adapter.*;
import com.tlong.test.R;
import com.tlong.test.model.user;
import com.tlong.test.ultil.UserLocalStore;

import java.text.DecimalFormat;

public class Cart2Activity extends AppCompatActivity {
    static TextView txtAmount,txtPrice;
    TextView txtNotice;
    ImageView imgNotice;
    Button btnDatHang,btnhome,btncart,btnDangNhap;
    SwipeMenuListView listView;
    giohangAdapter adapter;
    Button btnLogin;
    UserLocalStore userLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super .onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart2);
        userLocalStore  = new UserLocalStore(this);
        MainActivity.CheckCart=0;
        AnhXa();
        EventClick();
        CheckData();
        EventUltil();

    }

    private void EventClick() {
        //chuyển qua trang đặt hàng
        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), PaymentActivity.class);
                startActivity(intent);
            }
        });
        //chuyển trang cá nhân
        DangNhap();
        //chuyển trang home
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void DangNhap() {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            com.tlong.test.model.user user = userLocalStore.getLoggedInUser();
            @Override
            public void onClick(View view) {
                if(user!=null){
                    MainActivity.IDCUS=user.id;
                    Intent intent=new Intent(getApplicationContext(), InfoActivity.class);
                    startActivity(intent);
//                    overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                }
                else{
                    Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
//                    overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                }
            }
        });
    }
    private void CheckData(){
        if(MainActivity.ArrayCart.size()<=0){
            adapter.notifyDataSetChanged();
            txtNotice.setVisibility((View.VISIBLE));
            imgNotice.setVisibility((View.VISIBLE));
            listView.setVisibility(View.INVISIBLE);
        }
        else{
            txtNotice.setVisibility((View.INVISIBLE));
            imgNotice.setVisibility((View.INVISIBLE));
            listView.setVisibility(View.VISIBLE);
        }
    }
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
        txtPrice.setText(decimalFormat.format(tongtien)+" VNĐ");
        txtAmount.setText(String.valueOf(soluong));

    }

    private void AnhXa() {
        btnhome=(Button)findViewById(R.id.home_btn_home);
        btnDangNhap=(Button)findViewById(R.id.home_btn_account);
        txtAmount=(TextView)findViewById(R.id.cart2_txtview_soluong);
        txtNotice=(TextView)findViewById(R.id.cart2_txt_Chuacosanpham);
        txtPrice=(TextView)findViewById(R.id.cart2_txt_price);
        imgNotice=(ImageView)findViewById(R.id.cart2_img_chuacosp);
        btnDatHang=(Button)findViewById(R.id.cart2_btn_Dathang);
        listView=(SwipeMenuListView) findViewById(R.id.cart2_listView);
        adapter=new giohangAdapter(Cart2Activity.this,MainActivity.ArrayCart);
        listView.setAdapter(adapter);

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
        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open
                        MainActivity.ArrayCart.remove(position);
                        adapter.notifyDataSetChanged();
                        EventUltil();
                        if(MainActivity.ArrayCart.size()<=0){
                            txtNotice.setVisibility((View.VISIBLE));
                            imgNotice.setVisibility((View.VISIBLE));
                            listView.setVisibility(View.INVISIBLE);
                        }
                        else{
                            txtNotice.setVisibility((View.INVISIBLE));
                            imgNotice.setVisibility((View.INVISIBLE));
                            listView.setVisibility(View.VISIBLE);
                        }
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }
}