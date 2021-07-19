
package com.tlong.test.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.tlong.test.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import com.tlong.test.ultil.*;

import com.tlong.test.model.*;
import com.tlong.test.adapter.*;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipperFlasale;
    ImageButton ImgAdd, imgTop,imgJacket,imgBot,imgItem;
    DrawerLayout drawerLayout;
    ArrayList<type> typeArrayList;
    typeAdapter typeAdapter;
    ListView mainMenu;
    ImageView imgSearch;
    int IdType;
    String Title="";
    Button btnGioHang,btnDangNhap;
    Dialog dialog;
    ArrayList<image> imageArrayList;
    ArrayList<product> newproductArrayList;
    newAdapter newAdapter;
    RecyclerView NewrecyclerView;
    newAdapter.RecyclerViewClickListener listener;

    public static ArrayList<cart> ArrayCart;

    //biến check đang ở cart hay cart1
    public static int CheckCart=0;
    static double mainTotal=0;
    static int mainAmount=0;
    static int IDCUS;

    UserLocalStore userLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userLocalStore  = new UserLocalStore(this);
        AndXa();
        EventClicl();
        opennewDialog();
        if (CheckConnect.isNetworkAvailable(getApplicationContext())){
            ActionViewFlip();
            ActionBar();
            getDuLieuLoaiSanPham();
            getDuLieuSPMoiNhat();
            CatchOnItemListView();
            DangNhap();
        }
        else {
            CheckConnect.ShowToast_Short(getApplicationContext(),"Bạn hãy kt kết nối");
            finish();
        }
    }

    private void EventClicl() {
        //chuyển trang search sản phẩm
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),SearchActivity.class);
                startActivity(intent);
            }
        });
        //chuyển trang áo
        imgTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, CategoryTopActivity.class);
                intent.putExtra("IdType",1);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        });
        //chuyển trang áo khoác
        imgJacket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, CategoryOuterwearActivity.class);
                intent.putExtra("IdType",2);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        });
        //chuyển trang quần
        imgBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, CategoryBottomActivity.class);
                intent.putExtra("IdType",3);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        });
        //chuyển trang phụ kiện
        imgItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, CategoryAccessoriesActivity.class);
                intent.putExtra("IdType",4);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        });
        //chuyển trang giỏ hàng
        btnGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), Cart2Activity.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        });
        //chuyển trang tài khoản
    }

    //Hàm xử lý đăng nhập
    private void DangNhap() {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            user user = userLocalStore.getLoggedInUser();
            @Override
            public void onClick(View view) {

                if(user!=null){
                    IDCUS=user.id;
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
    //Chạy dialog giới thiệu team licky ở ngoài
    private void opennewDialog() {
        dialog.setContentView(R.layout.dialog_team);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        ImageView btn=dialog.findViewById(R.id.dialogteam_close);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    //menu thanh bar
    private void CatchOnItemListView() {
        mainMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        if(CheckConnect.isNetworkAvailable(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            CheckConnect.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra kết nối internet");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if(CheckConnect.isNetworkAvailable(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this, CategoryTopActivity.class);
                            intent.putExtra("IdType",typeArrayList.get(i).getIdType());
                            startActivity(intent);
//                            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                        }
                        else {
                            CheckConnect.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra kết nối internet");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if(CheckConnect.isNetworkAvailable(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this, CategoryOuterwearActivity.class);
                            intent.putExtra("IdType",typeArrayList.get(i).getIdType());
                            startActivity(intent);
//                            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                        }
                        else {
                            CheckConnect.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra kết nối internet");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if(CheckConnect.isNetworkAvailable(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this, CategoryBottomActivity.class);
                            intent.putExtra("IdType",typeArrayList.get(i).getIdType());
                            startActivity(intent);
//                            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                        }
                        else {
                            CheckConnect.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra kết nối internet");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if(CheckConnect.isNetworkAvailable(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this, CategoryAccessoriesActivity.class);
                            intent.putExtra("IdType",typeArrayList.get(i).getIdType());
                            startActivity(intent);
//                            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                        }
                        else {
                            CheckConnect.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra kết nối internet");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 5:
                        if(CheckConnect.isNetworkAvailable(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this, PolicyActivity.class);
                            startActivity(intent);
//                            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                        }
                        else {
                            CheckConnect.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra kết nối internet");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 6:
                        if(CheckConnect.isNetworkAvailable(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this, AboutUsActivity.class);
                            startActivity(intent);
//                            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                        }
                        else {
                            CheckConnect.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra kết nối internet");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 7:
                        if(CheckConnect.isNetworkAvailable(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this, ContactActivity.class);
                            startActivity(intent);
//                            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                        }
                        else {
                            CheckConnect.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra kết nối internet");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 8:
                        Intent intent=new Intent(MainActivity.this, AdminActivity.class);
                        startActivity(intent);
                }
            }
        });
    }
    //lấy dữ liệu các loại sản phẩm cho thanh bar
    private void getDuLieuLoaiSanPham() {
        RequestQueue requestQueue =Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.LinkTypeProduct, new Response.Listener<JSONArray>() {
            @Override

            public void onResponse(JSONArray response) {
                if(response != null){

                    for(int i = 0;i < response.length();i++){
                        try {
                            JSONObject jsonObject =response.getJSONObject(i);
                            IdType=jsonObject.getInt("IdType");
                            Title=jsonObject.getString("Title");
                            typeArrayList.add(new type(IdType,Title));
                            typeAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    typeArrayList.add(5,new type(999,"POLICY GUIDE"));
                    typeArrayList.add(6,new type(999,"ABOUT US"));
                    typeArrayList.add(7,new type(999,"CONTACT"));
                    typeArrayList.add(8,new type(999,"ADMIN"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnect.ShowToast_Short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    //lấy dữ liệu các sản phẩm mới nhất
    private void getDuLieuSPMoiNhat() {
        RequestQueue requestQueue =Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.LinkNewProduct, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int IdProduct = 0;
                    String Name = "";
                    String Imei="";
                    String Description = "";
                    int IdType = 0;
                    double price =0;
                    String Image="";
                    int IdImage=0;
                    for(int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject  =response.getJSONObject(i);
                            IdProduct=jsonObject.getInt("IdProduct");
                            Name=jsonObject.getString("Name");
                            Imei=jsonObject.getString("Imei");
                            Description=jsonObject.getString("Description");
                            IdType=jsonObject.getInt("IdType");
                            price=jsonObject.getDouble("price");
                            Image=jsonObject.getString("Image");
                            IdImage=jsonObject.getInt("IdImage");
                            newproductArrayList.add(new product(IdProduct,Name,Imei,Description,IdType,price));
                            imageArrayList.add(new image(IdImage,Image,IdProduct));
                            newAdapter.notifyDataSetChanged();
                        }  catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    //Chạy banner quảng cáo
    private void ActionViewFlip() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://thuvienmuasam.com/uploads/default/original/2X/7/7ab9e20d857691c1e49c3c58a322e812bbcaa220.jpeg");
        mangquangcao.add("https://adsplus.vn/wp-content/uploads/2018/12/46506310_2207689146222326_6344748611338764288_o.jpg");
        mangquangcao.add("https://theme.hstatic.net/1000351433/1000669365/14/popup_shop_1.jpg?v=244");
        mangquangcao.add("https://theme.hstatic.net/1000351433/1000669365/14/block_home_category3.jpg?v=244");
        for (int i = 0; i < mangquangcao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipperFlasale.addView(imageView);
        }
        viewFlipperFlasale.setFlipInterval(3000);
        viewFlipperFlasale.setAutoStart(true);
        Animation animation_slip_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slip_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipperFlasale.setInAnimation(animation_slip_in);
        viewFlipperFlasale.setOutAnimation(animation_slip_out);
    }
    //Action bar
    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.icon_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);

            }
        });

    }

    private void AndXa() {
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawerlayout);
        viewFlipperFlasale = (ViewFlipper) findViewById(R.id.main_viewflip_flasale);
        imgSearch=(ImageView)findViewById(R.id.main_search);
        imgTop=(ImageButton)findViewById(R.id.main_imgBtn_shirt);
        imgBot=(ImageButton)findViewById(R.id.main_imgBtn_bottom);
        imgJacket=(ImageButton)findViewById(R.id.main_imgBtn_outwear);
        imgItem=(ImageButton)findViewById(R.id.main_imgBtn_accessories);
        mainMenu=(ListView)findViewById(R.id.main_menu);
        btnGioHang=(Button) findViewById(R.id.home_btn_giohang);
        btnDangNhap=(Button)findViewById(R.id.home_btn_account);
        dialog=new Dialog(this);

        //thanh bar menu
        typeArrayList=new ArrayList<>();
        typeArrayList.add(0,new type(999,"Trang chủ"));
        typeAdapter=new typeAdapter(typeArrayList,getApplicationContext());
        mainMenu.setAdapter(typeAdapter);

        //sản phẩm mới
        listener= new newAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent=new Intent(getApplicationContext(), ProductActivity.class);
                intent.putExtra("productDetail",newproductArrayList.get(position));
                intent.putExtra("productImage",imageArrayList.get(position));
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        };
        imageArrayList=new ArrayList<>();
        newproductArrayList=new ArrayList<>();
        newAdapter=new newAdapter(getApplicationContext(),newproductArrayList,imageArrayList,listener);
        NewrecyclerView=(RecyclerView)findViewById(R.id.main_rcvproduct);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        NewrecyclerView.setLayoutManager(gridLayoutManager);
        NewrecyclerView.setAdapter(newAdapter);

        //khởi chạy giỏ hàng chính
        if(ArrayCart!=null)
        {

        }else{
            ArrayCart=new ArrayList<>();
        }
    }
}



